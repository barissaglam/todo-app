package barissaglam.todo.di.module;

import barissaglam.todo.ui.createcategory.CreateCategoryFragment;
import barissaglam.todo.ui.createtask.CreateTaskFragment;
import barissaglam.todo.ui.home.HomeFragment;
import barissaglam.todo.ui.drawer.DrawerFragment;
import barissaglam.todo.ui.menu.MenuFragment;
import barissaglam.todo.ui.taskdetail.TaskDetailFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributesHomeFragment();

    @ContributesAndroidInjector
    abstract DrawerFragment contributesDrawerFragment();

    @ContributesAndroidInjector
    abstract MenuFragment contributesMenuFragment();

    @ContributesAndroidInjector
    abstract CreateCategoryFragment contributesCreateListFragment();

    @ContributesAndroidInjector
    abstract CreateTaskFragment contributesCreateTaskFragment();

    @ContributesAndroidInjector
    abstract TaskDetailFragment contributesTaskDetailFragment();


}
