package barissaglam.todo.ui.createtask;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.model.entities.TaskEntity;
import barissaglam.todo.database.repository.TaskRepository;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.CreateDetailViewModel;
import barissaglam.todo.ui.common.SingleLiveEvent;

public class CreateTaskViewModel extends CreateDetailViewModel {

    // For contact with UI
    private SingleLiveEvent<String> warningMessage;
    private MutableLiveData<Boolean> taskCreatedSuccess;


    // ******************** CONSTRUCTOR **/
    @Inject
    public CreateTaskViewModel(Application application, CategoryRepository categoryRepository, TaskRepository taskRepository) {
        super(application, categoryRepository, taskRepository);
    }

    // ******************** Functions for view **/

    public void onCreateNewTask() {
        if (getTaskResult().getTaskName() == null || getTaskResult().getTaskName().isEmpty()) {
            warningMessage.setValue("Please enter the task name.");
            return;
        }

        getTaskResult().setOrder(getTaskRepository().getLargestTaskOrder() + 1);
        getTaskRepository().CreateNewTask(new TaskEntity(getTaskResult()));
        taskCreatedSuccess.setValue(true);
    }

    // ******************** Getter functions **/

    SingleLiveEvent<String> getWarningMessage() {
        if (warningMessage == null)
            warningMessage = new SingleLiveEvent<>();
        return warningMessage;
    }

    MutableLiveData<Boolean> getTaskCreatedSuccess() {
        if (taskCreatedSuccess == null)
            taskCreatedSuccess = new MutableLiveData<>();
        return taskCreatedSuccess;
    }

    TaskResult getLastTask() {
        return getTaskRepository().getLastTask();
    }


}
