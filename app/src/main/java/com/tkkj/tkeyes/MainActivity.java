package com.tkkj.tkeyes;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tkkj.tkeyes.adapter.MainAdapter;
import com.tkkj.tkeyes.base.BasicActivity;
import com.tkkj.tkeyes.model.DetailItem;
import com.tkkj.tkeyes.model.OperationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




public class MainActivity extends BasicActivity {

    // 注意：控件的修饰类型不能是：private 或 static
    // 否则会报错误： @BindView fields must not be private or static.
    @BindView(R.id.imgBtn_back)
    ImageButton imgBtnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.divider_line_mine)
    View dividerLineMine;
    @BindView(R.id.list_set)
    ListView listSet;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;


    private BluetoothGatt mBluetoothGatt;
    private Context context;
    private ListView listView;
    private BluetoothDevice bluetoothDevice;


    private String[] title = {"启动设备", "干预设备", "测试设备", "连接设备", "联系我们"};

    private ArrayList<OperationModel> list;
//    private SearchResult mResult;
    private BluetoothDevice mDevice;
    private boolean mConnected;

    private BroadcastReceiver freshRec;

    private DetailItem commItem, exchangeItem;//标记串口数据通道和蓝牙数据通道的item

    private List<DetailItem> deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //必须在setContentView()之后使用

        ButterKnife.bind(this);
        setTitle("功能操作");
        initData();

//        registerRec();
    }

    private void initData() {
        context = MainActivity.this;
        list = new ArrayList<>();
        deviceInfo = new ArrayList<DetailItem>();
        Intent intent = getIntent();
        String mac = intent.getStringExtra("mac");
//        mResult = intent.getParcelableExtra("device");
        tvTitle.setText("操作设备");

//        ClientManager.getClient().registerConnectStatusListener(mDevice.getAddress(), mConnectStatusListener);

//        connectDeviceIfNeeded();

        for (int i = 0; i < title.length; i++) {
            OperationModel model = new OperationModel();
            model.setTitle(title[i]);
            model.setCheck(0);
            list.add(model);
        }

        MainAdapter adapter = new MainAdapter(MainActivity.this, list);
        listSet.setAdapter(adapter);


    }


    @OnClick(R.id.imgBtn_back)
    public void onViewClicked() {
        finish();
    }
}

//    private void connectDevice() {
//        DialogUtil.getInstance().waitDialog(context, "正在连接设备");
//
//        BleConnectOptions options = new BleConnectOptions.Builder()
//                .setConnectRetry(3)
//                .setConnectTimeout(20000)
//                .setServiceDiscoverRetry(3)
//                .setServiceDiscoverTimeout(10000)
//                .build();
//
//        ClientManager.getClient().connect(mDevice.getAddress(), options, new BleConnectResponse() {
//            @Override
//            public void onResponse(int code, BleGattProfile profile) {
//                BluetoothLog.v(String.format("profile:\n%s", profile));
//                DialogUtil.getInstance().hideWait();
//                deviceInfo = BluetoothUtil.GattProfile(profile);
//                commItem = BluetoothUtil.getDetailItem(deviceInfo,"0000ffe5-0000-1000-8000-00805f9b34fb");


//                write serviceUUID:0000ffe9-0000-1000-8000-00805f9b34fb
//                CharacteristicUUID：0000ffe5-0000-1000-8000-00805f9b34fb
//                commItem = BluetoothUtil.getDetailItem(deviceInfo, "FFE9");
//                exchangeItem = BluetoothUtil.getDetailItem(deviceInfo,"FFE9");
//                SecureConnector.processStep1(mDevice);
//
//
//
//            }
//        });


//    }

//    private void connectDeviceIfNeeded() {
//        if (!mConnected) {
//            connectDevice();
//        }
//    }

//    private void registerRec() {
//        if (freshRec == null) {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(GlobalObject.GLOBAL_BROADCAST);
//            freshRec = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {

//                    if (intent != null && intent.getIntExtra("msg", 0) != 0) {
//                        switch (intent.getIntExtra("msg", 0)) {
//                            case GlobalObject.TEST_DEVICE:
//                                Log.v("tag", "测试设备");
//                                if (commItem != null) {
//                                    ClientManager.getClient().notify(mDevice.getAddress(), commItem.service, commItem.uuid, mNotifyRsp);
//                                    ClientManager.getClient().write(mDevice.getAddress(), commItem.service, commItem.uuid,
//                                            "write测试语句".getBytes(), mWriteRsp);
//                                }
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }
//            };
//
//            registerReceiver(freshRec, filter);
//        }

//                }


                //    @Override
//                protected void onDestroy() {
//        ClientManager.getClient().disconnect(mDevice.getAddress());
//        ClientManager.getClient().unregisterConnectStatusListener(mDevice.getAddress(), mConnectStatusListener);
//        if (freshRec != null) {
//            unregisterReceiver(freshRec);
//            freshRec = null;
//        }
//        super.onDestroy();
//                }


                /**
                 * 取消蓝牙连接
                 */
//                private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
//                    @Override
//                    public void onConnectStatusChanged(String mac, int status) {
//                        BluetoothLog.v(String.format("CharacterActivity.onConnectStatusChanged status = %d", status));

//                        if (status == STATUS_DISCONNECTED) {
//                            DialogUtil.getInstance().toast(context, "disconnected");

//                        }
//                    }
//                };


//                private final BleReadResponse mReadRsp = new BleReadResponse() {
//                    @Override
//                    public void onResponse(int code, byte[] data) {
//            if (code == REQUEST_SUCCESS) {
//                DialogUtil.getInstance().toast(context, "success");
//            } else {
//                DialogUtil.getInstance().toast(context, "failed");
//
//            }
//                    }
//                };

//                private final BleWriteResponse mWriteRsp = new BleWriteResponse() {
//                    @Override
//                    public void onResponse(int code) {
//            if (code == REQUEST_SUCCESS) {
//                DialogUtil.getInstance().toast(context, "success");
//            } else {
//                DialogUtil.getInstance().toast(context, "failed");
//            }
//                    }
//                };

//    private final BleNotifyResponse mNotifyRsp = new BleNotifyResponse() {
//        @Override
//        public void onNotify(UUID service, UUID character, byte[] value) {
//            if (service.equals(commItem.service) && character.equals(commItem.uuid)) {
//                BluetoothTools  bluetoothTools = new BluetoothTools();
//                bluetoothTools.sendMessage(context,bluetoothDevice,"Hello");
//                Log.e("tag", "onNotify: " + "write Hello" );
//            }
//        }

//        @Override
//        public void onResponse(int code) {
//            if (code == REQUEST_SUCCESS) {
//                DialogUtil.getInstance().toast(context, "success");
//            } else {
//                DialogUtil.getInstance().toast(context, "failed");
//            }
//        }
//    };

//    private final BleUnnotifyResponse mUnnotifyRsp = new BleUnnotifyResponse() {
//        @Override
//        public void onResponse(int code) {
//            if (code == REQUEST_SUCCESS) {
//                DialogUtil.getInstance().toast(context, "success");
//            } else {
//                DialogUtil.getInstance().toast(context, "failed");
//            }
//        }
//    };
//            }
//        }
//}
