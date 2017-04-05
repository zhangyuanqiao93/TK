package com.tkkj.tkeyes.DataBase;

import android.app.Application;
import android.content.Context;

/**
 * Created by TKKJ on 2017/4/5.
 */
public class BasicApplication extends MyApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static Context getContext() {
        return context;
    }
}
