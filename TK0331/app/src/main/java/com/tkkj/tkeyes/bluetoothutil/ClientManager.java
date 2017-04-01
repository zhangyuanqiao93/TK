package com.tkkj.tkeyes.bluetoothutil;

import com.inuker.bluetooth.library.BluetoothClient;
import com.tkkj.tkeyes.base.BasicApplication;

/**
 * Created by dingjikerbo on 2016/8/27.
 */
public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(BasicApplication.getInstance());
                }
            }
        }
        return mClient;
    }
}
