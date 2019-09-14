package barissaglam.todo.ui.drawer;

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
import barissaglam.todo.di.Injectable;
import barissaglam.todo.manager.LocalDataManager;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.ui.common.NavigationController;
import barissaglam.todo.ui.home.HomeViewModel;
import barissaglam.todo.utils.AutoClearedValue;

public class DrawerFragment extends BottomSheetDialogFragment implements Injectable, CategoriesAdapter.CategoryItemCallback {

    public static DrawerFragment newInstance() {

        Bundle args = new Bundle();

        DrawerFragment fragment = new DrawerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Inject Variables
    @Inject
    NavigationController navigationController;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    LocalDataManager localDataManager;

    // Normal Variables
    private AutoClearedValue<FragmentDrawerBinding> binding;
    private HomeViewModel homeViewModel;
    private CategoriesAdapter mAdapter;

    // View Variables



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDrawerBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_drawer, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();
        setupObservableViewModel();
        setupListeners();
    }

    private void setupViewModel() {
        homeViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory).get(HomeViewModel.class);
    }


    private void setupObservableViewModel() {
        homeViewModel.getCategoryListLiveData().observe(this, this::setupRecyclerView);
    }

    private void setupRecyclerView(List<CategoryEntity> listEntities) {
        mAdapter = new CategoriesAdapter(listEntities, this, localDataManager.getCurrentCategoryID()-1);
        binding.get().fdCategoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.get().fdCategoriesRecycler.setAdapter(mAdapter);
    }

    private void setupListeners() {
        binding.get().fdBtnCreateCategory.setOnClickListener(v -> {
            EventBus.getDefault().post(new BottomAppBarEvent());
            navigationController.navigateToCreateList();
            dismiss();
        });
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }


    // ******************** CategoryItemCallback functions **/
    @Override
    public void onClickCategoryItem(CategoryEntity categoryEntity) {
        dismiss();
        homeViewModel.getSelectedCategoryLiveData().setValue(categoryEntity);
        mAdapter.notifyDataSetChanged();

    }
}
