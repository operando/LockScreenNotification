package com.operamdo.lockscreennotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.operamdo.lockscreennotification.utils.IntentUtils;

import java.lang.reflect.Field;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        Notification.Builder builder = new Notification.Builder(
                getApplicationContext());
        builder.setContentIntent(contentIntent);
        builder.setTicker("Ticker");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("ContentTitle");
        builder.setContentText("ContentText");
        builder.setLargeIcon(largeIcon);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);

        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle(builder);
        bigTextStyle.bigText("BigText");
        bigTextStyle.setBigContentTitle("BigContentTitle");
        bigTextStyle.setSummaryText("SummaryText");

        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(0, bigTextStyle.build());

        if (!enableNotificationAccess(NotificationService.class.getName())) {
            IntentUtils.openSettingsNotificationAccess(this);
        }
    }

    private boolean enableNotificationAccess(String componentName) {
        Settings.Secure s = new Settings.Secure();
        Class cls = s.getClass();
        try {
            Field nm = cls.getField("ENABLED_NOTIFICATION_LISTENERS");
            final String flat = Settings.Secure.getString(getContentResolver(),
                    (String) nm.get(s));
            if (flat != null && !"".equals(flat)) {
                final String[] names = flat.split(":");
                for (int i = 0; i < names.length; i++) {
                    final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                    if (cn != null) {
                        if (cn.getClassName().equals(componentName)) {
                            return true;
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onNotificationAccess(View v) {
        IntentUtils.openSettingsNotificationAccess(this);
    }
}
