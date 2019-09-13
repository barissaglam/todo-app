package barissaglam.todo.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import barissaglam.todo.ui.createcategory.CreateCategoryViewModel;
import barissaglam.todo.ui.selectdatetime.DatePickerViewModel;
import barissaglam.todo.ui.selectdatetime.TimePickerViewModel;
import barissaglam.todo.ui.createtask.CreateTaskViewModel;
import barissaglam.todo.ui.home.HomeViewModel;
import barissaglam.todo.ui.main.MainViewModel;
import barissaglam.todo.ui.taskdetail.TaskDetailViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateTaskViewModel.class)
    abstract ViewModel bindCreateTaskViewModel(CreateTaskViewModel createTaskViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateCategoryViewModel.class)
    abstract ViewModel bindCreateListViewModel(CreateCategoryViewModel createCategoryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TaskDetailViewModel.class)
    abstract ViewModel bindTaskDetailViewModel(TaskDetailViewModel taskDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DatePickerViewModel.class)
    abstract ViewModel bindDatePickerViewModel(DatePickerViewModel datePickerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TimePickerViewModel.class)
    abstract ViewModel bindTimePickerViewModel(TimePickerViewModel timePickerViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);


}
