package barissaglam.todo.model.entities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TaskSteps")
public class TaskStepEntity extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int stepID;
    private int taskID;
    private String stepName;
    private boolean done;
    private int order;

    @Ignore
    public TaskStepEntity() {
    }

    public TaskStepEntity(int taskID, String stepName, boolean done, int order) {
        this.taskID = taskID;
        this.stepName = stepName;
        this.done = done;
        this.order = order;
    }

    public int getStepID() {
        return stepID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Bindable
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        notifyPropertyChanged(BR.done);
    }
}
