package com.tkkj.tkeyes.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;

import butterknife.Unbinder;

/**
 * Created by YANGB on 2016/7/1.
 */
public class BaseFragment extends Fragment {
    public Unbinder unbinder;
    private Activity activity;
    /**
     * 防止内部Handler类引起内存泄露
     */
    protected static class SafeHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public SafeHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
//            tintManager.setStatusBarTintColor(getResources().getColor(R.color.title_bg_color));
            tintManager.setStatusBarTintEnabled(true);
        }
        return view;
    }


    protected Activity getActivityFrag() {
        return activity;
    }


    /*数跳转页面（带参数）*/
    protected void next(Class<?> nextAty, Bundle extras) {
        Intent intent = new Intent(getActivityFrag(), nextAty);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /*数跳转页面（不带参数）*/
    protected void next(Class<?> nextAty) {
        Intent intent = new Intent(getActivityFrag(), nextAty);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // unbinder.unbind();
    }
}
