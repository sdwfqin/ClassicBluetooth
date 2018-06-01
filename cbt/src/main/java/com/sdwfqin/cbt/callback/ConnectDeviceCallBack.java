package com.sdwfqin.cbt.callback;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * 描述：连接设备
 *
 * @author zhangqin
 * @date 2018/6/1
 */
public interface ConnectDeviceCallBack {

    /**
     * 连接状态
     *
     * @param device
     */
    void connectSuccess(BluetoothSocket socket, BluetoothDevice device);

    /**
     * 连接失败
     */
    void connectError(Throwable throwable);
}
