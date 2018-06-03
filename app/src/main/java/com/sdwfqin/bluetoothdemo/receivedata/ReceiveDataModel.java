package com.sdwfqin.bluetoothdemo.receivedata;

import android.bluetooth.BluetoothDevice;

/**
 * 描述：
 *
 * @author zhangqin
 * @date 2018/6/3
 */
public class ReceiveDataModel {

    private BluetoothDevice device;
    private String msg;

    public ReceiveDataModel() {
    }

    public ReceiveDataModel(BluetoothDevice device, String msg) {
        this.device = device;
        this.msg = msg;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
