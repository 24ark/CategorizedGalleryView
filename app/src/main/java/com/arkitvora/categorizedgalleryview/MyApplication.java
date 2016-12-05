package com.arkitvora.categorizedgalleryview;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    private static MyApplication sInstance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        sInstance = this;
        context = getApplicationContext();

    }


    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}

