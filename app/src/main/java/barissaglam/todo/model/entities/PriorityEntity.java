package barissaglam.todo.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PriorityList")
public class PriorityEntity {
    @PrimaryKey(autoGenerate = true)
    private int priorityID;

    private String priorityTR;
    private String priorityEN;


    public PriorityEntity(String priorityTR, String priorityEN) {
        this.priorityTR = priorityTR;
        this.priorityEN = priorityEN;
    }

    public int getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(int priorityID) {
        this.priorityID = priorityID;
    }

    public String getPriorityTR() {
        return priorityTR;
    }

    public void setPriorityTR(String priorityTR) {
        this.priorityTR = priorityTR;
    }

    public String getPriorityEN() {
        return priorityEN;
    }

    public void setPriorityEN(String priorityEN) {
        this.priorityEN = priorityEN;
    }
}
