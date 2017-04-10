package com.tkkj.tkeyes.bluetoothutil;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.util.Log;
import android.widget.Toast;

import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleReadResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.inuker.bluetooth.library.utils.UUIDUtils;

import java.util.UUID;

/**
 * Created by liwentian on 2017/3/27.
 */

public class SecureConnector {

    private static BluetoothDevice mDevice;
    private static final int serviceUuid = 0xFFE5 ;//0xFE95
    private static final int charaUuid = 0xFFE9;//0x10,0xFFE
    private static final int value = 0x90CA85DE;//0x90CA85DE,0xDE85CA90

    public static void processStep1(BluetoothDevice device) {
        mDevice = device;

        ClientManager.getClient().write(device.getAddress(), UUIDUtils.makeUUID(serviceUuid), UUIDUtils.makeUUID(charaUuid),
                ByteUtils.fromInt(value), new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {

                        if (code == Code.REQUEST_SUCCESS) {
                            processStep2();
                        }
                    }
                });
    }

    public static void processStep2() {

         //发送数据
        String str =  "helloXX";

        ClientManager.getClient().write(mDevice.getAddress(), UUIDUtils.makeUUID(serviceUuid),
                UUIDUtils.makeUUID(charaUuid),str.getBytes(), new BleWriteResponse(){

                    @Override
                    public void onResponse(int code) {
                        Log.e("tag", "write回调code：" + code);
                    }
                });

        //接收数据
        ClientManager.getClient().read(mDevice.getAddress(), UUIDUtils.makeUUID(serviceUuid),
                UUIDUtils.makeUUID(charaUuid), new BleReadResponse(){




                    @Override
                    public void onResponse(int code, byte[] data) {
                        Log.e("tag", "read接收code：" + code);
                        if (data != null) {
                            Log.e("tag", "接收数据成功：" + "data" + new String(data));
                        }
                    }
                });

        ClientManager.getClient().notify(mDevice.getAddress(), UUIDUtils.makeUUID(serviceUuid),
                UUIDUtils.makeUUID(charaUuid), new BleNotifyResponse() {
                    @Override
                    public void onNotify(UUID service, UUID character, byte[] value) {
                        Log.e("tag","onNotify:"+ "\n"+"service:"+service+ "\n"+"character"+character+"\n"+"value"+value);
                    }

                    @Override
                    public void onResponse(int code) {
                        Log.e("tag","onResponse:"+code);
                    }
                });
    }
}
