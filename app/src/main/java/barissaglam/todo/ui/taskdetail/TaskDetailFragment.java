package barissaglam.todo.ui.taskdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentTaskDetailBinding;
import barissaglam.todo.helper.SimpleItemTouchHelperCallback;
import barissaglam.todo.model.entities.TaskStepEntity;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.model.event.DateSelectedEvent;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.BaseFragment;
import barissaglam.todo.ui.selectcategory.SelectCategoryDialog;
import barissaglam.todo.ui.selectdatetime.FragmentDatePicker;
import barissaglam.todo.ui.common.NavigationController;
import barissaglam.todo.ui.createtask.CreateTaskFragment;
import barissaglam.todo.utils.AutoClearedValue;
import barissaglam.todo.utils.Enums;
import barissaglam.todo.utils.KeyboardUtil;
import barissaglam.todo.utils.Keys;

public class TaskDetailFragment extends BaseFragment<FragmentTaskDetailBinding, TaskDetailViewModel> implements TaskStepAdapter.StepItemCallback {
    public static final String TAG = TaskDetailFragment.class.getCanonicalName();

    public static TaskDetailFragment newInstance(TaskResult taskResult) {

        Bundle args = new Bundle();
        args.putParcelable(Keys.KEY_TASK_RESULT, taskResult);

        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;

    // Other Variables
    private AutoClearedValue<FragmentTaskDetailBinding> binding;
    private TaskDetailViewModel mViewModel;
    private SelectCategoryDialog selectCategoryDialog;
    private TaskStepAdapter mStepAdapter;

    // View Variables
    private RecyclerView stepRecyclerView;
    private EditText edtAddStep;
    private Chip chipReminder, chipDueDate, chipPriority, chipList;
    private ImageButton btnCloseSubTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentTaskDetailBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel(savedInstanceState);
        setupObservableViewModel();
        setupViews();
        setupRecyclerView();
        setupAddStepEditText();
        setupOnClickListeners();

    }

    private void setupViewModel(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskDetailViewModel.class);
        mViewModel.getPage().set(Enums.Page.DETAIL);

        if (savedInstanceState == null && getArguments() != null)
            mViewModel.initModel(getArguments().getParcelable(Keys.KEY_TASK_RESULT));

        mViewModel.getTaskStepLiveData().setValue(mViewModel.getTaskStepEntityList());
        binding.get().setViewModel(mViewModel);
    }

    private void setupViews() {
        stepRecyclerView = binding.get().ftdStepRecyclerView;
        edtAddStep = binding.get().ftdEdtAddStep;
        chipDueDate = binding.get().ftdChipDueDate;
        chipReminder = binding.get().ftdChipReminder;
        chipPriority = binding.get().ftdChipPriority;
        chipList = binding.get().ftdChipList;
        btnCloseSubTask = binding.get().imageButton;
    }

    private void setupAddStepEditText() {

        edtAddStep.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtil.hideKeyboard(edtAddStep);
                mViewModel.createStep();
            }
            return false;
        });


        edtAddStep.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnCloseSubTask.setVisibility(View.VISIBLE);
                edtAddStep.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_button_unchecked_gray_24dp, 0, 0, 0);
            } else {
                edtAddStep.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_subdirectory_arrow_right_gray_24dp, 0, 0, 0);
                btnCloseSubTask.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setupObservableViewModel() {
        mViewModel.getTaskStepLiveData().observe(this, taskStepEntities -> {
            if (mStepAdapter.getItemCount() != 0 && taskStepEntities.size() != 0) {
                mStepAdapter.addItem(0, taskStepEntities.get(0));
                return;
            }

            mStepAdapter.insertList(taskStepEntities);
        });
    }

    private void setupRecyclerView() {
        mStepAdapter = new TaskStepAdapter(this, mViewModel);
        stepRecyclerView.setAdapter(mStepAdapter);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // Init ItemTouchHelper
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mStepAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(stepRecyclerView);
    }


    private void setupOnClickListeners() {
        binding.get().btnBack.setOnClickListener(v -> {
            if (getActivity() != null)
                getActivity().getSupportFragmentManager().popBackStack();

            //This event to show again BottomAppBar in Activity.
            EventBus.getDefault().post(new BottomAppBarEvent());
        });

        binding.get().btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCloseSubTask.setOnClickListener(v -> {
            mViewModel.stepName.set(null);
            KeyboardUtil.hideKeyboard(edtAddStep);
        });

        // ******************** Close icons of chips click listeners
        chipDueDate.setOnCloseIconClickListener(v -> mViewModel.resetDueDateField());
        chipReminder.setOnCloseIconClickListener(v -> mViewModel.resetReminderField());
        chipPriority.setOnCloseIconClickListener(v -> mViewModel.resetPriorityField());

        // ******************** Views click listeners
        chipDueDate.setOnClickListener(v -> showDatePicker(Enums.DatePickerAction.SELECT_TASK_DATE));

        chipReminder.setOnClickListener(v -> showDatePicker(Enums.DatePickerAction.SELECT_TASK_REMINDER_DATE));

        chipPriority.setOnClickListener(v ->
                showAlertDialogSingleChoiceItems("Choose Priority", mViewModel.getArrayOfPriorityNames(), (dialog, which) -> {
                    mViewModel.updatePriorityField(which);
                    dialog.dismiss();
                }, null, mViewModel.getTaskResult().getPriorityID() - 1));

        chipList.setOnClickListener(v -> {
            if (selectCategoryDialog == null)
                selectCategoryDialog = new SelectCategoryDialog();
            selectCategoryDialog.show(getChildFragmentManager(), CreateTaskFragment.TAG);
        });


    }

    // ******************** Functions for view **/

    private void showDatePicker(Enums.DatePickerAction datePickerAction) {
        FragmentDatePicker fragmentDatePicker = new FragmentDatePicker(datePickerAction, mViewModel.getDateSelectedEvent());
        fragmentDatePicker.show(getChildFragmentManager(), CreateTaskFragment.TAG);
    }

    // ******************** Functions for StepItemCallback **/

    @Override
    public void onStepItemClick(TaskStepEntity taskStepEntity) {
        taskStepEntity.setDone(!taskStepEntity.isDone());
        mViewModel.updateTaskStep(taskStepEntity);

        mStepAdapter.sortAllList();
    }


    // ******************** EventBus functions **/

    @Subscribe
    public void setDateSelectedEvent(DateSelectedEvent dateSelectedEvent) {
        mViewModel.updateDueDateOrReminderField(dateSelectedEvent);

    }

    // ******************** Lifecycle functions **/

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && edtAddStep != null && edtAddStep.isFocused())
            KeyboardUtil.showKeyboard(edtAddStep);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
