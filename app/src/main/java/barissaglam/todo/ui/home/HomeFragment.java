package barissaglam.todo.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentHomeBinding;
import barissaglam.todo.manager.LocalDataManager;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.model.event.DeleteCompletedTasksEvent;
import barissaglam.todo.model.event.ListSortEvent;
import barissaglam.todo.model.other.CompletedTasksModel;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.BaseFragment;
import barissaglam.todo.ui.common.NavigationController;
import barissaglam.todo.utils.AutoClearedValue;
import barissaglam.todo.utils.Constants;
import barissaglam.todo.utils.DateUtils;
import barissaglam.todo.utils.RecyclerViewUtils;
import io.reactivex.Observable;
import timber.log.Timber;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeAdapter.HomeItemCallback {
    public static final String TAG = HomeFragment.class.getCanonicalName();

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @Inject
    LocalDataManager localDataManager;

    // Normal Variables
    private AutoClearedValue<FragmentHomeBinding> binding;
    private HomeViewModel mViewModel;
    private HomeAdapter mAdapter, completedTasksAdapter;

    // View Variables
    private RecyclerView mRecyclerView, completedTasksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViews();
        setupViewModel();
        setupObservableViewModel();
        setupRecycler();

        mViewModel.getAllTasks(localDataManager.getCurrentCategoryID());
    }


    private void setupViews() {
        mRecyclerView = binding.get().fhHomeRecyclerView;
        completedTasksRecyclerView = binding.get().fhCompletedTasksRecyclerView;
    }

    private void setupRecycler() {
        mAdapter = new HomeAdapter(this, mViewModel);
        mAdapter.setSortType(mViewModel.listSortType.get());

        completedTasksAdapter = new HomeAdapter(this, mViewModel);
        completedTasksRecyclerView.setAdapter(completedTasksAdapter);
        completedTasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory).get(HomeViewModel.class);
        binding.get().setViewModel(mViewModel);
    }

    private void setupObservableViewModel() {
        mViewModel.getTasksLiveData().observe(getViewLifecycleOwner(), taskResults -> {
            if (mAdapter == null || taskResults == null || taskResults.size() == 0)
                return;
            mAdapter.setupList(taskResults);
        });

        mViewModel.getLiveDataOfCompletedTasks().observe(getViewLifecycleOwner(), taskResults -> {
            if (completedTasksAdapter == null || taskResults == null || taskResults.size() == 0)
                return;
            completedTasksAdapter.setupCompletedTaskList(taskResults);
        });

        mViewModel.getSelectedCategoryLiveData().observe(getViewLifecycleOwner(), categoryEntity -> {
            if (mAdapter != null)
                mAdapter.clear();

            mViewModel.getAllTasks(categoryEntity.getCategoryID());
            localDataManager.setCurrentCategoryID(categoryEntity.getCategoryID());
        });
    }


    // ******************** HomeItemCallback Functions **/

    @Override
    public void onShowTaskDetail(TaskResult taskResult) {
        EventBus.getDefault().post(new BottomAppBarEvent());
        navigationController.navigateToTaskDetail(taskResult);
    }

    @Override
    public void onUpdateTaskDone(TaskResult taskResult, int itemPos) {
        taskResult.setDone(!taskResult.isDone());
        mViewModel.updateTask(taskResult);
        mAdapter.updateTaskStatusInList(completedTasksAdapter, taskResult, itemPos);
    }

    // ******************** EventBus functions **/

    @Subscribe
    public void createdNewTaskEvent(TaskResult taskResult) {
        mViewModel.addNewTaskToCurrentList(taskResult);
        mAdapter.addNewItem(taskResult);
    }

    @Subscribe
    public void sortListEvent(ListSortEvent listSortEvent) {
        if (listSortEvent.getSortType() == mViewModel.listSortType.get())
            return;

        mViewModel.listSortType.set(listSortEvent.getSortType());
        mAdapter.setSortType(listSortEvent.getSortType());

        mAdapter.setupList(mViewModel.getTasksList(localDataManager.getCurrentCategoryID()));
    }

    @Subscribe
    public void deleteCompletedTasksEvent(DeleteCompletedTasksEvent deleteCompletedTasksEvent) {
        mViewModel.deleteCompletedTasks();
        completedTasksAdapter.clear();
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
}
