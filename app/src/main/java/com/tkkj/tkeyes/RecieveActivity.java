package com.tkkj.tkeyes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tkkj.tkeyes.base.BasicApplication;
import com.tkkj.tkeyes.bluetooth.BLEDevice;
import com.tkkj.tkeyes.bluetooth.RFStarBLEService;

import java.io.UnsupportedEncodingException;

public class RecieveActivity extends AppCompatActivity implements BLEDevice.RFStarBLEBroadcastReceiver {


    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private TextView mtv;
    private Button mbtn;
    BasicApplication app;
    Context context;
    boolean flag= true;//默认为开始
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
        requestPermission();
        app = (BasicApplication) getApplication();
        Log.e("onCreate",app.appmanager.bluetoothDevice.getName());
        Log.e("onCreate",app.appmanager.cubicBLEDevice!=null?"notnull":"null");

        initView();
    }

    private void initView() {
        mtv = (TextView) findViewById(R.id.getDataView1);
        mbtn = (Button) findViewById(R.id.btn_receive);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    flag = true;
                    if (app.appmanager.cubicBLEDevice != null)
                        app.appmanager.cubicBLEDevice.setNotification("ffe0", "ffe4", true);
                } else {
                    flag = false;
                    finish();
                }
            }
        });
    }

    /**
     * 权限申请
     */
    private void requestPermission() {
        context = RecieveActivity.this;
        //权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Android M 处理Runtime Permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        this.REQUEST_LOCATION_PERMISSION);


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //判断是否给用户做一个说明
                }
            }


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE,
                        },
                        this.REQUEST_LOCATION_PERMISSION);


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE)) {
                    //判断是否给用户做一个说明
                }
            }
        }
    }
    StringBuilder str = new StringBuilder();
    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(intent.getAction())) {
            if (uuid.contains("ffe4")) {
                byte[] data = intent
                        .getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
                Log.e("data",data.toString());
                try {
                    String string = new String(data, "GB2312");
                    str.append(string);

                    mtv.setText(str);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                .equals(intent.getAction())) {

        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (app.appmanager.cubicBLEDevice != null)
            app.appmanager.cubicBLEDevice.setBLEBroadcastDelegate(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (app.appmanager.cubicBLEDevice != null)
            app.appmanager.cubicBLEDevice.setNotification("ffe0", "ffe4",
                    false);
    }
}
