package com.tkkj.tkeyes.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.Serializable;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CubicBLEDevice extends BLEDevice implements Serializable {

	public CubicBLEDevice(Context context, BluetoothDevice bluetoothDevice) {
		// TODO Auto-generated constructor stub
		super(context, bluetoothDevice);
	}

	@Override
	protected void discoverCharacteristicsFromService() {
		// TODO Auto-generated method stub
		//Log.d(App.TAG, "load all the services ");

		for (BluetoothGattService bluetoothGattService : bleService
				.getSupportedGattServices(device)) {
			String serviceUUID = Long.toHexString(
					bluetoothGattService.getUuid().getMostSignificantBits())
					.substring(0, 4);
			for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
					.getCharacteristics()) {
				String characterUUID = Long.toHexString(
						bluetoothGattCharacteristic.getUuid()
								.getMostSignificantBits()).substring(0, 4);
				Log.d("_rfstar", "characteristic  : "
						+ bluetoothGattCharacteristic.getUuid().toString());
			}
		}
		// BluetoothGattService gattService = new BluetoothGattService(uuid,
		// serviceType);
		// UUID uuid = UUID.fromString("0000ffe9-0000-1000-8000-00805f9b34fb");
		// BluetoothGattCharacteristic characteristic = new
		// BluetoothGattCharacteristic(
		// uuid, properties, permissions);
	}

	/**
	 *
	 * @param
	 * @param serviceUUID
	 * @param characteristicUUID
	 */
	public void writeValue(String serviceUUID, String characteristicUUID,
						   byte[] value) {

		// TODO Auto-generated method stub
		for (BluetoothGattService bluetoothGattService : bleService
				.getSupportedGattServices(device)) {
			String gattServiceUUID = Long.toHexString(
					bluetoothGattService.getUuid().getMostSignificantBits())
					.substring(0, 4);
			for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
					.getCharacteristics()) {
				String characterUUID = Long.toHexString(
						bluetoothGattCharacteristic.getUuid()
								.getMostSignificantBits()).substring(0, 4);
				if (gattServiceUUID.equals(serviceUUID)
						&& characteristicUUID.equals(characterUUID)) {
//					 bluetoothGattCharacteristic.setValue(value);
//					 this.writeValue(bluetoothGattCharacteristic);
					int length = value.length;
					int lengthChar = 0;
					int position = 0;
					while (length > 0) {
						if (length > 150) {
							lengthChar = 150;
						} else if (length > 0) {
							lengthChar = length;
						} else {
							return;
						}
						byte sendValue[] = new byte[lengthChar];
						for (int count = 0; count < lengthChar; count++) {
							sendValue[count] = value[position + count];
						}
						Log.d("tag", "send:  " + Tools.byte2Hex(sendValue));
//						 = value.substring(position, lengthChar+ position);
						bluetoothGattCharacteristic.setValue(sendValue);
						bluetoothGattCharacteristic
								.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
						this.writeValue(bluetoothGattCharacteristic);

						length -= lengthChar;
						position += lengthChar;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param serviceUUID
	 * @param characteristicUUID
	 * @param
	 */
	public void writeValue(String serviceUUID, String characteristicUUID,
						   String value) {
		// TODO Auto-generated method stub
		for (BluetoothGattService bluetoothGattService : bleService
				.getSupportedGattServices(device)) {
			String gattServiceUUID = Long.toHexString(
					bluetoothGattService.getUuid().getMostSignificantBits())
					.substring(0, 4);
			for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
					.getCharacteristics()) {
				String characterUUID = Long.toHexString(
						bluetoothGattCharacteristic.getUuid()
								.getMostSignificantBits()).substring(0, 4);
				if (gattServiceUUID.equals(serviceUUID)
						&& characteristicUUID.equals(characterUUID)) {

					int length = value.length();
					int lengthChar = 0;
					int position = 0;
					while (length > 0) {
						if (length > 20) {
							lengthChar = 20;
						} else if (length > 0) {
							lengthChar = length;
						} else {
							return;
						}
						String sendValue = value.substring(position, lengthChar
								+ position);
						bluetoothGattCharacteristic.setValue(sendValue);
						bluetoothGattCharacteristic
								.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
						this.writeValue(bluetoothGattCharacteristic);

						length -= lengthChar;
						position += lengthChar;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param serviceUUID
	 * @param characteristicUUID
	 */
	public void readValue(String serviceUUID, String characteristicUUID) {
		for (BluetoothGattService bluetoothGattService : bleService
				.getSupportedGattServices(device)) {
			String gattServiceUUID = Long.toHexString(
					bluetoothGattService.getUuid().getMostSignificantBits())
					.substring(0, 4);
			for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
					.getCharacteristics()) {
				String characterUUID = Long.toHexString(
						bluetoothGattCharacteristic.getUuid()
								.getMostSignificantBits()).substring(0, 4);
				if (gattServiceUUID.equals(serviceUUID)
						&& characteristicUUID.equals(characterUUID)) {
					//Log.d(App.TAG, "charaterUUID read is success  : "
							//+ characterUUID);
					this.readValue(bluetoothGattCharacteristic);
				}
			}
		}
	}

	/**
	 * 
	 * @param serviceUUID
	 * @param characteristicUUID
	 */
	public void setNotification(String serviceUUID, String characteristicUUID,
								boolean enable) {
		for (BluetoothGattService bluetoothGattService : bleService
				.getSupportedGattServices(device)) {
			String gattServiceUUID = Long.toHexString(
					bluetoothGattService.getUuid().getMostSignificantBits())
					.substring(0, 4);
			for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
					.getCharacteristics()) {
				String characterUUID = Long.toHexString(
						bluetoothGattCharacteristic.getUuid()
								.getMostSignificantBits()).substring(0, 4);
				if (gattServiceUUID.equals(serviceUUID)
						&& characteristicUUID.equals(characterUUID)) {
					this.setCharacteristicNotification(
							bluetoothGattCharacteristic, enable);
				}
			}
		}
	}
}
