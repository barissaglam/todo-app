package barissaglam.todo.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import barissaglam.todo.R;
import barissaglam.todo.model.result.TaskResult;
import barissaglam.todo.utils.Keys;
import dagger.android.AndroidInjection;
import dagger.android.DaggerBroadcastReceiver;
import timber.log.Timber;

public class ReminderBroadcastManager extends DaggerBroadcastReceiver {
    String CHANNEL_ID = "task_reminder";
    String CHANNEL_NAME = "Reminder Notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AndroidInjection.inject(this, context);

        Bundle bundle = intent.getBundleExtra(Keys.KEY_TASK_RESULT);

        if (bundle != null) {
            TaskResult taskResult = bundle.getParcelable(Keys.KEY_TASK_RESULT);
            if (taskResult == null)
                return;

            showNotification(context, taskResult.getTaskName());
        }

    }


    private void showNotification(Context context, String taskTitle) {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setGroupSummary(true)
                        .setColor(context.getResources().getColor(R.color.colorPrimary))
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(taskTitle)
                        .setContentText(taskTitle)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        //  .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            notificationBuilder.setCategory(Notification.CATEGORY_MESSAGE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());


    }
}