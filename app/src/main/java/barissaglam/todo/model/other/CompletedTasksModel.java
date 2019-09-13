package barissaglam.todo.model.other;

import java.util.List;

import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.home.HomeAdapter;

public class CompletedTasksModel {
    private List<TaskResult> taskResults;
    private HomeAdapter homeAdapter;

    public CompletedTasksModel(List<TaskResult> taskResults, HomeAdapter homeAdapter) {
        this.taskResults = taskResults;
        this.homeAdapter = homeAdapter;
    }

    public List<TaskResult> getTaskResults() {
        return taskResults;
    }

    public void setTaskResults(List<TaskResult> taskResults) {
        this.taskResults = taskResults;
    }

    public HomeAdapter getHomeAdapter() {
        return homeAdapter;
    }

    public void setHomeAdapter(HomeAdapter homeAdapter) {
        this.homeAdapter = homeAdapter;
    }
}
