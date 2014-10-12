package com.operamdo.lockscreennotification;

import android.service.notification.StatusBarNotification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class StatusBarNotificationCache {

    @Getter
    @Setter
    private static StatusBarNotification[] statusBarNotification;
}
