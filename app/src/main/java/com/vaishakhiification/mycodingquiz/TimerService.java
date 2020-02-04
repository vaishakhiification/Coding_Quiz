package com.vaishakhiification.mycodingquiz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class TimerService extends Service {
    private final IBinder binder = new TimerBinder();
    private static final int NOTIFICATION_ID = 1;

    private int ms_1;
    private boolean wasRunning_1 = true;
    private boolean running_1 = true;

    private int ms_2;
    private boolean wasRunning_2 = true;
    private boolean running_2 = true;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        boolean reset = intent.getBooleanExtra("reset", false);
        if (reset) {
            ms_1 = 0;
            ms_2 = 0;
        }
        running_1 = true;
        running_2 = true;
        return binder;
    }

    public class TimerBinder extends Binder {
        TimerService getTimer() {
            return TimerService.this;
        }
    }

    int getHours_1() {
        return this.ms_1 / 3600;
    }

    int getMinutes_1() {
        return (this.ms_1 % 3600) / 60;
    }

    int getMs_1() {
        return this.ms_1 % 60;
    }

    boolean getRunning_1() {
        return this.running_1;
    }

    boolean getWasRunning_1() {
        return this.wasRunning_1;
    }

    void setMs_1(int ms) {
        this.ms_1 = ms;
    }

    void setWasRunning_1(boolean wasRunning) {
        this.wasRunning_1 = wasRunning;
    }

    void setRunning_1(boolean running) {
        this.running_1 = running;
    }

    void incrementMs_1() {
        this.ms_1++;
    }

    int getHours_2() {
        return this.ms_2 / 3600;
    }

    int getMinutes_2() {
        return (this.ms_2 % 3600) / 60;
    }

    int getMs_2() {
        return this.ms_2 % 60;
    }

    boolean getRunning_2() {
        return this.running_2;
    }

    boolean getWasRunning_2() {
        return this.wasRunning_2;
    }

    void setMs_2(int ms) {
        this.ms_2 = ms;
    }

    void setWasRunning_2(boolean wasRunning) {
        this.wasRunning_2 = wasRunning;
    }

    void setRunning_2(boolean running) {
        this.running_2 = running;
    }

    void incrementMs_2() {
        this.ms_2++;
    }

    void timeOut(){
        Toast.makeText(getApplicationContext(), getString(R.string.time_up), Toast.LENGTH_LONG).show();
        displayNotification();
    }

    private void displayNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.time_up))
                .setContentText(getString(R.string.tap_here))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0, 1000})
                .setAutoCancel(true);
        Intent actionIntent = new Intent(this, ResultActivity.class);
        actionIntent.putExtra("passed", false);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(
                this,
                0,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(actionPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
