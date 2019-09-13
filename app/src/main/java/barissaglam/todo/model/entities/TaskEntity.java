package barissaglam.todo.model.entities;

import androidx.databinding.BaseObservable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import barissaglam.todo.model.result.TaskResult;

@Entity(tableName = "Tasks",
        foreignKeys = {
                @ForeignKey(entity = CategoryEntity.class,
                        parentColumns = "categoryID",
                        childColumns = "categoryID",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "categoryID")})

public class TaskEntity extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int taskID;
    private String taskName;
    private String taskDueDate;
    private String reminderDate;
    private int priorityID;
    private int categoryID;
    private boolean done;
    private int order;

    @Ignore
    public TaskEntity() {
    }


    public TaskEntity(String taskName, String taskDueDate, String reminderDate, int priorityID, int categoryID, boolean done, int order) {
        this.taskName = taskName;
        this.taskDueDate = taskDueDate;
        this.reminderDate = reminderDate;
        this.priorityID = priorityID;
        this.categoryID = categoryID;
        this.done = done;
        this.order = order;
    }

    @Ignore
    public TaskEntity(TaskResult taskResult){
        this.taskID = taskResult.getTaskID();
        this.taskName = taskResult.getTaskName();
        this.taskDueDate = taskResult.getTaskDueDate();
        this.reminderDate = taskResult.getReminderDate();
        this.priorityID = taskResult.getPriorityID();
        this.categoryID = taskResult.getCategoryID();
        this.done = taskResult.isDone();
        this.order = taskResult.getOrder();
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public int getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(int priorityID) {
        this.priorityID = priorityID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int listID) {
        this.categoryID = listID;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
