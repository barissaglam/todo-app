package barissaglam.todo.ui.createcategory;

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

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentCreateCategoryBinding;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.ui.base.BaseFragment;
import barissaglam.todo.ui.selectcolor.SelectColorDialog;
import barissaglam.todo.utils.AutoClearedValue;
import barissaglam.todo.utils.KeyboardUtil;

public class CreateCategoryFragment extends BaseFragment<FragmentCreateCategoryBinding, CreateCategoryViewModel> {
    public static final String TAG = CreateCategoryFragment.class.getCanonicalName();

    public static CreateCategoryFragment newInstance() {

        Bundle args = new Bundle();

        CreateCategoryFragment fragment = new CreateCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Inject Variables
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    // View Variables
    private EditText edtCategoryName;

    // Normal Variables
    private AutoClearedValue<FragmentCreateCategoryBinding> binding;
    private CreateCategoryViewModel mViewModel;
    private SelectColorDialog selectColorDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCreateCategoryBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_category, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViews();
        setupViewModel();
        observableViewModel();
        setupOnClickListeners();

    }

    private void setupViews() {
        edtCategoryName = binding.get().fccEdtCategoryName;
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateCategoryViewModel.class);
        binding.get().setViewModel(mViewModel);
    }

    private void observableViewModel() {
        mViewModel.getCreatedSuccess().observe(this, s -> closePage());

        mViewModel.getWarningMessage().observe(this, this::showWarning);
    }

    private void setupOnClickListeners() {
        // Views click listeners
        binding.get().btnCancel.setOnClickListener(v -> closePage());

        binding.get().btnDone.setOnClickListener(v -> mViewModel.createNewList());

        binding.get().fccImgSelectColor.setOnClickListener(v -> {
            if (selectColorDialog == null)
                selectColorDialog = new SelectColorDialog();
            selectColorDialog.show(getChildFragmentManager(), CreateCategoryFragment.TAG);
        });
    }


    private void closePage() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();

            if (edtCategoryName != null)
                KeyboardUtil.hideKeyboard(edtCategoryName);
        }

        //This event to show again BottomAppBar in Activity.
        EventBus.getDefault().post(new BottomAppBarEvent());
    }


    // ******************** Lifecycle functions **/

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && edtCategoryName != null)
            KeyboardUtil.showKeyboard(edtCategoryName);
    }
}
