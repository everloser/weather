package com.google.everloser12.fishingweather;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

/**
 * Created by al-ev on 29.04.2016.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    public static MyApplication getInstance()
    {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d("Moi", "MyApplication class onCreate");
        myApplication = this;
        Fresco.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
}
