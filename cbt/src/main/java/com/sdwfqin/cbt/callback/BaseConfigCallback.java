package com.sdwfqin.cbt.callback;

import android.bluetooth.BluetoothDevice;

/**
 * 描述：蓝牙广播回调
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public interface BaseConfigCallback {

    /**
     * 蓝牙开关
     *
     * @param state
     */
    void onStateSwitch(int state);

    /**
     * 设备搜索结束
     */
    void onScanStop();

    /**
     * 发现新设备
     *
     * @param device
     */
    void onFindDevice(BluetoothDevice device);

    /**
     * 连接设备
     *
     * @param device
     */
    void onConnect(BluetoothDevice device);
}
