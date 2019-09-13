package barissaglam.todo.ui.createtask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentCreateTaskBinding;
import barissaglam.todo.manager.LocalDataManager;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.model.event.DateSelectedEvent;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.reminder.ReminderBroadcastManager;
import barissaglam.todo.ui.base.BaseFragment;
import barissaglam.todo.ui.selectdatetime.FragmentDatePicker;
import barissaglam.todo.utils.AutoClearedValue;
import barissaglam.todo.utils.Enums;
import barissaglam.todo.utils.KeyboardUtil;
import barissaglam.todo.utils.Keys;

public class CreateTaskFragment extends BaseFragment<FragmentCreateTaskBinding, CreateTaskViewModel> {
    public static final String TAG = CreateTaskFragment.class.getCanonicalName();

    public static CreateTaskFragment newInstance() {

        Bundle args = new Bundle();
        CreateTaskFragment fragment = new CreateTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    LocalDataManager localDataManager;

    // Other Variables
    private AutoClearedValue<FragmentCreateTaskBinding> binding;
    private CreateTaskViewModel mViewModel;

    // Normal Variables

    // View Variables
    private EditText edtTaskText;
    private Chip chipDueDate, chipReminder, chipPriority;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCreateTaskBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();
        setupViews();
        setupObservableViewModel();
        setupOnClickListeners();
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateTaskViewModel.class);
        mViewModel.getPage().set(Enums.Page.CREATE);
        mViewModel.getTaskResult().setCategoryID(localDataManager.getCurrentCategoryID());
        binding.get().setViewModel(mViewModel);
    }


    private void setupViews() {
        edtTaskText = binding.get().fctEdtTaskText;
        chipDueDate = binding.get().fctChipDueDate;
        chipPriority = binding.get().fctChipPriority;
        chipReminder = binding.get().fctChipReminder;
    }

    private void setupOnClickListeners() {

        // ******************** Close icons of chips click listeners
        chipDueDate.setOnCloseIconClickListener(v -> mViewModel.resetDueDateField());
        chipReminder.setOnCloseIconClickListener(v -> mViewModel.resetReminderField());
        chipPriority.setOnCloseIconClickListener(v -> mViewModel.resetPriorityField());

        // ******************** Views click listeners
        binding.get().btnCancel.setOnClickListener(v -> closePage());

        chipDueDate.setOnClickListener(v -> showDatePicker(Enums.DatePickerAction.SELECT_TASK_DATE));
        chipReminder.setOnClickListener(v -> showDatePicker(Enums.DatePickerAction.SELECT_TASK_REMINDER_DATE));
        chipPriority.setOnClickListener(v ->
                showAlertDialogSingleChoiceItems("Choose Priority", mViewModel.getArrayOfPriorityNames(), (dialog, which) -> {
                    mViewModel.updatePriorityField(which);
                    dialog.dismiss();
                }, null, mViewModel.getTaskResult().getPriorityID() - 1));

    }

    private void setupObservableViewModel() {
        mViewModel.getTaskCreatedSuccess().observe(this, s -> {
            // When created new list, send an event to HomeFragment. Purpose the list which created is adding RecyclerView adapter.
            EventBus.getDefault().post(mViewModel.getLastTask());
            setAlarmManagerForReminder(mViewModel.getLastTask());
            closePage();
        });

        mViewModel.getWarningMessage().observe(this, this::showWarning);
    }


    // ******************** Functions for view **/

    private void showDatePicker(Enums.DatePickerAction datePickerAction) {
        FragmentDatePicker fragmentDatePicker = new FragmentDatePicker(datePickerAction, mViewModel.getDateSelectedEvent());
        fragmentDatePicker.show(getChildFragmentManager(), CreateTaskFragment.TAG);
    }

    private void closePage() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();

            if (edtTaskText != null)
                KeyboardUtil.hideKeyboard(edtTaskText);
        }

        //This event to show again BottomAppBar in Activity.
        EventBus.getDefault().post(new BottomAppBarEvent());
    }

    private void setAlarmManagerForReminder(TaskResult taskResult) {
        if (getContext() != null &&
                mViewModel != null &&
                mViewModel.getTaskResult() != null &&
                mViewModel.getDateSelectedEvent() != null &&
                mViewModel.getDateSelectedEvent().getSelectedReminderHour() != null &&
                !mViewModel.getDateSelectedEvent().getSelectedReminderHour().isEmpty() &&
                mViewModel.getDateSelectedEvent().getSelectedReminderMinute() != null &&
                !mViewModel.getDateSelectedEvent().getSelectedReminderMinute().isEmpty()) {

            Calendar calendar = Calendar.getInstance();

            int hour = Integer.valueOf(mViewModel.getDateSelectedEvent().getSelectedReminderHour());
            int minute = Integer.valueOf(mViewModel.getDateSelectedEvent().getSelectedReminderMinute());

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);


            Intent intent = new Intent(getContext(), ReminderBroadcastManager.class);
            Bundle todoBundle = new Bundle();
            todoBundle.putParcelable(Keys.KEY_TASK_RESULT, taskResult);
            intent.putExtra(Keys.KEY_TASK_RESULT, todoBundle);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        /*    if (Build.VERSION.SDK_INT >= 23)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            else
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);*/
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }

    }

    // ******************** EventBus functions **/

    @Subscribe
    public void dateSelectedEvent(DateSelectedEvent dateSelectedEvent) {
        mViewModel.updateDueDateOrReminderField(dateSelectedEvent);
    }

    // ******************** Lifecycle functions **/
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


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && edtTaskText != null)
            KeyboardUtil.showKeyboard(edtTaskText);
    }


}