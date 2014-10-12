package com.operamdo.lockscreennotification;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;


public class LockScreenNoticicationAppWidget extends AppWidgetProvider {

    private static final String TAG = LockScreenNoticicationAppWidget.class.getSimpleName();

    public static String ACTION_NOTIFICATION_UPDATE = LockScreenNoticicationAppWidget.class
            .getPackage().getName() + ".update";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NOTIFICATION_UPDATE);
        context.getApplicationContext().registerReceiver(new NotificationUpdateReceiver(), intentFilter);
    }

    @Override
    public void onDisabled(Context context) {
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lock_screen_noticication_app_widget);
        Intent remoteViewsFactoryIntent = new Intent(context, NotificationWidgetService.class);
        views.setRemoteAdapter(R.id.appwidget_notification_list, remoteViewsFactoryIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static class NotificationUpdateReceiver extends BroadcastReceiver {
        private static final String TGA = NotificationUpdateReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context c, Intent in) {
            Log.d(TAG, in.getAction());

            if (ACTION_NOTIFICATION_UPDATE.equals(in.getAction())) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(c);
                ComponentName cn = new ComponentName(c, LockScreenNoticicationAppWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(cn);
                final int N = appWidgetIds.length;
                for (int i = 0; i < N; i++) {
                    updateAppWidget(c, appWidgetManager, appWidgetIds[i]);
                }
            }
        }
    }
}


