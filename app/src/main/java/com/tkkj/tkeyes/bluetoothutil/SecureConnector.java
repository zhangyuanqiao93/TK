package com.tkkj.tkeyes.bluetoothutil;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.tkkj.tkeyes.bluetooth.BluetoothTools;

/**
 * Created by liwentian on 2017/3/27.
 */

public class SecureConnector {

    private static BluetoothDevice mDevice;
    private static final int serviceUuid = 0xFFE5 ;//0xFE95
    private static final int charaUuid = 0xFFE9;//0x10,0xFFE
    private static final int value = 0x90CA85DE;//0x90CA85DE,0xDE85CA90
    private static Context context;
    private static BluetoothTools tool;

    public static void processStep1(BluetoothDevice device) {
        mDevice = device;

//        final BLEDevice bleDevice = new BLEDevice(context,mDevice) {
//            @Override
//            public void readValue(BluetoothGattCharacteristic characteristic) {
//                super.readValue(characteristic);
//                if (characteristic == null) {
//                    Log.w("tag", "55555555555 readValue characteristic is null");
//                } else {
//
//                    bleService.readValue(this.device, characteristic);
//
//                }
//            }

//            @Override
//            protected void discoverCharacteristicsFromService() {
//                for (BluetoothGattService bluetoothGattService : bleService
//                        .getSupportedGattServices(device)) {
//                    String serviceUUID = Long.toHexString(
//                            bluetoothGattService.getUuid().getMostSignificantBits())
//                            .substring(0, 4);
//                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
//                            .getCharacteristics()) {
//                        String characterUUID = Long.toHexString(
//                                bluetoothGattCharacteristic.getUuid()
//                                        .getMostSignificantBits()).substring(0, 4);
//                        Log.d("_rfstar", "characteristic  : "
//                                + bluetoothGattCharacteristic.getUuid().toString());
//                    }
//                }
//            }
//        };

//        ClientManager.getClient().write(device.getAddress(), UUIDUtils.makeUUID(serviceUuid), UUIDUtils.makeUUID(charaUuid),
//                ByteUtils.fromInt(value), new BleWriteResponse() {
//                    @Override
//                    public void onResponse(int code) {
//
//                        if (code == Code.REQUEST_SUCCESS) {
//                            processStep2();
//                        }
//                    }
//                });
    }

    public static void processStep2() {



         //发送数据
        final String str =  "hello";
        final byte[] bytes = str.getBytes();

//        ClientManager.getClient().write(mDevice.getAddress(), UUIDUtils.makeUUID(serviceUuid),
//                UUIDUtils.makeUUID(charaUuid),bytes, new BleWriteResponse(){

//                    @Override
//                    public void onResponse(int code) {
//
//                        //收到写入response后开始写入
//                        Log.e("tag", "write回调code：" + code);

                        //  写入数据测试
//                        Log.e("tag", "开始写数据: " + "write" );
//                        tool.sendMessage(context,mDevice,str);
//
//                    }
//                });

        //接收数据,读写走的是同一个UUID通道还是不同通道
//        ClientManager.getClient().read(mDevice.getAddress(), UUIDUtils.makeUUID(serviceUuid),
//                UUIDUtils.makeUUID(charaUuid), new BleReadResponse(){
//
//                    @Override
//                    public void onResponse(int code, byte[] data) {
//                        Log.e("tag", "read接收code：" + code);
//                        //data ==null
//                        if ( data != null) {
//                            Log.e("tag", "接收数据成功：" + "data" + new String(data));
//                             tool.receiveMessage(context,mDevice,true);
//                        }
//                    }
//                });
    }
}
