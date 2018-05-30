package com.sdwfqin.cbt.callback;

import com.sdwfqin.cbt.model.DeviceModel;

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
     */
    void onScanStart(boolean isOn);

    /**
     * 搜索结束
     */
    void onScanStop(List<DeviceModel> deviceList);

    /**
     * 发现新设备
     *
     * @param deviceModel
     */
    void onFindDevice(DeviceModel deviceModel);
}
