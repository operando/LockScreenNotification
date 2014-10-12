package com.operamdo.lockscreennotification;

import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.text.format.DateUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class NotificationWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NotificationWidgetViewsFactory();
    }

    class NotificationWidgetViewsFactory implements RemoteViewsFactory {

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            return StatusBarNotificationCache.getStatusBarNotification().length;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(getPackageName(), R.layout.list_item_notification);
            StatusBarNotification sn = StatusBarNotificationCache.getStatusBarNotification()[position];
            rv.setTextViewText(R.id.notification_titile, sn.getNotification().tickerText);
            rv.setTextViewText(R.id.notification_date, DateUtils.formatDateTime(getApplicationContext(), sn.getPostTime(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE |
                            DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_YEAR));
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
