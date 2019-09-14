package barissaglam.todo.ui.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.apache.commons.collections4.map.ListOrderedMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import barissaglam.todo.BR;
import barissaglam.todo.R;
import barissaglam.todo.helper.ItemTouchHelperAdapter;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.adapter.DataBoundViewHolder;
import barissaglam.todo.ui.base.adapter.MultiTypeDataBoundAdapter;
import barissaglam.todo.utils.Constants;
import timber.log.Timber;

public class HomeAdapter extends MultiTypeDataBoundAdapter implements ItemTouchHelperAdapter {
    private final HomeItemCallback mCallback;
    private final HomeViewModel homeViewModel;
    private boolean hideDetail;
    private int sortType;

    void setSortType(int sortType) {
        this.sortType = sortType;
    }


    private ListOrderedMap<String, List<TaskResult>> taskMap = new ListOrderedMap<>();
    private ListOrderedMap<String, Integer> mapOfPriorityID = new ListOrderedMap<>();


    public HomeAdapter(HomeItemCallback callback, HomeViewModel homeViewModel, Object... items) {
        this.mCallback = callback;
        this.homeViewModel = homeViewModel;

        // Init priority map
        mapOfPriorityID.put("No Priority", 0);
        mapOfPriorityID.put("Low", 1);
        mapOfPriorityID.put("Medium", 2);
        mapOfPriorityID.put("High", 3);

    }

    @Override
    public int getItemLayoutId(int position) {
        if (mItems.get(position) instanceof String) {
            return R.layout.item_tasklist_header;
        } else if (mItems.get(position) instanceof TaskResult)
            return R.layout.item_tasks;
        else return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    protected void bindItem(DataBoundViewHolder holder, int position, List payloads) {
        super.bindItem(holder, position, payloads);

        holder.binding.setVariable(BR.data, mItems.get(position));
        holder.binding.setVariable(BR.callback, mCallback);
        holder.binding.setVariable(BR.ItemPos, holder.getAdapterPosition());
        holder.binding.setVariable(BR.HideDetail, hideDetail);

    }

    // ******************** List sort functions **/

    void clear() {
        notifyItemRangeRemoved(0, mItems.size());
        mItems.clear();
        if (taskMap.size() != 0)
            taskMap.clear();
    }


    // ******************** Functions of SimpleItemTouchHelperCallback S**/
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);

                if (mItems.get(i) instanceof TaskResult && mItems.get(i + 1) instanceof TaskResult) {
                    TaskResult taskResult1 = (TaskResult) mItems.get(i);
                    TaskResult taskResult2 = (TaskResult) mItems.get(i + 1);
                    int order1 = taskResult1.getOrder();
                    int order2 = taskResult2.getOrder();
                    taskResult1.setOrder(order2);
                    taskResult2.setOrder(order1);

                    homeViewModel.updateTask(taskResult1);
                    homeViewModel.updateTask(taskResult2);
                }
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);

                if (mItems.get(i) instanceof TaskResult && mItems.get(i - 1) instanceof TaskResult) {
                    TaskResult taskResult1 = (TaskResult) mItems.get(i);
                    TaskResult taskResult2 = (TaskResult) mItems.get(i - 1);
                    int order1 = taskResult1.getOrder();
                    int order2 = taskResult2.getOrder();
                    taskResult1.setOrder(order2);
                    taskResult2.setOrder(order1);

                    homeViewModel.updateTask(taskResult1);
                    homeViewModel.updateTask(taskResult2);
                }
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }


    ////// ŞUAN BURADA ÇALIŞIYORUM

    void setupList(@NonNull List<TaskResult> taskResults) {
        clear();

        switch (sortType) {
            case Constants.SORT_LIST_CUSTOM:
                setupToSortByCustom(taskResults);
                break;
            case Constants.SORT_LIST_PRIORITY:
                setupToSortByPriority(taskResults);
                break;
            case Constants.SORT_LIST_DATE:
                setupToSortByDueDate(taskResults);
                break;
        }

    }

    void setupCompletedTaskList(List<TaskResult> taskResults) {
        for (TaskResult taskResult : taskResults)
            addItem(taskResult);
    }

    // ******************** Setup list to sort **/

    private void setupToSortByCustom(List<TaskResult> taskResults) {
        Collections.sort(taskResults, (t1, t2) -> t2.getOrder() - t1.getOrder());

        for (TaskResult taskResult : taskResults)
            addItem(taskResult);
    }

    private void setupToSortByPriority(List<TaskResult> taskResults) {
        Collections.sort(taskResults, (t1, t2) -> t2.getPriorityID() - t1.getPriorityID());

        for (TaskResult taskResult : taskResults) {
            String sectionName = (taskResult.getPriority() == null || taskResult.getPriority().isEmpty()) ? "No Priority" : taskResult.getPriority();
            List<TaskResult> value = taskMap.get(sectionName);
            if (value == null) {
                value = new ArrayList<>();
                taskMap.put(sectionName, value);
                addItem(sectionName);
            }
            value.add(taskResult);
            addItem(taskResult);
        }
    }

    private void setupToSortByDueDate(List<TaskResult> taskResults) {
        Collections.sort(taskResults, (t1, t2) -> compareTwoDate(t1.getTaskDueDate(), t2.getTaskDueDate()));

        for (TaskResult taskResult : taskResults) {
            String sectionName = (taskResult.getTaskDueDate() == null || taskResult.getTaskDueDate().isEmpty()) ? "No Date" : taskResult.getTaskDueDate();
            List<TaskResult> value = taskMap.get(sectionName);
            if (value == null) {
                value = new ArrayList<>();
                taskMap.put(sectionName, value);
                addItem(sectionName);
            }
            value.add(taskResult);
            addItem(taskResult);
        }
    }


    void addNewItem(TaskResult taskResult) {

        if (sortType == Constants.SORT_LIST_CUSTOM)
            addItem(0, taskResult);
        else {
            int sectionPositionInList = 0;
            int sectionPositionInMap = 0;
            String sectionNameOfNewTask = getSectionNameOfTask(taskResult);

            if (taskMap.size() != 0)
                for (Map.Entry<String, List<TaskResult>> entry : taskMap.entrySet()) {
                    String sectionName = entry.getKey();
                    List<TaskResult> taskListInSection = entry.getValue();

                    if (sortType == Constants.SORT_LIST_DATE && compareTwoDate(sectionNameOfNewTask, sectionName) < 0) {
                        addNewItemWithSection(sectionPositionInList, sectionPositionInMap, taskResult, sectionNameOfNewTask);
                        return;
                    } else if (sortType == Constants.SORT_LIST_PRIORITY && taskResult.getPriorityID() > mapOfPriorityID.get(sectionName)) {
                        addNewItemWithSection(sectionPositionInList, sectionPositionInMap, taskResult, sectionNameOfNewTask);
                        return;
                    } else if (sectionNameOfNewTask.equals(sectionName)) {
                        addNewItemWithoutSection(sectionPositionInList, taskResult, sectionNameOfNewTask);
                        return;
                    } else {
                        sectionPositionInList = sectionPositionInList + taskListInSection.size() + 1;
                        if (sectionPositionInList == mItems.size()) {
                            addNewItemWithSection(sectionPositionInList, sectionPositionInMap + 1, taskResult, sectionNameOfNewTask);
                            return;
                        }
                    }
                    sectionPositionInMap++;
                }
            else
                addNewItemWithSection(0, 0, taskResult, sectionNameOfNewTask);
        }
    }

    private void addNewItemWithSection(int sectionPositionInList, int sectionPositionInMap, TaskResult taskResult, String sectionNameOfNewTask) {
        addItem(sectionPositionInList, sectionNameOfNewTask); // Added section for Date or Priority
        addItem(sectionPositionInList + 1, taskResult); // Add task below section
        List<TaskResult> taskResults = new ArrayList<>();
        taskResults.add(taskResult);
        taskMap.put(sectionPositionInMap, sectionNameOfNewTask, taskResults); // Added task in map
    }

    private void addNewItemWithoutSection(int sectionPositionInList, TaskResult taskResult, String sectionNameOfNewTask) {
        addItem(sectionPositionInList + 1, taskResult);
        taskMap.get(sectionNameOfNewTask).add(0, taskResult);

    }

    private String getSectionNameOfTask(TaskResult taskResult) {
        if (sortType == Constants.SORT_LIST_DATE)
            return (taskResult.getTaskDueDate() == null || taskResult.getTaskDueDate().isEmpty()) ? "No Date" : taskResult.getTaskDueDate();
        else if (sortType == Constants.SORT_LIST_PRIORITY)
            return (taskResult.getPriority() == null || taskResult.getPriority().isEmpty()) ? "No Priority" : taskResult.getPriority();
        else return "Unknown";
    }


    private int compareTwoDate(String firstDate, String secondDate) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Date date1 = formatter.parse(firstDate);
            Date date2 = formatter.parse(secondDate);

            return date1.compareTo(date2);

        } catch (ParseException e1) {
            // Catch block will work when one of tasks don't have due date
            if (firstDate.equals(secondDate)) {
                return 0;
            } else if (firstDate.isEmpty() || firstDate.equals("No Date")) { // To add tasks which don't have due date to bottom of list.
                return 1;
            } else {
                return -1;
            }
        }
    }

    void updateTaskStatusInList(HomeAdapter completedTaskAdapter, TaskResult taskResult, int itemPos) {

        if (taskResult.isDone()) {
            //Common transaction
            removeItem(itemPos);
            completedTaskAdapter.addItem(0, taskResult);
            homeViewModel.completedTaskCount.set(homeViewModel.completedTaskCount.get() + 1);

            if (sortType == Constants.SORT_LIST_CUSTOM)
                return;

            String sectionNameOfTask = getSectionNameOfTask(taskResult);

            //To remove in taskMap
            for (int index = 0; index < taskMap.get(sectionNameOfTask).size(); index++) {
                if (taskMap.get(sectionNameOfTask).get(index).getTaskID() == taskResult.getTaskID()) {
                    taskMap.get(sectionNameOfTask).remove(index);
                    break;
                }
            }

            if (taskMap.get(sectionNameOfTask).size() == 0) {
                removeItem(itemPos - 1);
                taskMap.remove(sectionNameOfTask);
            }


        } else {
            completedTaskAdapter.removeItem(itemPos);
            addNewItem(taskResult);

            homeViewModel.completedTaskCount.set(homeViewModel.completedTaskCount.get() - 1);
        }
    }

    ////// ŞUAN BURADA ÇALIŞIYORUM


    // ******************** Functions of SimpleItemTouchHelperCallback E**/

    @Override
    public void onItemDismiss(int position) {
        //mItems.remove(position);
        // notifyItemRemoved(position);
    }


    public interface HomeItemCallback {
        void onShowTaskDetail(TaskResult taskResult);

        void onUpdateTaskDone(TaskResult taskResult, int itemPos);
    }
}
