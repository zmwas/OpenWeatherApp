package com.zack.openweatherapp;

import android.content.Context;

import com.zack.openweatherapp.bookmarkcities.db.AppDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServiceLocatorUtil {
    private  static Context context;

    public ServiceLocatorUtil(Context context) {
        this.context = context;
    }

    public static AppDatabase initializeDb() {
        return AppDatabase.getDatabase(context);
    }
    public static Executor executor = Executors.newSingleThreadExecutor();
}
