package br.com.sailboat.flashcards.interactor;

public class CardMetricsLoader {

//    public static int getAmountOfDoneTasks(Context context, TaskHistoryFilter filter) throws Exception {
//        TaskHistorySQLite taskSQLite = TaskHistorySQLite.newInstance(context);
//        return taskSQLite.getAmountOfDoneTasks(filter);
//    }
//
//    public static int getAmountOfDoneTasks(Context context, long taskId) throws Exception {
//        TaskHistorySQLite taskSQLite = TaskHistorySQLite.newInstance(context);
//        return taskSQLite.getAmountOfDoneTasks(taskId);
//    }
//
//    public static int getAmountOfNotDoneTasks(Context context, TaskHistoryFilter filter) throws Exception {
//        TaskHistorySQLite taskSQLite = TaskHistorySQLite.newInstance(context);
//        return taskSQLite.getAmountOfNotDoneTasks(filter);
//    }
//
//    public static int getAmountOfNotDoneTasks(Context context, long taskId) throws Exception {
//        TaskHistorySQLite taskSQLite = TaskHistorySQLite.newInstance(context);
//
//        return taskSQLite.getAmountOfNotDoneTasks(taskId);
//    }
//
//    public static int getFire(Context context, long taskId) throws Exception {
//        List<TaskHistory> history = TaskHistorySQLite.newInstance(context).getTaskHistory(taskId);
//
//        int fire = 0;
//
//        if (!history.isEmpty()) {
//
//            for (TaskHistory h : history) {
//                if (h.getStatus() == TaskStatus.DONE.getIndex()) {
//                    fire++;
//                } else {
//                    break;
//                }
//            }
//
//        }
//
//        return fire;
//    }
//
//    public static TaskMetrics getMetrics(Context ctx, long taskId) throws Exception {
//        TaskMetrics taskMetrics = new TaskMetrics();
//        taskMetrics.setDoneTasks(getAmountOfDoneTasks(ctx, taskId));
//        taskMetrics.setNotDoneTasks(getAmountOfNotDoneTasks(ctx, taskId));
//        taskMetrics.setFire(getFire(ctx, taskId));
//
//        return taskMetrics;
//    }
//
//    public static TaskMetrics getMetrics(Context ctx, TaskHistoryFilter filter) throws Exception {
//        TaskMetrics taskMetrics = new TaskMetrics();
//        taskMetrics.setDoneTasks(getAmountOfDoneTasks(ctx, filter));
//        taskMetrics.setNotDoneTasks(getAmountOfNotDoneTasks(ctx, filter));
//
//        return taskMetrics;
//    }



}
