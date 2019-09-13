package barissaglam.todo.ui.base;

import android.app.Application;

import androidx.databinding.ObservableField;

import com.google.gson.Gson;

import java.util.List;

import barissaglam.todo.R;
import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.database.repository.TaskRepository;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.entities.TaskEntity;
import barissaglam.todo.model.event.DateSelectedEvent;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.utils.Enums;
import timber.log.Timber;

public class CreateDetailViewModel extends BaseViewModel {

    private ObservableField<Enums.Page> page;
    private DateSelectedEvent dateSelectedEvent;
    private TaskResult taskResult = new TaskResult();

    private String[] arrayOfPriorityNames;

    private TaskRepository taskRepository;
    private List<CategoryEntity> categoryEntities;


    public CreateDetailViewModel(Application application, CategoryRepository categoryRepository, TaskRepository taskRepository) {
        this.arrayOfPriorityNames = application.getResources().getStringArray(R.array.priorityList);
        this.taskRepository = taskRepository;
        this.categoryEntities = categoryRepository.getAllCategories();

    }

    // ******************** Functions to reset TaskResult fields **/

    public void resetDueDateField() {
        dateSelectedEvent.setSelectedTaskDate(null);
        taskResult.setTaskDueDate(null);

        if (getPage().get() == Enums.Page.DETAIL)
            taskRepository.updateTask(new TaskEntity(taskResult));
    }

    public void resetReminderField() {
        dateSelectedEvent.setSelectedReminderHour(null);
        dateSelectedEvent.setSelectedReminderMinute(null);
        taskResult.setReminderDate(null);

        if (getPage().get() == Enums.Page.DETAIL)
            taskRepository.updateTask(new TaskEntity(taskResult));
    }

    public void resetPriorityField() {
        taskResult.setPriorityID(0);
        taskResult.setPriority(null);

        if (getPage().get() == Enums.Page.DETAIL)
            taskRepository.updateTask(new TaskEntity(taskResult));
    }

    // ******************** Functions to update TaskResult fields **/

    public void updateDueDateOrReminderField(DateSelectedEvent dateSelectedEvent) {
        this.dateSelectedEvent = dateSelectedEvent;

        if (dateSelectedEvent.getDatePickerAction() == Enums.DatePickerAction.SELECT_TASK_DATE)
            taskResult.setTaskDueDate(dateSelectedEvent.getSelectedTaskDate());

        if (dateSelectedEvent.getDatePickerAction() == Enums.DatePickerAction.SELECT_TASK_REMINDER_DATE)
            taskResult.setReminderDate(dateSelectedEvent.getFullReminderDate());

        Timber.i(new Gson().toJson(dateSelectedEvent));

        if (getPage().get() == Enums.Page.DETAIL)
            taskRepository.updateTask(new TaskEntity(taskResult));
    }

    public void updateListField(int which) {
        taskResult.setCategoryID(which + 1);
        taskResult.setCategoryName(categoryEntities.get(which).getCategoryName());
        taskResult.setCategoryColor(categoryEntities.get(which).getCategoryColor());

        if (getPage().get() == Enums.Page.DETAIL)
            taskRepository.updateTask(new TaskEntity(taskResult));
    }

    public void updatePriorityField(int which) {
        taskResult.setPriorityID(which + 1);
        taskResult.setPriority(arrayOfPriorityNames[which]);

        if (getPage().get() == Enums.Page.DETAIL)
            taskRepository.updateTask(new TaskEntity(taskResult));
    }

    // ******************** Getter functions **/

    public DateSelectedEvent getDateSelectedEvent() {
        if (dateSelectedEvent == null)
            dateSelectedEvent = new DateSelectedEvent();
        return dateSelectedEvent;
    }

    public TaskResult getTaskResult() {
        return taskResult;
    }

    public String[] getArrayOfPriorityNames() {
        return arrayOfPriorityNames;
    }

    protected TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public ObservableField<Enums.Page> getPage() {
        if (page == null)
            page = new ObservableField<>();
        return page;
    }

    public void setTaskResult(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }
}
