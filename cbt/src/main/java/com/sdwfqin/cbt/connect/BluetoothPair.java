package com.sdwfqin.cbt.connect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.sdwfqin.cbt.utils.CbtConstant;

import java.io.IOException;

/**
 * 创建时间: 2018/5/31
 * 作者: xiaoHou
 * E-mail: 605322850@qq.com
 * Blog: www.xiaohoutongxue.cn
 * 描述: ConnectThread  配对蓝牙
 **/
public class BluetoothPair extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothPair(BluetoothAdapter mBluetoothAdapter, BluetoothDevice device) {
        this.mBluetoothAdapter= mBluetoothAdapter;
        mmDevice = device;
        BluetoothSocket tmp = null;
        try {
            //尝试建立安全的连接
            tmp = mmDevice.createRfcommSocketToServiceRecord(CbtConstant.MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmSocket = tmp;
    }

    @Override
    public void run() {
        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
        try {
            mmSocket.connect();
        } catch (IOException e) {
            return;
        }
    }

    public void cancel(){
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
