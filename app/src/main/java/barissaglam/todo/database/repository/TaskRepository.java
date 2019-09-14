package barissaglam.todo.database.repository;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import barissaglam.todo.database.dao.TaskDao;
import barissaglam.todo.model.entities.TaskEntity;
import barissaglam.todo.model.entities.TaskStepEntity;
import barissaglam.todo.model.result.TaskResult;

public class TaskRepository {
    private TaskDao taskDao;

    @Inject
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void CreateNewTask(TaskEntity taskEntity) {
        taskDao.createTask(taskEntity);
    }

    public List<TaskResult> getAllTaskByList(int listID, boolean done) {
        return taskDao.getTasksByList(listID, done);
    }

    public TaskResult getLastTask() {
        return taskDao.getLastTask();
    }

    public void updateTask(TaskEntity taskEntity) {
        taskDao.updateTask(taskEntity);
    }

    public int getLargestTaskOrder() {
        return taskDao.getLargestTaskOrder();
    }
    public void deleteCompletedTasks(int categoryID){
        taskDao.deleteCompletedTasks(categoryID);
    }

    // steps
    public void createTaskStep(TaskStepEntity taskStepEntity) {
        taskDao.createTaskStep(taskStepEntity);
    }

    public List<TaskStepEntity> getTaskStepsById(int taskID) {
        return taskDao.getTaskStepsById(taskID);
    }

    public TaskStepEntity getLastStep(int taskID) {
        return taskDao.getLastStep(taskID);
    }

    public int getLargestStepOrder() {
        return taskDao.getLargestStepOrder();
    }

    public void updateTaskStep(TaskStepEntity taskStepEntity) {
        taskDao.updateTaskStep(taskStepEntity);
    }
}
