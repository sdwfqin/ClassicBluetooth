package com.sdwfqin.cbt.callback;

import com.sdwfqin.cbt.model.DeviceModel;

/**
 * 描述：蓝牙广播回调
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public interface BaseConfigCallback {

    /**
     * 蓝牙开关
     */
    void onStateSwitch(int state);

    /**
     * 设备搜索结束
     */
    void onScanStop();

    /**
     * 发现新设备
     *
     * @param deviceModel
     */
    void onFindDevice(DeviceModel deviceModel);

    void onConn(int conn_type);
}
