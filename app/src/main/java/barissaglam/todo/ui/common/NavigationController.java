package barissaglam.todo.ui.common;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.createcategory.CreateCategoryFragment;
import barissaglam.todo.ui.createtask.CreateTaskFragment;
import barissaglam.todo.ui.home.HomeFragment;
import barissaglam.todo.ui.main.MainActivity;
import barissaglam.todo.ui.taskdetail.TaskDetailFragment;

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }


    public void navigateToHome() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, HomeFragment.newInstance(), HomeFragment.TAG);
        transaction.commit();
    }

    public void navigateToCreateList() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.fade_in, 0, 0, R.animator.fade_out);
        transaction.add(containerId, CreateCategoryFragment.newInstance(), CreateCategoryFragment.TAG);
        transaction.addToBackStack(CreateCategoryFragment.TAG);
        transaction.commit();
    }

    public void navigateToCreateTask() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.fade_in, 0, 0, R.animator.fade_out);
        transaction.add(containerId, CreateTaskFragment.newInstance(), CreateTaskFragment.TAG);
        transaction.addToBackStack(CreateTaskFragment.TAG);
        transaction.commit();
    }

    public void navigateToTaskDetail(TaskResult taskResult) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.fade_in, 0, 0, R.animator.fade_out);
        transaction.add(containerId, TaskDetailFragment.newInstance(taskResult), TaskDetailFragment.TAG);
        transaction.addToBackStack(TaskDetailFragment.TAG);
        transaction.commit();
    }

}
