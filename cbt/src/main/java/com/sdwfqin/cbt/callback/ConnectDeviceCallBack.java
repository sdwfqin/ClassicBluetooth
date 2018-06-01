package com.sdwfqin.cbt.callback;

import com.sdwfqin.cbt.model.DeviceModel;

/**
 * 创建时间: 2018/5/31
 * 作者: xiaoHou
 * E-mail: 605322850@qq.com
 * Blog: www.xiaohoutongxue.cn
 * 描述: GetConnectDevice 获取连接设备
 **/
public interface ConnectDeviceCallBack {
    /**
     * 获取设备信息
     * @return
     */
    DeviceModel getConnectDevice();

    /**
     * 连接状态
     * @param conn
     */
    void connectDevice(int conn);
}
