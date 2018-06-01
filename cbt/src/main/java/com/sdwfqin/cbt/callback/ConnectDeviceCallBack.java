package com.sdwfqin.cbt.callback;

import android.bluetooth.BluetoothDevice;

/**
 * 创建时间: 2018/5/31
 * 作者: xiaoHou
 * E-mail: 605322850@qq.com
 * Blog: www.xiaohoutongxue.cn
 * 描述: GetConnectDevice 获取连接设备
 **/
public interface ConnectDeviceCallBack {

    /**
     * 连接状态
     *
     * @param device
     */
    void connectSuccess(BluetoothDevice device);

    /**
     * 连接失败
     *
     * @param device
     */
    void connectError(BluetoothDevice device);
}
