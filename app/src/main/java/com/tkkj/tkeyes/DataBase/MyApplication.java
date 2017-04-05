package com.tkkj.tkeyes.DataBase;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by TKKJ on 2017/4/5.
 */

public class MyApplication extends Application{
    private static MyApplication instance;
    private static Context mContext;

    public static Application getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return super.getApplicationInfo();
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        instance = this;
//        BluetoothContext.set(this);


        /**
         *  greenDao全局配置,只希望有一个数据库操作对象
         */
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();

    }
}
