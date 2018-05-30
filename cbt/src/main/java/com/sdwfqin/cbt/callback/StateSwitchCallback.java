package com.sdwfqin.cbt.callback;

/**
 * 描述：蓝牙开关回调
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public interface StateSwitchCallback {

    /**
     * true 开启
     * false 关闭
     *
     * @param isOn 蓝牙开关状态
     */
    void onStateChange(boolean isOn);
}
