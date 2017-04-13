//package com.tkkj.tkeyes;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.tkkj.tkeyes.base.BasicActivity;
//import com.tkkj.tkeyes.view.PullRefreshListView;
//import com.tkkj.tkeyes.view.PullToRefreshFrameLayout;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by TKKJ on 2017/3/25
// */
//
//public class FacilityActivity extends BasicActivity {
//
//
//    private final static int REQUEST_ENABLE_BT = 1;
//    private final static int BLUTOOTH_ENABLE = 2;
//    private final static int BLUTOOTH_DISENABLE = 3;
//
//
//    private final static int REQUEST_LOCATION_PERMISSION = 11;
//    @BindView(R.id.imgBtn_back)
//    ImageButton imgBtnBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.pulllayout)
//    PullToRefreshFrameLayout mRefreshLayout;
//
//    private Context context;

//
//    private PullRefreshListView mListView;
//    private DeviceListAdapter mAdapter;
//    private List<SearchResult> mDevices;

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            switch (msg.what) {
//                case BLUTOOTH_ENABLE:
//                    searchDevice();
//                    break;
//                case BLUTOOTH_DISENABLE:
//                    startBlueTooth();
//                    break;
//            }
//            super.handleMessage(msg);
//
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_facility);
//        ButterKnife.bind(this);
//
//        requestPermission();//判断权限
//        initData();//初始化操作处理
//    }


    /**
     * 权限申请
     */
//    private void requestPermission() {
//        context = FacilityActivity.this;
//        //权限申请
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Android M 处理Runtime Permission
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED) {
//                //请求权限
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//                                Manifest.permission.ACCESS_FINE_LOCATION},
//                        this.REQUEST_LOCATION_PERMISSION);
//
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    //判断是否给用户做一个说明
//                }
//            }
//
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                //请求权限
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_PHONE_STATE,
//                        },
//                        this.REQUEST_LOCATION_PERMISSION);
//
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.READ_PHONE_STATE)) {
//                    //判断是否给用户做一个说明
//                }
//            }
//        }
//    }

//    private void initData() {

//        tvTitle.setText("查找蓝牙设备");
//        mDevices = new ArrayList<SearchResult>();

//        mListView = mRefreshLayout.getPullToRefreshListView();
//        mAdapter = new DeviceListAdapter(this);
//        mListView.setAdapter(mAdapter);

//        mListView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
//
//            @Override
//            public void onRefresh() {
//                // TODO Auto-generated method stub
//                searchDevice();
//            }

//        });

//        if (BluetoothUtil.isBluetoothSupported()) {
//            if (!BluetoothUtil.isBluetoothEnabled()) {
//                mHandler.sendEmptyMessage(BLUTOOTH_DISENABLE);
//            } else {
//                mHandler.sendEmptyMessage(BLUTOOTH_ENABLE);
//            }
//        } else {
//            DialogUtil.getInstance().errmessage(context, "设备不支持蓝牙", "");
//        }
//    }

//    @OnClick(R.id.imgBtn_back)
//    public void onClick() {
//        finish();
//    }


        //搜索设备

//    private void searchDevice() {
//
//        DialogUtil.getInstance().waitDialog(context, "正在搜索蓝牙设备");
//        SearchRequest request = new SearchRequest.Builder()
//                .searchBluetoothLeDevice(5000, 2)
//                .searchBluetoothClassicDevice(2000, 2).
//                        build();
//        ClientManager.getClient().search(request, mSearchResponse);
//    }

//    private final SearchResponse mSearchResponse = new SearchResponse() {
//        @Override
//        public void onSearchStarted() {
//            BluetoothLog.v("MainActivity.onSearchStarted");
//            mListView.onRefreshComplete(true);
//            mRefreshLayout.showState(AppConstants.LIST);
//            mDevices.clear();
//        }

//        @Override
//        public void onDeviceFounded(SearchResult device) {
//            BluetoothLog.v("MainActivity.onDeviceFounded " + device.device.getAddress());
//            if (!mDevices.contains(device)) {
//                mDevices.add(device);
//                mAdapter.setDataList(mDevices);
//            }
//            if (mDevices.size() > 0) {
//                mRefreshLayout.showState(AppConstants.LIST);
//            }
//        }

//        @Override
//        public void onSearchStopped() {
//            BluetoothLog.v("MainActivity.onSearchStopped");
//            mListView.onRefreshComplete(true);
//            mRefreshLayout.showState(AppConstants.LIST);
//            DialogUtil.getInstance().hideWait();
//        }
//
//        @Override
//        public void onSearchCanceled() {
//            BluetoothLog.v("MainActivity.onSearchCanceled");
//            mListView.onRefreshComplete(true);
//            mRefreshLayout.showState(AppConstants.LIST);
//        }
//    };
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        ClientManager.getClient().stopSearch();
//    }
//
//
//    private void startBlueTooth() {
//        DialogUtil.getInstance().warning(context, "蓝牙未打开", "现在打开蓝牙", new OnDialogUtilListener() {
//            @Override
//            public void onConfirm() {
//                super.onConfirm();
//                DialogUtil.getInstance().waitDialog(context, "正在打开蓝牙");
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                // 设置蓝牙可见性，最多100秒
//                enableBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 100);
//                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//                DialogUtil.getInstance().hideWait();
//                BluetoothLog.v("蓝牙设备连接成功");
//                Toast.makeText(context,"蓝牙连接成功",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel() {
//                super.onCancel();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        //判断是不是启动蓝牙的结果
//
//        switch (requestCode) {
//            case REQUEST_ENABLE_BT:
//                if (resultCode != RESULT_OK) {
//                    DialogUtil.getInstance().errmessage(context, "打开蓝牙失败", "");
//                } else {
//                    mHandler.sendEmptyMessage(BLUTOOTH_ENABLE);
//                }
//                break;
//        }
//    }
//}
//