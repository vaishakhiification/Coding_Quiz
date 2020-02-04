package com.vaishakhiification.mycodingquiz;

import android.view.View;

public class Timer {
    private int ms = 0;
    boolean running;
    boolean wasRunning;

    Timer() {
        running = true;
        wasRunning = true;
    }

    public void timerStart(View view) {
        running = true;
    }

    public void timerStop(View view) {
        running = false;
    }

    public void timerReset(View view) {
        running = false;
        ms = 0;
    }

    int getHours() {
        return this.ms / 3600;
    }

    int getMinutes() {
        return (this.ms % 3600) / 60;
    }

    int getMs() {
        return this.ms % 60;
    }


}
