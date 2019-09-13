package barissaglam.todo.utils;

public class Necessary {
       /* Snackbar snackbar = Snackbar.make(view, "Alarm Removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alarmData.add(position, recoverItem);
                    notifyItemInserted(position);

                }

            }).setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int dismissType) {
                    super.onDismissed(snackbar, dismissType);

                    if(dismissType == DISMISS_EVENT_TIMEOUT || dismissType == DISMISS_EVENT_SWIPE
                            || dismissType == DISMISS_EVENT_CONSECUTIVE || dismissType == DISMISS_EVENT_MANUAL)
                        dataBaseHelper.deleteAlarms(position);

                }
            });*/








           /* IN HOMEADAPTER

    void initCompletedTasks(List<TaskResult> taskResults) {
        this.completedTasks.clear();
        this.completedTasks = taskResults;
        if (taskResults != null && taskResults.size() != 0) {
            addItem("TASK_LIST_HEADER");
            for (TaskResult taskResult : taskResults)
                addItem(taskResult);
        }

    }

    void initNonCompletedTasks(List<TaskResult> taskResults) {
        this.noCompletedTasks.clear();
        this.noCompletedTasks = taskResults;
        if (taskResults != null && taskResults.size() != 0)
            for (TaskResult taskResult : taskResults)
                addItem(taskResult);
    }

    void updateTask(TaskResult taskResult, int itemPos) {
        if (taskResult.isDone()) {
            if (completedTasks != null && completedTasks.size() == 0)
                addItem(noCompletedTasks.size(), "TASK_LIST_HEADER");


            removeItem(itemPos);
            addItem(getItemCount() - completedTasks.size(), taskResult);
            completedTasks.add(0, taskResult);
            noCompletedTasks.remove(itemPos);


        } else {
            removeItem(itemPos);
            addItem(0, taskResult);
            noCompletedTasks.add(0, taskResult);
            completedTasks.remove(itemPos - noCompletedTasks.size());

            if (completedTasks.size() == 0)
                removeItem(noCompletedTasks.size());

        }
    }
*/























    // ******************** List sort functions **/

   /* void sortAllList() {
        Collections.sort(mItems, (o1, o2) -> {
            if (o1 instanceof TaskResult && o2 instanceof TaskResult) {
                TaskResult t1 = (TaskResult) o1;
                TaskResult t2 = (TaskResult) o2;
                return t2.getOrder() - t1.getOrder();
            } else
                return 0;
        });


        Collections.sort(mItems, (o1, o2) -> {
            if (o1 instanceof TaskResult && o2 instanceof TaskResult) {
                TaskResult t1 = (TaskResult) o1;
                TaskResult t2 = (TaskResult) o2;
                return Boolean.compare(t1.isDone(), t2.isDone());
            } else
                return 0;
        });


        notifyDataSetChanged();
    }*/

  /*  void sortByPriority(List<TaskResult> taskResults) {
        hideDetail = true;
        if (taskResults == null || taskResults.size() == 0)
            return;

        clear();
        Collections.sort(taskResults, (t1, t2) -> t2.getPriorityID() - t1.getPriorityID());

        disposable.add(Observable.fromIterable(taskResults)
                .groupBy(TaskResult::getPriority)
                .concatMapSingle(Observable::toList)
                .subscribe(group -> {
                    addItem(group.get(0).getPriority() != null ? group.get(0).getPriority().isEmpty() ? "No Priority" : group.get(0).getPriority() : "No Priority");
                    for (TaskResult taskResult : group) {
                        addItem(taskResult);
                    }
                }));
    }*/

}
