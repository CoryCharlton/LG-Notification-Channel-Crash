package com.ccswe.lgnotificationchannelcrash;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOTIFICATION_CHANNEL_GROUP_ID = "lgnotificationchannelcrash_notification_channel_group_testing";
    public static final String NOTIFICATION_CHANNEL_ID_BASE = "lgnotificationchannelcrash_notification_channel_testing_";

    private final Random _random = new Random();

    private void createNotificationChannel(boolean useEmptyArrayForVibrationPattern) {
        final int id = _random.nextInt();
        final NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID_BASE + id, "Test channel #" + id, NotificationManager.IMPORTANCE_MIN);

        if (useEmptyArrayForVibrationPattern) {
            notificationChannel.setVibrationPattern(new long[] {});
        } else {
            notificationChannel.setVibrationPattern(new long[] {0});
        }

        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(false);
        notificationChannel.setBypassDnd(true);
        notificationChannel.setDescription(getString(R.string.app_name));
        notificationChannel.setGroup(NOTIFICATION_CHANNEL_GROUP_ID);
        notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setShowBadge(false);
        notificationChannel.setSound(null, null);

        final NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotificationChannelGroup() {
        final NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(NOTIFICATION_CHANNEL_GROUP_ID, "Test group name");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            notificationChannelGroup.setDescription("Test group description");
        }

        final NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannelGroup(notificationChannelGroup);
        }
    }

    private void deleteNotificationChannels() {
        final NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager == null) {
            return;
        }

        final List<NotificationChannel> notificationChannels = notificationManager.getNotificationChannels();
        for (NotificationChannel notificationChannel : notificationChannels) {
            notificationManager.deleteNotificationChannel(notificationChannel.getId());
        }

        final List<NotificationChannelGroup> notificationChannelGroups = notificationManager.getNotificationChannelGroups();
        for (NotificationChannelGroup notificationChannelGroup : notificationChannelGroups) {
            notificationManager.deleteNotificationChannelGroup(notificationChannelGroup.getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_create_bad_notification_channel).setOnClickListener(this);
        findViewById(R.id.button_create_good_notification_channel).setOnClickListener(this);
        findViewById(R.id.button_open_notification_settings).setOnClickListener(this);

        deleteNotificationChannels();
        createNotificationChannelGroup();
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }

        final int id = v.getId();
        switch (id) {
            case R.id.button_create_bad_notification_channel:
            case R.id.button_create_good_notification_channel:
                createNotificationChannel(v.getId() == R.id.button_create_bad_notification_channel);
                break;
            case R.id.button_open_notification_settings:
                startNotificationSettings();
                break;
        }
    }

    private void startNotificationSettings() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());

        startActivity(intent);
    }
}
