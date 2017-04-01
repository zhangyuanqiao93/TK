package com.tkkj.tkeyes.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tkkj.tkeyes.R;


/**
 * Created by YANGB on 2016/8/17.
 */
public class BaseAcitivty extends Activity {
    private ServiceConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
//        PushAgent.getInstance(getApplicationContext()).onAppStart();
      /*  mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //playerBar();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };*/
    }

    protected void next(Class<?> nextAty) {
        Intent intent = new Intent(this, nextAty);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//刷新
        startActivity(intent);
    }


    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        DialogUtil.getInstance().hideWait();
        // unbinder.unbind();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        //友盟统计
//        MobclickAgent.onResume(getApplicationContext());

    }
    @Override
    public void finish() {
        this.overridePendingTransition(R.anim.activity_from_in, R.anim.activity_from_out);
//        DialogUtil.getInstance().hideWait();
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

//        DialogUtil.getInstance().forceClose();
        //AppManager.getInstance().finishActivity(this);
        super.finish();
    }
}
