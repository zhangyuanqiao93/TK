package com.tkkj.tkeyes.bluetooth;import android.annotation.SuppressLint;import android.app.Service;import android.bluetooth.BluetoothDevice;import android.bluetooth.BluetoothGatt;import android.bluetooth.BluetoothGattCallback;import android.bluetooth.BluetoothGattCharacteristic;import android.bluetooth.BluetoothGattDescriptor;import android.bluetooth.BluetoothGattService;import android.bluetooth.BluetoothProfile;import android.content.Intent;import android.os.Binder;import android.os.IBinder;import android.util.Log;import java.util.ArrayList;import java.util.List;import java.util.UUID;/* * 管理蓝牙的服务  * 			功能：  *			    1) 连接蓝牙设备 *				2) 管理连接状态 *				3) 获取蓝牙设备的相关服务 * * @author Kevin.wu *  */@SuppressLint("NewApi")public final class RFStarBLEService extends Service {	public final static String ACTION_GATT_CONNECTED = "com.rfstar.kevin.service.ACTION_GATT_CONNECTED";	public final static String ACTION_GATT_CONNECTING = "com.rfstar.kevin.service.ACTION_GATT_CONNECTING";	public final static String ACTION_GATT_DISCONNECTED = "com.rfstar.kevin.service.ACTION_GATT_DISCONNECTED";	public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.rfstar.kevin.service.ACTION_GATT_SERVICES_DISCOVERED";	public final static String ACTION_DATA_AVAILABLE = "com.rfstar.kevin.service.ACTION_DATA_AVAILABLE";	public final static String EXTRA_DATA = "com.rfstar.kevin.service.EXTRA_DATA";	public final static String ACTION_GAT_RSSI = "com.rfstar.kevin.service.RSSI";	public final static String RFSTAR_CHARACTERISTIC_ID = "com.rfstar.kevin.service.characteristic"; // 唯一标识	private final IBinder kBinder = new LocalBinder();	private static ArrayList<BluetoothGatt> arrayGatts = new ArrayList<BluetoothGatt>(); // 存放BluetoothGatt的集合    private String TAG = "tag";	@Override	public IBinder onBind(Intent intent) {		// TODO Auto-generated method stub		return kBinder;	}	@Override	public boolean onUnbind(Intent intent) {		// TODO Auto-generated method stub		return super.onUnbind(intent);	}	/**	 * 初始化BLE 如果已经连接就不用再次连接	 * //	 * @param bleDevice	 * @return	 */	public boolean initBluetoothDevice(BluetoothDevice device) {		BluetoothGatt gatt = this.getBluetoothGatt(device);		if (gatt != null) {			if (gatt.connect()) {				// 已经连接上				Log.d(TAG, getString(R.string.devices_connection_addres)						+ gatt.getDevice().getAddress() + "  连接上  数量: "						+ arrayGatts.size());			} else {				return false;			}			return true;		}		Log.d(TAG, "5555" + device.getName() + ": 蓝牙设备正准备连接");		gatt = device.connectGatt(this, false, bleGattCallback);		arrayGatts.add(gatt);		return true;	}	/**	 * 断开连接	 */	public void disconnect(BluetoothDevice device) {		BluetoothGatt gatt = this.getBluetoothGatt(device);		if (gatt == null) {			Log.w(TAG, "kBluetoothGatt 不能断开连接");			return;		}		gatt.disconnect();		arrayGatts.remove(gatt);	}	/**	 * 连接防丢器	 * 	 * @return	 */	public boolean connect(BluetoothDevice device) {		return initBluetoothDevice(device); // 写到这，无法打 cancelOpen	}	public class LocalBinder extends Binder {		public RFStarBLEService getService() {			return RFStarBLEService.this;		}	}	private final BluetoothGattCallback bleGattCallback = new BluetoothGattCallback() {		/*		 * 连接的状发生变化 (non-Javadoc)		 * 		 * @see		 * android.bluetooth.BluetoothGattCallback#onConnectionStateChange(android		 * .bluetooth.BluetoothGatt, int, int)		 */		@Override		public void onConnectionStateChange(BluetoothGatt gatt, int status,                                            int newState) {			String action = null;			if (newState == BluetoothProfile.STATE_CONNECTED) {				action = ACTION_GATT_CONNECTED;				gatt.discoverServices();			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {				action = ACTION_GATT_DISCONNECTED;			}			if (action != null && !action.equals("")) {				broadcastUpdate(action);			}		}		/*		 * 搜索device中的services (non-Javadoc)		 * 		 * @see		 * android.bluetooth.BluetoothGattCallback#onServicesDiscovered(android		 * .bluetooth.BluetoothGatt, int)		 */		@Override		public void onServicesDiscovered(BluetoothGatt gatt, int status) {			Log.w(TAG, "eeeeeeee  onServicesDiscovered received: " + status);			if (status == BluetoothGatt.GATT_SUCCESS) {				broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);			} else {				Log.w(TAG, "onServicesDiscovered received: " + status);			}		}		/*		 * 读取特征值 (non-Javadoc)		 * 		 * @see		 * android.bluetooth.BluetoothGattCallback#onCharacteristicRead(android		 * .bluetooth.BluetoothGatt,		 * android.bluetooth.BluetoothGattCharacteristic, int)		 */		public void onCharacteristicRead(BluetoothGatt gatt,				BluetoothGattCharacteristic characteristic,				int status) {			if (status == BluetoothGatt.GATT_SUCCESS) {				broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);			} else {				Log.d(TAG, "onCharacteristicRead received: " + status);			}		}		public void onCharacteristicWrite(BluetoothGatt gatt,                                          BluetoothGattCharacteristic characteristic, int status) {			Log.d(TAG,					"back write " + Tools.byte2Hex(characteristic.getValue()));			if (BluetoothGatt.GATT_SUCCESS == status) {				Log.d(TAG, "back write  success");			} else if (BluetoothGatt.GATT_FAILURE == status) {				Log.d(TAG, "back write  failure");			}		};		/*		 * 特征值的变化 (non-Javadoc)		 *		 * @see		 * android.bluetooth.BluetoothGattCallback#onCharacteristicChanged(android		 * .bluetooth.BluetoothGatt,		 * android.bluetooth.BluetoothGattCharacteristic)		 */		public void onCharacteristicChanged(BluetoothGatt gatt,				BluetoothGattCharacteristic characteristic) {			broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);			Log.d(TAG,					"notification " + Tools.byte2Hex(characteristic.getValue()));		}		/*		 * 读取信号 (non-Javadoc)		 * 		 * @see		 * android.bluetooth.BluetoothGattCallback#onReadRemoteRssi(android.		 * bluetooth.BluetoothGatt, int, int)		 */		public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {			if (gatt.connect()) {				broadcastUpdate(ACTION_GAT_RSSI);				Log.d(TAG, "11111111111111111 onReadRemoteRssi  : " + rssi);			}		}	};	/**	 * 发送数据到广播	 * 	 * @param action	 */	private void broadcastUpdate(String action) {		Intent intent = new Intent(action);		sendBroadcast(intent);	}	/**	 * 发送带蓝牙信息的到广播	 * 	 * @param action	 * @param characteristic	 */	private void broadcastUpdate(String action,			BluetoothGattCharacteristic characteristic) {		Intent intent = new Intent(action);		// For all other profiles, writes the data formatted in HEX.		final byte[] data = characteristic.getValue();		Log.e(TAG, "broadcastUpdate: "+ data );		if (data != null && data.length > 0) {			intent.putExtra(EXTRA_DATA, characteristic.getValue());			intent.putExtra(RFSTAR_CHARACTERISTIC_ID, characteristic.getUuid()					.toString());		}		sendBroadcast(intent);	}	public void readValue(BluetoothDevice device,			BluetoothGattCharacteristic characteristic) {		// TODO Auto-generated method stub		BluetoothGatt gatt = this.getBluetoothGatt(device);		if (gatt == null) {			Log.w("", "kBluetoothGatt 为没有初始化，所以不能读取数据");			return;		}		gatt.readCharacteristic(characteristic);	}	public boolean writeValue(BluetoothDevice device,			BluetoothGattCharacteristic characteristic) {		// TODO Auto-generated method stub		BluetoothGatt gatt = this.getBluetoothGatt(device);		if (gatt == null) {			Log.w(TAG, "BluetoothGatt 为没有初始化，所以不能写入数据");			return false;		}		gatt.writeCharacteristic(characteristic);		Log.d(TAG, "OK! connect :  连接上  数量： " + arrayGatts.size());//		return false or true?		return true;	}	public void setCharacteristicNotification(BluetoothDevice device,                                              BluetoothGattCharacteristic characteristic, boolean enable) {		// TODO Auto-generated method stub		BluetoothGatt gatt = this.getBluetoothGatt(device);		if (gatt == null) {			Log.w(TAG, "kBluetoothGatt 为没有初始化，所以不能发送使能数据");			return;		}		BluetoothGattDescriptor localBluetoothGattDescriptor;		UUID localUUID = UUID				.fromString("00002902-0000-1000-8000-00805F9B34FB");//UUID是什么接口		localBluetoothGattDescriptor = characteristic.getDescriptor(localUUID);		if (enable) {			byte[] arrayOfByte = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;			localBluetoothGattDescriptor.setValue(arrayOfByte);		} else {			byte[] arrayOfByte = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;			localBluetoothGattDescriptor.setValue(arrayOfByte);		}		gatt.setCharacteristicNotification(characteristic, enable);		gatt.writeDescriptor(localBluetoothGattDescriptor);	}	/**	 * 获取services	 * 	 * @return	 */	public List<BluetoothGattService> getSupportedGattServices(			BluetoothDevice device) {		BluetoothGatt gatt = this.getBluetoothGatt(device);		if (gatt == null) {			Log.w(TAG, "111111111111  services is null ");			return null;		}		return gatt.getServices();	}	// 从arrayGatts匹配出与device中address相同的BluetoothGatt	private BluetoothGatt getBluetoothGatt(BluetoothDevice device) {		BluetoothGatt gatt = null;		for (BluetoothGatt tmpGatt : arrayGatts) {			if (tmpGatt.getDevice().getAddress().equals(device.getAddress())) {				gatt = tmpGatt;			}		}		return gatt;	}}