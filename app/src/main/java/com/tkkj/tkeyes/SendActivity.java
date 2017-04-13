package com.tkkj.tkeyes;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tkkj.tkeyes.CacheManger.CacheManager;
import com.tkkj.tkeyes.base.BasicApplication;
import com.tkkj.tkeyes.bluetooth.BLEDevice;
import com.tkkj.tkeyes.bluetooth.RFStarBLEService;
import com.tkkj.tkeyes.utils.DataEncryptUtil;
import com.tkkj.tkeyes.utils.GlobalObject;
import com.tkkj.tkeyes.utils.StrToCharArry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SendActivity extends AppCompatActivity implements BLEDevice.RFStarBLEBroadcastReceiver {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private BasicApplication app;
    private EditText editText;
    private Button btnsend;
    private Context context;


    private ArrayList<String> listData;//等待发送到设备的数组
    private BroadcastReceiver freshRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        requestPermission();

        app = (BasicApplication) getApplication();
        initView();
        registerRec();
    }


    /**
     * 权限申请
     */
    private void requestPermission() {
        context = SendActivity.this;
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

    private void initView() {

        listData = new ArrayList<>();
        Log.e("tag", "" + app.appmanager.bluetoothDevice.getAddress());
        editText = (EditText) findViewById(R.id.ed_send);
        btnsend = (Button) findViewById(R.id.btn_send);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (editText.getText()!=null&&!editText.getText().toString().trim().isEmpty()){
                    String trim = editText.getText().toString().trim();

                }else {
                    editText.setError("发送数据不能为空");
                    editText.requestFocus();
                    return;
                }*/

                if (app.appmanager.cubicBLEDevice != null) {

                    writeData();
                    //app.appmanager.cubicBLEDevice.writeValue("ffe5", "ffe9", trim.getBytes());
                }
            }
        });
    }

    private void writeData() {
        String value = CacheManager.getData(DataEncryptUtil.getUserId());
        try {
            JSONArray jsonArray = new JSONArray(value);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                listData.add(json.getString("data"));
            }

            StrToCharArry obj = new StrToCharArry();
//            byte[] b   = obj.StrToCharArry(jsonArray.getJSONObject(0).getString("data"));
            ArrayList<Integer> b=obj.StrToCharArry(jsonArray.getJSONObject(0).getString("data"));
            int len = b.size();
            byte[] b_1 = new byte[len];
            for(int i = 0; i<len; i++){
                int alEach = (int)b.get(i);
                b_1[i] = (byte)alEach;
            }
            Log.e("tag", Arrays.toString(b_1));
//            byte[] str =new byte[10] ;
//            for(int i = 0;i < 10; i++){
//                str[i] = (byte)(66+i);
//            }
            Log.e("tag", jsonArray.getJSONObject(0).getString("data"));
//            Log.e("tag", str.toString());
//            app.appmanager.cubicBLEDevice.writeValue("ffe5", "ffe9", str);
           app.appmanager.cubicBLEDevice.writeValue("ffe5", "ffe9", b_1);
//            app.appmanager.cubicBLEDevice.writeValue("ffe5", "ffe9", jsonArray.getJSONObject(0).getString("data")+",XX");
            Thread.sleep(100);
           // Log.e("tag", Arrays.toString("XX".getBytes()));
//            app.appmanager.cubicBLEDevice.writeValue("ffe5", "ffe9", "XX".getBytes());
            app.appmanager.cubicBLEDevice.writeValue("ffe5", "ffe9", "XX");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(intent.getAction())) {
            Log.e("Send", "连接");

        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(intent.getAction())) {
            Log.e("Send", "连接断开");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (app.appmanager.cubicBLEDevice != null) {
            app.appmanager.cubicBLEDevice.setBLEBroadcastDelegate(this);
        }
    }


    /**
     * 注册广播
     * */
    private void registerRec() {
        if (freshRec == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(GlobalObject.GLOBAL_BROADCAST);
            freshRec = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent != null) {
                        Log.e("tag","SendActivity：是否发送成功? :"+intent.getBooleanExtra("isSuccess", false)+"");
                        if (intent.getBooleanExtra("isSuccess", false)) {

                        }
                    }
                }
            };

            registerReceiver(freshRec, filter);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(freshRec);
    }
}
