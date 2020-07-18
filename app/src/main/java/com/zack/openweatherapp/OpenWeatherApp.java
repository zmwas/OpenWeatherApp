package com.zack.openweatherapp;

import android.app.Application;

public class OpenWeatherApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceLocatorUtil serviceLocatorUtil = new ServiceLocatorUtil(this);

    }
}
