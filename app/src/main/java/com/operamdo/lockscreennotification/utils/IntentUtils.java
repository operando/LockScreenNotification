package com.operamdo.lockscreennotification.utils;

import android.content.Context;
import android.content.Intent;

public final class IntentUtils {

    private IntentUtils() {
    }

    public static void openSettingsNotificationAccess(Context c) {
        Intent i = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        c.startActivity(i);
    }
}
