package barissaglam.todo.ui.taskdetail;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.model.entities.TaskStepEntity;
import barissaglam.todo.database.repository.TaskRepository;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.CreateDetailViewModel;
import barissaglam.todo.utils.DateUtils;

public class TaskDetailViewModel extends CreateDetailViewModel {

    private MutableLiveData<List<TaskStepEntity>> taskStepLiveData;
    public ObservableField<String> stepName = new ObservableField<>("");

    @Inject
    public TaskDetailViewModel(Application application, CategoryRepository categoryRepository, TaskRepository taskRepository) {
        super(application, categoryRepository, taskRepository);

    }

    void createStep() {
        if (stepName.get() == null || stepName.get().trim().isEmpty())
            return;

        getTaskRepository().createTaskStep(new TaskStepEntity(getTaskResult().getTaskID(), stepName.get().trim(), false, getTaskRepository().getLargestStepOrder() + 1));
        getTaskStepLiveData().setValue(getTaskRepository().getTaskStepsById(getTaskResult().getTaskID()));
        stepName.set(null);
    }


    void initModel(TaskResult taskResult) {
        setTaskResult(taskResult);

        getDateSelectedEvent().setSelectedTaskDate(getTaskResult().getTaskDueDate());
        getDateSelectedEvent().setSelectedReminderDate(DateUtils.getDateFromDateTime("dd/MM/yyyy HH:mm", getTaskResult().getReminderDate()));
        getDateSelectedEvent().setSelectedReminderHour(DateUtils.getHourFromDateTime("dd/MM/yyyy HH:mm", getTaskResult().getReminderDate()));
        getDateSelectedEvent().setSelectedReminderMinute(DateUtils.getMinuteFromDateTime("dd/MM/yyyy HH:mm", getTaskResult().getReminderDate()));
    }

    void updateTaskStep(TaskStepEntity taskStepEntity) {
        getTaskRepository().updateTaskStep(taskStepEntity);
    }

    // ******************** Getter functions **/
    List<TaskStepEntity> getTaskStepEntityList() {
        return getTaskRepository().getTaskStepsById(getTaskResult().getTaskID());
    }

    MutableLiveData<List<TaskStepEntity>> getTaskStepLiveData() {
        if (taskStepLiveData == null)
            taskStepLiveData = new MutableLiveData<>();
        return taskStepLiveData;
    }

    TaskStepEntity getLastStep() {
        return getTaskRepository().getLastStep(getTaskResult().getTaskID());
    }
}
