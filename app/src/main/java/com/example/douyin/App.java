package com.example.douyin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class App extends Application {
    public static String ip = "wenl121.cn";
    public static String user = "user";
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }
}
