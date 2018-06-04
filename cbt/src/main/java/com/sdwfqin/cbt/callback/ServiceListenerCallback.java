package com.sdwfqin.cbt.callback;

import android.bluetooth.BluetoothDevice;

/**
 * 描述：蓝牙服务回调
 * <p>
 * 注意线程
 *
 * @author zhangqin
 * @date 2018/6/1
 */
public interface ServiceListenerCallback {

    void onStartError(Throwable throwable);

    /**
     * 收到字符串
     *
     * @param s
     */
    void onDataListener(String s, BluetoothDevice device);
}
