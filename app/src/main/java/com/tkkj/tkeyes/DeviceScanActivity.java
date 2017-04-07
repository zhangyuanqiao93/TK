package com.tkkj.tkeyes;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;


public class DeviceScanActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private final static int BLUTOOTH_ENABLE = 2;
    private final static int BLUTOOTH_DISENABLE = 3;
    private static final String TAG = "DeviceActivity";
    private Handler mHandler;
    private static  boolean mScanning;
    private static int SCAN_PERIOD = 1000;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothDevice mBluetoothDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan2);

        //1.检测设备是否支持BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        BluetoothSupport();
    }

    //2.获取蓝牙适配器,两种方式选择其中一个
//    final BluetoothManager bluetoothManager =(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//    BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    //3.是否启用蓝牙
    public void BluetoothSupport(){
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "蓝牙已启用", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "蓝牙未启用", Toast.LENGTH_SHORT).show();
                    mHandler.sendEmptyMessage(BLUTOOTH_ENABLE);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        scanLeDevice(true);
    }

    //4.搜索设备
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD); //10秒后停止搜索
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback); //开始搜索
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);//停止搜索
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //在这里可以把搜索到的设备保存起来
                    device.getName();//获取蓝牙设备名字
                    device.getAddress();//获取蓝牙设备mac地址
                }
            });
        }
    };

    
    //5.选择一个设备连接
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG,"BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        // Previously connected device. Try to reconnect. (先前连接的设备。 尝试重新连接)  
//        if (mBluetoothDeviceAddress != null&& address.equals(mBluetoothDeviceAddress)&& mBluetoothGatt != null) {
//            Log.d(TAG,"Trying to use an existing mBluetoothGatt for connection.");
//            if (mBluetoothGatt.connect()) {
////                mConnectionState = STATE_CONNECTING;
//                return true;
//            } else {
//                return false;
//            }
//        }
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the  
        // autoConnect  
        // parameter to false.  
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback); //该函数才是真正的去进行连接
        Log.d(TAG, "Trying to create a new connection.");
//        mBluetoothDeviceAddress = address;
//        mConnectionState = STATE_CONNECTING;
        return true;
    }


    //连接后会回调BluetoothGattCallback接口，包括读取设备，
    // 往设备里写数据及设备发出通知等都会回调该接口。其中比较重要的是BluetoothGatt。
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override  //当连接上设备或者失去连接时会回调该函数
        public void onConnectionStateChange(BluetoothGatt gatt, int status,int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功
                mBluetoothGatt.discoverServices(); //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {  //连接失败
            }
        }
        @Override  //当设备是否找到服务时，会回调该函数
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {   //找到服务了
                //在这里可以对服务进行解析，寻找到你需要的服务
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }
        @Override  //当读取设备时会回调该函数
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            System.out.println("onCharacteristicRead");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
                int charaProp = characteristic.getProperties();if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0);
                //表示可发出通知。  判断该Characteristic属性
            }
        }

        @Override //当向设备Descriptor中写数据时，会回调该函数
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            System.out.println("onDescriptorWriteonDescriptorWrite = " + status + ", descriptor =" + descriptor.getUuid().toString());
        }

        @Override //设备发出通知时会调用到该接口
        public void onCharacteristicChanged(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic) {
            if (characteristic.getValue() != null) {
                System.out.println(characteristic.getStringValue(0));
            }
            System.out.println("--------onCharacteristicChanged-----");
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            System.out.println("rssi = " + rssi);
        }
        @Override //当向Characteristic写数据时会回调该函数
        public void onCharacteristicWrite(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic, int status) {
            System.out.println("--------write success----- status:" + status);
        }
    };



    //设备连接成功并回调BluetoothGattCallback接口里面的onConnectionStateChange函数，
    // 然后调用mBluetoothGatt.discoverServices();去发现服务。
    // 发现服务后会回调BluetoothGattCallback接口里面的 onServicesDiscovered函数，在里面我们可以获取服务列表
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;
        return mBluetoothGatt.getServices();   //此处返回获取到的服务列表
    }


    //接收通知
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null)
            return;
        for (BluetoothGattService gattService : gattServices) { // 遍历出gattServices里面的所有服务
//            List<BluetoothGattCharacteristic> gattCharacteristics = gattServices.getCharacteristics();
//            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) { // 遍历每条服务里的所有Characteristic
//                if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(需要通信的UUID)) {
                    // 有哪些UUID，每个UUID有什么属性及作用，一般硬件工程师都会给相应的文档。我们程序也可以读取其属性判断其属性。
                    // 此处可以可根据UUID的类型对设备进行读操作，写操作，设置notification等操作
                    // BluetoothGattCharacteristic gattNoticCharacteristic 假设是可设置通知的Characteristic
                    // BluetoothGattCharacteristic gattWriteCharacteristic 假设是可读的Characteristic
                    // BluetoothGattCharacteristic gattReadCharacteristic  假设是可写的Characteristic
//                }
//            }
        }
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
//        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID
//                .fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
//        if (descriptor != null) {
            System.out.println("write descriptor");
//            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//            mBluetoothGatt.writeDescriptor(descriptor);
//        }

    }

}
