package barissaglam.todo.ui.home;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.database.repository.TaskRepository;
import barissaglam.todo.manager.LocalDataManager;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.entities.TaskEntity;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.BaseViewModel;
import barissaglam.todo.ui.common.SingleLiveEvent;

public class HomeViewModel extends BaseViewModel {

    public ObservableInt totalTaskCount = new ObservableInt(0);
    public ObservableInt completedTaskCount = new ObservableInt(0);
    public ObservableInt listSortType = new ObservableInt();

    private MutableLiveData<List<CategoryEntity>> categoryListLiveData;
    private SingleLiveEvent<CategoryEntity> selectedCategoryLiveData;
    private MutableLiveData<List<TaskResult>> tasksLiveData;
    private MutableLiveData<List<TaskResult>> liveDataOfCompletedTasks;

    private TaskRepository taskRepository;


    @Inject
    public HomeViewModel(TaskRepository taskRepository, CategoryRepository categoryRepository, LocalDataManager localDataManager) {
        this.taskRepository = taskRepository;

        listSortType.set(localDataManager.getListSortType());

        // For DrawerFragment. Because all of lists in DrawerFragment. DrawerFragment and HomeFragment using common ViewModel...
        getCategoryListLiveData().setValue(categoryRepository.getAllCategories());
    }

    void updateTask(TaskResult taskResult) {
        taskRepository.updateTask(new TaskEntity(taskResult));
    }

    void getAllTasks(int listID) {
        getTasksLiveData().setValue(taskRepository.getAllTaskByList(listID, false));
        getLiveDataOfCompletedTasks().setValue(taskRepository.getAllTaskByList(listID,true));

        if (getTasksLiveData().getValue() != null &&getLiveDataOfCompletedTasks().getValue() != null){
            totalTaskCount.set(getTasksLiveData().getValue().size() + getLiveDataOfCompletedTasks().getValue().size());
            completedTaskCount.set(getLiveDataOfCompletedTasks().getValue().size());
        }
    }

    void addNewTaskToCurrentList(TaskResult taskResult) {
        totalTaskCount.set(totalTaskCount.get() + 1);

        if (getTasksLiveData().getValue() != null)
            getTasksLiveData().getValue().add(0, taskResult);
    }


    // ******************** Getter functions **/

    MutableLiveData<List<TaskResult>> getTasksLiveData() {
        if (tasksLiveData == null)
            tasksLiveData = new MutableLiveData<>();
        return tasksLiveData;
    }

    MutableLiveData<List<TaskResult>> getLiveDataOfCompletedTasks() {
        if (liveDataOfCompletedTasks == null)
            liveDataOfCompletedTasks = new MutableLiveData<>();
        return liveDataOfCompletedTasks;
    }

    public MutableLiveData<List<CategoryEntity>> getCategoryListLiveData() {
        if (categoryListLiveData == null)
            categoryListLiveData = new MutableLiveData<>();
        return categoryListLiveData;
    }

    public SingleLiveEvent<CategoryEntity> getSelectedCategoryLiveData() {
        if (selectedCategoryLiveData == null)
            selectedCategoryLiveData = new SingleLiveEvent<>();
        return selectedCategoryLiveData;
    }

    List<TaskResult> getTasksList(int categoryID) {
        return taskRepository.getAllTaskByList(categoryID, false);
    }
}