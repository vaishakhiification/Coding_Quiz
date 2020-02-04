package com.vaishakhiification.mycodingquiz;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    private String temp = "PRINT";
    private final IBinder binder = new MyServiceBinder();

    public class MyServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String getTemp() {
        return temp;
    }
}
