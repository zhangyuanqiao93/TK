package com.tkkj.tkeyes.Service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;


import java.util.UUID;


/**
 * Created by TKKJ on 2017/4/7.
 */

/**
 * 蓝牙传输回调服务，数据接收处理。
 * */

public class BluetoothLeservice {



    private static final String  TAG = "BluetoothLeservice";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt bluetoothGatt;


//    private BluetoothGattCallback mBluetoothGatt;
    BluetoothGattCallback mBluetoothGatt = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices(); //执行到这里其实蓝牙已经连接成功了

//                TLog.i(TAG, "Connected to GATT server.");
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                if(mBluetoothDevice != null){
//                    TLog.i(TAG, "重新连接");
//                    connect();
//                }else{
//                    TLog.i(TAG, "Disconnected from GATT server.");
//                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
//                TLog.i(TAG, "onServicesDiscovered");
//                getBatteryLevel();  //获取电量
            } else {
//                TLog.i(TAG, "onServicesDiscovered status------>" + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            //判断UUID是否相等
//            Utils.bytesToHexString(characteristic.getValue());
//            if (Values.UUID_KEY_BATTERY_LEVEL_CHARACTERISTICS.equals(characteristic.getUuid().toString())) {
//
//            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);

//            TLog.d(TAG,"status = " + status);
//            TLog.d(TAG, "onCharacteristicWrite------>" + Utils.bytesToHexString(characteristic.getValue()));
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };
    BluetoothGattServerCallback mGattCallback  = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
        }
        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        }

    };

    //发现服务的回调
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.e(TAG, "成功发现服务");
        }else{
            Log.e(TAG, "服务发现失败，错误码为:" + status);
        }
    }

    //写操作的回调
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.e(TAG, "写入成功" +characteristic.getValue());
            characteristic.getValue();
        }
    }

    //读操作的回调
    public void onCharacteristicRead(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.e(TAG, "读取成功" +characteristic.getValue());
        }
    }

    //数据返回的回调（此处接收BLE设备返回数据）
    public void onCharacteristicChanged(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic) {

    }

    //查找蓝牙设备
    public void connect(){
//        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
    }

//a.获取服务

    public BluetoothGattService getService(UUID uuid) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            TLog.e(TAG, "BluetoothAdapter not initialized");
            return null;
        }
//        return mBluetoothGatt.getService(uuid);
        return null;
    }
    //b.获取特征

    private BluetoothGattCharacteristic getCharcteristic(String serviceUUID, String characteristicUUID) {

//得到服务对象
        BluetoothGattService service = getService(UUID.fromString(serviceUUID));  //调用上面获取服务的方法

        if (service == null) {
//            TLog.e(TAG, "Can not find 'BluetoothGattService'");
            return null;
        }

//得到此服务结点下Characteristic对象
        final BluetoothGattCharacteristic gattCharacteristic = service.getCharacteristic(UUID.fromString(characteristicUUID));
        if (gattCharacteristic != null) {
            return gattCharacteristic;
        } else {
//            TLog.e(TAG, "Can not find 'BluetoothGattCharacteristic'");
            return null;
        }
    }



//获取数据

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            TLog.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
//        mBluetoothGatt.readCharacteristic(characteristic);
    }

    //写入数据
    public void write(byte[] data) {   //一般都是传byte
        //得到可写入的characteristic Utils.isAIRPLANE(mContext) &&
//        if(!mBleManager.isEnabled()){
//            TLog.e(TAG, "writeCharacteristic 开启飞行模式");
//            closeBluetoothGatt();
//            isGattConnected = false;
//            broadcastUpdate(WaveView.Config.ACTION_GATT_DISCONNECTED);
//            return;
//        }
//        BluetoothGattCharacteristic writeCharacteristic = getCharcteristic(Values.UUID_KEY_SERVICE, Values.UUID_KEY_WRITE);  //这个UUID都是根据协议号的UUID
//        if (writeCharacteristic == null) {
//            TLog.e(TAG, "Write failed. GattCharacteristic is null.");
//            return;
//        }
//        writeCharacteristic.setValue(data); //为characteristic赋值
//        writeCharacteristicWrite(writeCharacteristic);

    }

    public void writeCharacteristicWrite(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            TLog.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
//        TLog.e(TAG, "BluetoothAdapter 写入数据");
        boolean isBoolean = false;
//        isBoolean = mBluetoothGatt.writeCharacteristic(characteristic);
//        TLog.e(TAG, "BluetoothAdapter_writeCharacteristic = " +isBoolean);  //如果isBoolean返回的是true则写入成功
    }

}
