package com.tkkj.tkeyes;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tkkj.tkeyes.Service.ApplicationManager;
import com.tkkj.tkeyes.adapter.BAdapter;
import com.tkkj.tkeyes.base.BasicApplication;
import com.tkkj.tkeyes.bluetooth.BLEDevice;
import com.tkkj.tkeyes.bluetooth.CubicBLEDevice;
import com.tkkj.tkeyes.bluetooth.RFStarBLEService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ApplicationManager.RFStarManageListener, BLEDevice.RFStarBLEBroadcastReceiver{


    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private ProgressDialog dialog;
    BasicApplication app;
    private boolean isWrite =false;
    private ListView list = null;
    private BAdapter bleAdapter = null;
    private RadioGroup rgreadOrwrite;
    private ArrayList<BluetoothDevice> arraySource = null;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        requestPermission();
        app = (BasicApplication) getApplication();
        initView();

        //如果有设备正连接，断开连接
        if (app.appmanager.cubicBLEDevice != null) {
            app.appmanager.cubicBLEDevice.disconnectedDevice();
            app.appmanager.cubicBLEDevice = null;
        }
    }

    /*
      校验蓝牙权限
     */
    private void checkBluetoothPermission() {
        context = SearchActivity.this;
        if (Build.VERSION.SDK_INT >= 23) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            } else {
                //具有权限
//                connectBluetooth();
            }
        } else {
            //系统不高于6.0直接执行
//            connectBluetooth();
        }
    }
    /**
     * 权限申请
     */
    private void requestPermission() {
        context = SearchActivity.this;
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
                    Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                }
            }


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE,
                        }, this.REQUEST_LOCATION_PERMISSION);


                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE)) {
                    //判断是否给用户做一个说明
                }
            }
        }
    }

    private void initView() {
        app.appmanager.setRFstarBLEManagerListener(this);
        list = (ListView) findViewById(R.id.list_device_source);
        list.setOnItemClickListener(this);
        arraySource = new ArrayList<BluetoothDevice>();
        bleAdapter = new BAdapter(this,arraySource);
        list.setAdapter(bleAdapter);
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        rgreadOrwrite = (RadioGroup) findViewById(R.id.rg_group);
        rgreadOrwrite.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rb_read){
                    isWrite =false;
                }else if (checkedId==R.id.rb_write){
                    isWrite=true;
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("tag", "onClick: search" );
                app.appmanager.startScanBluetoothDevice();
                app.appmanager.isEdnabled(SearchActivity.this);

                dialog = new ProgressDialog(SearchActivity.this);
                dialog.setMessage("正在搜索");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        app.appmanager.bluetoothDevice = arraySource.get(position);
        app.appmanager.cubicBLEDevice = new CubicBLEDevice(
                this.getApplicationContext(), app.appmanager.bluetoothDevice);
        app.appmanager.cubicBLEDevice.setBLEBroadcastDelegate(SearchActivity.this);
        if (isWrite){
            Intent intent = new Intent(SearchActivity.this,SendActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(SearchActivity.this,ReceiveActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void RFstarBLEManageListener(BluetoothDevice device, int rssi, byte[] scanRecord) {
        arraySource.add(device);
        bleAdapter.notifyDataSetChanged();
    }

    @Override
    public void RFstarBLEManageStartScan() {
        arraySource.clear();
    }

    @Override
    public void RFstarBLEManageStopScan() {
        dialog.dismiss();
    }

    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        String action = intent.getAction();
        this.connectedOrDis(action);
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
            //Log.d(App.TAG, getString(R.string.Connection_Complete));
            Log.d("app search","连接完成");
            dialog.show();
        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
            // Log.d(App.TAG, getString(R.string.connection_break));
            Log.d("app search","连接中断");
            dialog.hide();
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                .equals(action)) {
            dialog.hide();
            this.finish();
        }
        if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
            if (uuid.contains("ffe4")) {
                byte[] data = intent
                        .getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
                try {
                    Log.e("data",new String(data, "GB2312"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                .equals(action)) {

        }
    }

    private void connectedOrDis(String action) {
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
            Log.d("app search","连接完成");

        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
            Log.d("app search","连接中断");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (app.appmanager.cubicBLEDevice != null)
            app.appmanager.cubicBLEDevice.setNotification("ffe0", "ffe4", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (app.appmanager.cubicBLEDevice != null)
        {
            app.appmanager.cubicBLEDevice.setBLEBroadcastDelegate(this);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        arraySource.clear();
        bleAdapter.notifyDataSetChanged();
        app.appmanager.stopScanBluetoothDevice();
    }
}
