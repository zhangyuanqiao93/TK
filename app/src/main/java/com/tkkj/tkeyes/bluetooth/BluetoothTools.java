package com.tkkj.tkeyes.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

/**
 * Created by lsx on 2017/4/10.
 */

public class BluetoothTools {

    private TextView lengthTxt = null, countTimesTxt = null, messageTxt;
    private ScrollView messageBG = null;

    /**
     * @param bluetoothDevice : 选中的设备
     * @param msg :  要发送的信息
     */
    public void sendMessage(Context context, BluetoothDevice bluetoothDevice, String msg) {
        if (TextUtils.isEmpty(msg)){
            return;
        }
        CubicBLEDevice cubicBLEDevice = null; // 选中的cubicBLEDevice
        cubicBLEDevice = new CubicBLEDevice(context.getApplicationContext(), bluetoothDevice);
        cubicBLEDevice.setBLEBroadcastDelegate(new BLEDevice.RFStarBLEBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent, String macData, String uuid) {

            }
        });


        cubicBLEDevice.writeValue("ffe5", "ffe9", msg.getBytes());
    }


    /**
     * @param isRecevie : 是否接收数据
     */
    public void receiveMessage(Context context, BluetoothDevice bluetoothDevice, boolean isRecevie){
        CubicBLEDevice cubicBLEDevice = null; // 选中的cubicBLEDevice
        cubicBLEDevice = new CubicBLEDevice(context.getApplicationContext(), bluetoothDevice);
        cubicBLEDevice.setBLEBroadcastDelegate(new BLEDevice.RFStarBLEBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent, String macData, String uuid) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
                    if (uuid.contains("ffe4")) {
                        byte[] data = intent
                                .getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
                        try {
                            Log.e("tag", "接收到的信息：" + new String(data, "GB2312"));
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                        .equals(action)) {

                }
            }
        });


        cubicBLEDevice.setNotification("ffe0", "ffe4", isRecevie);
    }

    private StringBuffer allMessageStr = new StringBuffer(); // 接收的所有数据

    /**
     * 追加的数据
     *
     * @param subString
     */
    public void appendString(String subString) {

        String tempStr = null;
        allMessageStr.append(subString);

        int size = this.allMessageStr.length();
        if (size > 500) {
            tempStr = allMessageStr.substring(size - 500, size);
        } else {
            tempStr = allMessageStr.toString();
        }
        this.messageTxt.setText(tempStr);

        this.lengthTxt.setText(size + "");

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                messageBG.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

}
