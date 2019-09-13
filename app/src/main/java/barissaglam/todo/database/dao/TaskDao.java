package barissaglam.todo.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import barissaglam.todo.model.entities.TaskEntity;

import java.util.List;

import barissaglam.todo.model.entities.TaskStepEntity;
import barissaglam.todo.model.result.TaskResult;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Query("SELECT Tasks.taskID, " +
            "Tasks.taskName, " +
            "Tasks.taskDueDate, " +
            "Categories.categoryID," +
            "Categories.categoryName, " +
            "Categories.categoryColor," +
            "PriorityList.priorityID ," +
            "PriorityList.priorityEN AS priority," +
            "Tasks.reminderDate, " +
            "Tasks.`order`, " +
            "Tasks.done FROM Tasks INNER JOIN Categories ON Tasks.categoryID = Categories.categoryID LEFT JOIN PriorityList ON Tasks.priorityID = PriorityList.priorityID WHERE done=:done AND Tasks.categoryID=:categoryID ORDER BY Tasks.`order` DESC")
    List<TaskResult> getTasksByList(int categoryID, boolean done);

    @Query("SELECT Tasks.taskID, " +
            "Tasks.taskName, " +
            "Tasks.taskDueDate, " +
            "Categories.categoryID," +
            "Categories.categoryName, " +
            "Categories.categoryColor," +
            "PriorityList.priorityID ," +
            "PriorityList.priorityEN AS priority," +
            "Tasks.reminderDate, " +
            "Tasks.`order`, " +
            "Tasks.done FROM Tasks INNER JOIN Categories ON Tasks.categoryID = Categories.categoryID LEFT JOIN PriorityList ON Tasks.priorityID = PriorityList.priorityID  ORDER BY Tasks.taskID DESC LIMIT 1")
    TaskResult getLastTask();

    @Query("SELECT MAX(`order`) FROM Tasks")
    int getLargestTaskOrder();

    @Insert(onConflict = REPLACE)
    void createTask(TaskEntity taskEntity);

    @Update(onConflict = REPLACE)
    void updateTask(TaskEntity taskEntity);


    // ******************** TaskStep **
    @Insert(onConflict = REPLACE)
    void createTaskStep(TaskStepEntity taskStepEntity);

    @Update(onConflict = REPLACE)
    void updateTaskStep(TaskStepEntity taskStepEntity);

    @Query("SELECT * FROM TaskSteps WHERE taskID=:taskID ORDER BY done ASC, `order` DESC")
    List<TaskStepEntity> getTaskStepsById(int taskID);

    @Query("SELECT * FROM TaskSteps WHERE taskID=:taskID ORDER BY stepID DESC LIMIT 1")
    TaskStepEntity getLastStep(int taskID);

    @Query("SELECT MAX(`order`) FROM TaskSteps")
    int getLargestStepOrder();

    @Query("UPDATE TaskSteps SET done=:done WHERE stepID=:stepID")
    void updateTaskStepDoneStatus(Boolean done, int stepID);


}
