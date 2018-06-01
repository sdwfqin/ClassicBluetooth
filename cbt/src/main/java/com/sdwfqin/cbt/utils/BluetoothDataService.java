package com.sdwfqin.cbt.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 创建时间: 2018/5/31
 * 作者: xiaoHou
 * E-mail: 605322850@qq.com
 * Blog: www.xiaohoutongxue.cn
 * 描述: ConnectThread  配对蓝牙
 **/
public class BluetoothDataService {

    private BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothAdapter mBluetoothAdapter;
    public boolean isConnection = false;

    public static BluetoothDataService getInstance() {
        return BluetoothDataServiceHolder.sCbtManager;
    }

    private static class BluetoothDataServiceHolder {
        private static final BluetoothDataService sCbtManager = new BluetoothDataService();
    }

    /**
     * 初始化
     *
     * @return
     */
    public void init(BluetoothAdapter bluetoothAdapter, BluetoothDevice device) {
        mBluetoothAdapter = bluetoothAdapter;
        mBluetoothDevice = device;
        BluetoothSocket tmp = null;
        try {
            //尝试建立安全的连接
            tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(CbtConstant.CBT_UUID);
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
        mBluetoothSocket = tmp;
        connect();
    }

    private void connect() {
        new Thread(() -> {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            try {
                mBluetoothSocket.connect();
                isConnection = true;
            } catch (IOException e) {
                CbtLogs.e(e.getMessage());
            }
        }).start();
    }

    public BluetoothSocket getBluetoothSocket() {
        return mBluetoothSocket;
    }

    /**
     * 发送数据
     */
    public void sendData(byte[] data) {
        OutputStream outputStream = null;
        try {
            outputStream = mBluetoothSocket.getOutputStream();
            outputStream.write(data, 0, data.length);
            outputStream.flush();
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
    }

    public void cancel() {
        try {
            mBluetoothSocket.close();
            isConnection = false;
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
    }
}
