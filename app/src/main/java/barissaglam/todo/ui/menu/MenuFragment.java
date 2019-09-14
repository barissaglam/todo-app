package barissaglam.todo.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentDrawerBinding;
import barissaglam.todo.databinding.FragmentMenuBinding;
import barissaglam.todo.di.Injectable;
import barissaglam.todo.manager.LocalDataManager;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.model.event.DeleteCompletedTasksEvent;
import barissaglam.todo.model.event.ListSortEvent;
import barissaglam.todo.ui.common.NavigationController;
import barissaglam.todo.ui.drawer.CategoriesAdapter;
import barissaglam.todo.ui.home.HomeViewModel;
import barissaglam.todo.utils.AutoClearedValue;
import barissaglam.todo.utils.Constants;

public class MenuFragment extends BottomSheetDialogFragment implements Injectable{
    public static final int SORT_CUSTOM = 0;
    public static final int SORT_DATE = 1;
    public static final int SORT_PRIORITY = 2;

    public static MenuFragment newInstance() {

        Bundle args = new Bundle();

        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Inject Variables
    @Inject
    LocalDataManager localDataManager;
   /* @Inject
    NavigationController navigationController;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    LocalDataManager localDataManager;
*/
    // Normal Variables
    private AutoClearedValue<FragmentMenuBinding> binding;
    private HomeViewModel mViewModel;

    // View Variables
    CategoriesAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMenuBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();

        setupOnClickListeners();
    }

    private void setupViewModel() {
       // mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory).get(HomeViewModel.class);
    }

    private void setupOnClickListeners(){
        // SortBy buttons click listeners
        binding.get().fmBtnSortCustom.setOnClickListener(v -> {
            localDataManager.setListSortType(Constants.SORT_LIST_CUSTOM);
            EventBus.getDefault().post(new ListSortEvent(Constants.SORT_LIST_CUSTOM));
            dismiss();
        });

        binding.get().fmBtnSortDate.setOnClickListener(v -> {
            localDataManager.setListSortType(Constants.SORT_LIST_DATE);
            EventBus.getDefault().post(new ListSortEvent(Constants.SORT_LIST_DATE));
            dismiss();
        });

        binding.get().fmBtnSortPriority.setOnClickListener(v -> {
            localDataManager.setListSortType(Constants.SORT_LIST_PRIORITY);
            EventBus.getDefault().post(new ListSortEvent(Constants.SORT_LIST_PRIORITY));
            dismiss();
        });

        // Options buttons click listeners
        binding.get().fmBtnOptionsDeleteCompleted.setOnClickListener(v -> {
            EventBus.getDefault().post(new DeleteCompletedTasksEvent());
            dismiss();
        });
    }


    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

}
