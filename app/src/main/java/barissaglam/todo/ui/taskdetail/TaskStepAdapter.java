package barissaglam.todo.ui.taskdetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import barissaglam.todo.R;
import barissaglam.todo.databinding.ItemTaskStepBinding;
import barissaglam.todo.helper.ItemTouchHelperAdapter;
import barissaglam.todo.model.entities.TaskStepEntity;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.ui.base.adapter.DataBoundAdapter;
import barissaglam.todo.ui.base.adapter.DataBoundViewHolder;

public class TaskStepAdapter extends DataBoundAdapter<ItemTaskStepBinding> implements ItemTouchHelperAdapter {

    private List<TaskStepEntity> taskStepEntityList;
    private StepItemCallback callback;
    private final TaskDetailViewModel taskDetailViewModel;

    public TaskStepAdapter(StepItemCallback callback, TaskDetailViewModel taskDetailViewModel) {
        super(R.layout.item_task_step);
        this.callback = callback;
        this.taskDetailViewModel = taskDetailViewModel;
    }

    @Override
    protected void bindItem(DataBoundViewHolder<ItemTaskStepBinding> holder, int position, List<Object> payloads) {
        holder.binding.setData(taskStepEntityList.get(position));
        holder.binding.setCallback(callback);

    }

    void insertList(List<TaskStepEntity> newTaskStepEntityList) {
        if (taskStepEntityList == null)
            taskStepEntityList = new ArrayList<>();
        taskStepEntityList.addAll(newTaskStepEntityList);
        notifyDataSetChanged();
    }

    void addItem(int position, TaskStepEntity item) {
        taskStepEntityList.add(position, item);
        notifyItemInserted(position);
    }



    void sortAllList() {
        Collections.sort(taskStepEntityList, (o1, o2) -> o2.getOrder() - o1.getOrder());

        Collections.sort(taskStepEntityList, (o1, o2) -> Boolean.compare(o1.isDone(), o2.isDone()));

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskStepEntityList != null ? taskStepEntityList.size() : 0;
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(taskStepEntityList, i, i + 1);

                TaskStepEntity stepEntity = taskStepEntityList.get(i);
                TaskStepEntity stepEntity1 = taskStepEntityList.get(i + 1);
                int order1 = stepEntity.getOrder();
                int order2 = stepEntity1.getOrder();
                stepEntity.setOrder(order2);
                stepEntity1.setOrder(order1);

                taskDetailViewModel.updateTaskStep(stepEntity);
                taskDetailViewModel.updateTaskStep(stepEntity1);

            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(taskStepEntityList, i, i - 1);

                TaskStepEntity stepEntity = taskStepEntityList.get(i);
                TaskStepEntity stepEntity1 = taskStepEntityList.get(i - 1);
                int order1 = stepEntity.getOrder();
                int order2 = stepEntity1.getOrder();
                stepEntity.setOrder(order2);
                stepEntity1.setOrder(order1);

                taskDetailViewModel.updateTaskStep(stepEntity);
                taskDetailViewModel.updateTaskStep(stepEntity1);

            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }


    public interface StepItemCallback {
        void onStepItemClick(TaskStepEntity taskStepEntity);
    }
}
