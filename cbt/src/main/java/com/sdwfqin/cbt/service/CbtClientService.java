package com.sdwfqin.cbt.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.sdwfqin.cbt.callback.ConnectDeviceCallback;
import com.sdwfqin.cbt.callback.SendDataCallback;
import com.sdwfqin.cbt.utils.CbtConstant;
import com.sdwfqin.cbt.utils.CbtExecutor;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 描述：蓝牙设备客户端服务
 *
 * @author zhangqin
 * @date 2018/6/1
 */
public class CbtClientService {

    private BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectDeviceCallback mCallBack;
    public boolean isConnection = false;

    public static CbtClientService getInstance() {
        return BluetoothDataServiceHolder.CBT_CLIENT_SERVICE;
    }

    private static class BluetoothDataServiceHolder {
        private static final CbtClientService CBT_CLIENT_SERVICE = new CbtClientService();
    }

    /**
     * 初始化
     *
     * @return
     */
    public void init(BluetoothAdapter bluetoothAdapter, BluetoothDevice device, ConnectDeviceCallback callBack) {
        mCallBack = callBack;
        if (mBluetoothDevice != null) {
            if (mBluetoothDevice.getAddress().equals(device.getAddress())) {
                mCallBack.connectSuccess(mBluetoothSocket, mBluetoothDevice);
                return;
            } else {
                cancel();
            }
        }

        mBluetoothAdapter = bluetoothAdapter;
        mBluetoothDevice = device;
        BluetoothSocket tmp = null;
        try {
            //尝试建立安全的连接
            tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(CbtConstant.CBT_UUID);
        } catch (IOException e) {
            mCallBack.connectError(e);
        }
        mBluetoothSocket = tmp;
        connect();
    }

    private void connect() {
        CbtExecutor.getInstance().execute(() -> {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            try {
                mBluetoothSocket.connect();
                isConnection = true;
            } catch (IOException e) {
                mCallBack.connectError(e);
            }
        });
    }

    public BluetoothSocket getBluetoothSocket() {
        return mBluetoothSocket;
    }

    /**
     * 发送数据
     */
    public void sendData(List<byte[]> data, SendDataCallback callback) {
        CbtExecutor.getInstance().execute(() -> {
            OutputStream outputStream;
            try {
                outputStream = mBluetoothSocket.getOutputStream();
                for (int i = 0; i < data.size(); i++) {
                    byte[] bytes = data.get(i);
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                }
                callback.sendSuccess();
            } catch (IOException e) {
                callback.sendError(e);
                CbtLogs.e(e.getMessage());
            }
        });
    }

    public void cancel() {
        try {
            mBluetoothSocket.close();
            mBluetoothAdapter = null;
            mBluetoothDevice = null;
            isConnection = false;
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
    }
}
