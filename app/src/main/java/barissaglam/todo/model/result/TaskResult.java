package barissaglam.todo.model.result;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import barissaglam.todo.BR;

public class TaskResult extends BaseObservable implements Parcelable {

    private int taskID;
    private String taskName;
    private String taskDueDate;
    private int categoryID;
    private String categoryName;
    private String categoryColor;
    private int priorityID;
    private String priority;
    private String reminderDate;
    private boolean done;
    private int order;

    public TaskResult() {
    }

    public TaskResult(String taskName, String taskDueDate, int categoryID, String categoryName, String categoryColor, int priorityID, String priority, String reminderDate, boolean done, int order) {
        this.taskName = taskName;
        this.taskDueDate = taskDueDate;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.priorityID = priorityID;
        this.priority = priority;
        this.reminderDate = reminderDate;
        this.done = done;
        this.order = order;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    @Bindable
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
        notifyPropertyChanged(BR.taskName);
    }

    @Bindable
    public String getTaskDueDate() {
        return taskDueDate == null ? "" : taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
        notifyPropertyChanged(BR.taskDueDate);
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(BR.categoryName);
    }

    @Bindable
    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
        notifyPropertyChanged(BR.categoryColor);
    }

    public int getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(int priorityID) {
        this.priorityID = priorityID;
    }

    @Bindable
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
        notifyPropertyChanged(BR.priority);
    }

    @Bindable
    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
        notifyPropertyChanged(BR.reminderDate);
    }

    @Bindable
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        notifyPropertyChanged(BR.done);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskID);
        dest.writeString(this.taskName);
        dest.writeString(this.taskDueDate);
        dest.writeInt(this.categoryID);
        dest.writeString(this.categoryName);
        dest.writeString(this.categoryColor);
        dest.writeInt(this.priorityID);
        dest.writeString(this.priority);
        dest.writeString(this.reminderDate);
        dest.writeByte(this.done ? (byte) 1 : (byte) 0);
    }

    protected TaskResult(Parcel in) {
        this.taskID = in.readInt();
        this.taskName = in.readString();
        this.taskDueDate = in.readString();
        this.categoryID = in.readInt();
        this.categoryName = in.readString();
        this.categoryColor = in.readString();
        this.priorityID = in.readInt();
        this.priority = in.readString();
        this.reminderDate = in.readString();
        this.done = in.readByte() != 0;
    }


    public static final Creator<TaskResult> CREATOR = new Creator<TaskResult>() {
        @Override
        public TaskResult createFromParcel(Parcel source) {
            return new TaskResult(source);
        }

        @Override
        public TaskResult[] newArray(int size) {
            return new TaskResult[size];
        }
    };
}
