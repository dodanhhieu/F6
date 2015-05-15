package com.vn.hm.calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.vn.hm.MainActivity;
import com.vn.hm.R;

public class AlarmRecerver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	createNotification(context, intent);
    }

    private void createNotification(Context context, Intent intent) {
	NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
		context).setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(intent.getStringExtra("title"))
		.setContentText(intent.getStringExtra("desc"))
		.setAutoCancel(true);

	Intent resultIntent = new Intent(context, MainActivity.class);

	TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
	stackBuilder.addParentStack(MainActivity.class);
	stackBuilder.addNextIntent(resultIntent);

	PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
		PendingIntent.FLAG_UPDATE_CURRENT);
	mBuilder.setContentIntent(resultPendingIntent);

	NotificationManager mNotificationManager = (NotificationManager) context
		.getSystemService(Context.NOTIFICATION_SERVICE);
	Notification notification = mBuilder.build();
	notification.defaults = Notification.DEFAULT_ALL;
	mNotificationManager.notify(1, notification);
    }

}