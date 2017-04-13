package com.tkkj.tkeyes.base;

/**
 * Created by YANGB on 2016/4/3.
 */

import android.app.Application;
import android.content.Context;

import com.tkkj.tkeyes.CacheManger.GreenDaoManager;
import com.tkkj.tkeyes.Service.ApplicationManager;
import com.tkkj.tkeyes.bluetooth.CubicBLEDevice;


public class BasicApplication extends Application {

    public ApplicationManager appmanager;


   private AppManager _SpActivityManager = null;
    private static Context mContext;

   @Override
   public void onCreate() {
        super.onCreate();
       this.appmanager = new ApplicationManager(getApplicationContext());
       initData();
   }
    static BasicApplication applicationcontext;
    public BasicApplication(){
        applicationcontext =this;
    }
    public static BasicApplication getInstance(){
        if (applicationcontext==null){
            applicationcontext = new BasicApplication();
        }
        return applicationcontext;
    }

    private void initData() {
        _SpActivityManager = AppManager.getInstance();
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();
    }




//    public AppManager getActivityManager() {
//        return this._SpActivityManager;
//    }

//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//    }

    public static Context getContext() {
        return mContext;
    }

}
