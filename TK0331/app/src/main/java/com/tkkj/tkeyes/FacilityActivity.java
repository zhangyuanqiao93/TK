package com.tkkj.tkeyes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.tkkj.tkeyes.adapter.DeviceListAdapter;
import com.tkkj.tkeyes.base.BasicActivity;
import com.tkkj.tkeyes.bluetoothutil.ClientManager;
import com.tkkj.tkeyes.utils.AppConstants;
import com.tkkj.tkeyes.utils.DialogUtil;
import com.tkkj.tkeyes.view.PullRefreshListView;
import com.tkkj.tkeyes.view.PullToRefreshFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);
        ButterKnife.bind(this);

        requestPermission();
        initData();
    }


    //    权限申请
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

        searchDevice();
    }

    @OnClick(R.id.imgBtn_back)
    public void onClick() {
        finish();
    }


    private void searchDevice() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(5000, 2)
                .searchBluetoothClassicDevice(3000, 2).
                        build();

        ClientManager.getClient().search(request, mSearchResponse);
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            BluetoothLog.w("MainActivity.onSearchStarted");
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
}