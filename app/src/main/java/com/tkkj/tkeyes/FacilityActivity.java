package com.tkkj.tkeyes;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.tkkj.tkeyes.adapter.DeviceListAdapter;
import com.tkkj.tkeyes.base.BasicActivity;
import com.tkkj.tkeyes.bluetoothutil.BluetoothUtil;
import com.tkkj.tkeyes.bluetoothutil.ClientManager;
import com.tkkj.tkeyes.utils.AppConstants;
import com.tkkj.tkeyes.utils.DialogUtil;
import com.tkkj.tkeyes.utils.OnDialogUtilListener;
import com.tkkj.tkeyes.view.PullRefreshListView;
import com.tkkj.tkeyes.view.PullToRefreshFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.bluetooth.BluetoothDevice.BOND_BONDED;
import static android.bluetooth.BluetoothDevice.BOND_BONDING;

/**
 * Created by TKKJ on 2017/3/25
 */

public class FacilityActivity extends BasicActivity {


    private final static int REQUEST_ENABLE_BT = 1;
    private final static int BLUTOOTH_ENABLE = 2;
    private final static int BLUTOOTH_DISENABLE = 3;


    private final static int REQUEST_LOCATION_PERMISSION = 11;
    @BindView(R.id.imgBtn_back)
    ImageButton imgBtnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pulllayout)
    PullToRefreshFrameLayout mRefreshLayout;

    private Context context;


    private PullRefreshListView mListView;
    private DeviceListAdapter mAdapter;
    private List<SearchResult> mDevices;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case BLUTOOTH_ENABLE:
                    searchDevice();
                    break;
                case BLUTOOTH_DISENABLE:
                    startBlueTooth();
                    break;
            }
            super.handleMessage(msg);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);
        ButterKnife.bind(this);

        requestPermission();
        initData();
    }


    /**
     *  权限申请
     */
    private void requestPermission() {
        context = FacilityActivity.this;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    this.REQUEST_LOCATION_PERMISSION);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //判断是否跟用户做一个说明
                DialogUtil.getInstance().toast
                        (getApplicationContext(), "需要蓝牙权限");
            }
        }
    }
    private void initData() {

        tvTitle.setText("查找蓝牙设备");
        mDevices = new ArrayList<SearchResult>();

        mListView = mRefreshLayout.getPullToRefreshListView();
        mAdapter = new DeviceListAdapter(this);
        mListView.setAdapter(mAdapter);

        mListView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                searchDevice();
            }

        });

        if (BluetoothUtil.isBluetoothSupported()) {
            if (!BluetoothUtil.isBluetoothEnabled()) {
                mHandler.sendEmptyMessage(BLUTOOTH_DISENABLE);
            } else {
                mHandler.sendEmptyMessage(BLUTOOTH_ENABLE);
            }

        } else {
            DialogUtil.getInstance().errmessage(context, "设备不支持蓝牙", "");
        }
    }

    @OnClick(R.id.imgBtn_back)
    public void onClick() {
        finish();
    }


    private void searchDevice() {

        DialogUtil.getInstance().waitDialog(context,"正在搜索蓝牙设备");
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(5000, 2)
                .searchBluetoothClassicDevice(3000, 2).
                        build();

        ClientManager.getClient().search(request, mSearchResponse);
        DialogUtil.getInstance().hideWait();

    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            BluetoothLog.d("MainActivity.onSearchStarted");
            mListView.onRefreshComplete(true);
            mRefreshLayout.showState(AppConstants.LIST);
            mDevices.clear();
        }

        @Override
        public void onDeviceFounded(SearchResult device) {
//            BluetoothLog.w("MainActivity.onDeviceFounded " + device.device.getAddress());
            if (!mDevices.contains(device)) {
                mDevices.add(device);
                mAdapter.setDataList(mDevices);
            }

            if (mDevices.size() > 0) {
                mRefreshLayout.showState(AppConstants.LIST);
            }


        }

        @Override
        public void onSearchStopped() {
            BluetoothLog.w("MainActivity.onSearchStopped");
            mListView.onRefreshComplete(true);
            mRefreshLayout.showState(AppConstants.LIST);

        }

        @Override
        public void onSearchCanceled() {
            BluetoothLog.w("MainActivity.onSearchCanceled");
            mListView.onRefreshComplete(true);
            mRefreshLayout.showState(AppConstants.LIST);

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        ClientManager.getClient().stopSearch();
    }


    private void startBlueTooth() {
        DialogUtil.getInstance().warning(context, "蓝牙未打开", "现在打开蓝牙", new OnDialogUtilListener() {
            @Override
            public void onConfirm() {
                super.onConfirm();
                DialogUtil.getInstance().waitDialog(context, "正在打开蓝牙");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 设置蓝牙可见性，最多100秒
                enableBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 100);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                DialogUtil.getInstance().hideWait();

            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //判断是不是启动蓝牙的结果

        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode != RESULT_OK) {
                    DialogUtil.getInstance().errmessage(context, "打开蓝牙失败", "");
                } else {
                    mHandler.sendEmptyMessage(BLUTOOTH_ENABLE);
                }
                break;
        }
    }

    /**
     * 监听设备配对状态变化
     * */
    private final BluetoothBondListener mBluetoothBondListener = new BluetoothBondListener() {
        @Override
        public void onBondStateChanged(String mac, int bondState) {
//             bondState = Constants.BOND_NONE, BOND_BONDING, BOND_BONDED;

        }
    };

    /**
     * Beacon解析:
     * 可以在广播中携带设备的自定义数据，用于设备识别，数据广播，事件通知等，
     * 这样手机端无需连接设备就可以获取设备推送的数据。
     * */
}