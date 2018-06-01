package com.sdwfqin.cbt.connect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.sdwfqin.cbt.utils.CbtConstant;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.io.IOException;

/**
 * 创建时间: 2018/5/31
 * 作者: xiaoHou
 * E-mail: 605322850@qq.com
 * Blog: www.xiaohoutongxue.cn
 * 描述: ConnectThread  配对蓝牙
 **/
public class BluetoothDataService extends Thread {

    private final BluetoothSocket mBluetoothSocket;
    private final BluetoothDevice mBluetoothDevice;
    private BluetoothAdapter mBluetoothAdapter;

    public BluetoothDataService(BluetoothAdapter mBluetoothAdapter, BluetoothDevice device) {
        this.mBluetoothAdapter = mBluetoothAdapter;
        mBluetoothDevice = device;
        BluetoothSocket tmp = null;
        try {
            //尝试建立安全的连接
            tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(CbtConstant.CBT_UUID);
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
        mBluetoothSocket = tmp;
    }

    @Override
    public void run() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        try {
            mBluetoothSocket.connect();
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
            return;
        }
    }

    public BluetoothSocket getBluetoothSocket() {
        return mBluetoothSocket;
    }

    public void cancel() {
        try {
            mBluetoothSocket.close();
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
    }
}
