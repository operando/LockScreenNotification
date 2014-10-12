package com.operamdo.lockscreennotification;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {
    private String TAG = NotificationService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // ステータスバーに通知があった場合
        Log.d(TAG, "onNotificationPosted");
        showLog(sbn); // 通知内容をログに出力する
        setStatusBarNotificationsCache();
        sendIntentnotificationWedget();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // ステータスバーから通知が消された場合
        Log.d(TAG, "onNotificationRemoved");
        showLog(sbn); // 通知内容をログに出力する
        setStatusBarNotificationsCache();
        sendIntentnotificationWedget();
    }

    private void setStatusBarNotificationsCache() {
        StatusBarNotificationCache.setStatusBarNotification(getActiveNotifications());
    }

    private void sendIntentnotificationWedget() {
        Intent i = new Intent(this, LockScreenNoticicationAppWidget.NotificationUpdateReceiver.class);
        i.setAction(LockScreenNoticicationAppWidget.ACTION_NOTIFICATION_UPDATE);
        sendBroadcast(i);
    }

    private void showLog(StatusBarNotification sbn) {
        int id = sbn.getId();
        String name = sbn.getPackageName();
        long time = sbn.getPostTime();
        boolean clearable = sbn.isClearable();
        boolean playing = sbn.isOngoing();
        CharSequence text = sbn.getNotification().tickerText;
        Log.d(TAG, "id:" + id + " name:" + name + " time:" + time);
        Log.d(TAG, "isClearable:" + clearable +
                " isOngoing:" + playing + " tickerText:" + text);
    }
}