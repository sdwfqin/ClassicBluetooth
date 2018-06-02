package com.sdwfqin.cbt.callback;

import android.bluetooth.BluetoothDevice;

import java.util.List;

/**
 * 描述：扫描设备回调
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public interface ScanCallback {

    /**
     * 开始搜索设备
     * <p>
     * true 成功
     * false 失败
     *
     * @param isOn
     */
    void onScanStart(boolean isOn);

    /**
     * 搜索结束
     *
     * @param deviceList
     */
    void onScanStop(List<BluetoothDevice> deviceList);

    /**
     * 发现新设备
     *
     * @param device
     */
    void onFindDevice(BluetoothDevice device);
}
