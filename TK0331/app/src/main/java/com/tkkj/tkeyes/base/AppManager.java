package com.tkkj.tkeyes.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Activity的管理和退出
 */
public class AppManager {

    private static AppManager instance;
    private static Stack<Activity> activityStack;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 关闭具体的Activity
     */
    public void finishActivity(Activity activity) {
        if (activityStack != null && !activityStack.isEmpty()) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 关闭除某个Activity以外的所有Activity
     */
    public void finishAllExceptActivity(Class exceptClass) {
        if (activityStack != null && !activityStack.isEmpty()) {
            List<Activity> noCloseActivityList = new ArrayList<Activity>();
            for (Activity activity : activityStack) {
                if (activity.getClass() != exceptClass) {
                    activity.finish();
                } else {
                    noCloseActivityList.add(activity);
                }
            }
            activityStack.clear();
            for (Activity activity : noCloseActivityList) {
                activityStack.add(activity);
            }
            noCloseActivityList.clear();
        }
    }

    /**
     * 关闭所有的Activity
     */
    public void finishAllActivity() {
        if (activityStack != null && activityStack.size()>1) {
            for (Activity activity : activityStack) {
                activity.finish();
            }
            activityStack.clear();
        }
        else{
            return;
        }
    }

    /**
     * 退出应用
     */

    public void exitSystem(Context context) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
        finishAllActivity();
        //保存统计数据
        System.exit(0);
    }

}
