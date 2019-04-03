package com.sdwfqin.cbt;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.sdwfqin.cbt.callback.BaseConfigCallback;
import com.sdwfqin.cbt.callback.ConnectDeviceCallback;
import com.sdwfqin.cbt.callback.ScanCallback;
import com.sdwfqin.cbt.callback.SendDataCallback;
import com.sdwfqin.cbt.callback.ServiceListenerCallback;
import com.sdwfqin.cbt.callback.StateSwitchCallback;
import com.sdwfqin.cbt.receiver.BluetoothReceiver;
import com.sdwfqin.cbt.service.CbtClientService;
import com.sdwfqin.cbt.service.CbtServiceListener;
import com.sdwfqin.cbt.utils.CbtConstant;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 描述：Android经典蓝牙工具类
 * <p>
 * TODO：已配对设备列表
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class CbtManager implements BaseConfigCallback {

    private Application mContext;
    /**
     * 蓝牙适配器
     */
    private BluetoothAdapter mBluetoothAdapter;
    /**
     * 蓝牙广播
     */
    private BluetoothReceiver mBluetoothReceiver;

    /**
     * 蓝牙开关回调
     */
    private StateSwitchCallback mStateSwitchCallback;
    /**
     * 扫描设备回调
     */
    private ScanCallback mScanCallback;
    /**
     * 连接设备回掉
     */
    private ConnectDeviceCallback mConnCallBack;
    /**
     * 蓝牙服务回调
     */
    private ServiceListenerCallback mListenerCallback;

    private List<BluetoothDevice> mDeviceList = new ArrayList<>();

    public static CbtManager getInstance() {
        return CbtManagerHolder.CBT_MANAGER;
    }

    @Override
    public void onStateSwitch(int state) {
        if (mStateSwitchCallback == null) {
            return;
        }
        if (state == BluetoothAdapter.STATE_ON) {
            mStateSwitchCallback.onStateChange(true);
        } else if (state == BluetoothAdapter.STATE_OFF) {
            mStateSwitchCallback.onStateChange(false);
        }
    }

    @Override
    public void onScanStop() {
        if (mScanCallback == null) {
            return;
        }
        mScanCallback.onScanStop(mDeviceList);
    }

    @Override
    public void onFindDevice(BluetoothDevice device) {
        if (mScanCallback == null) {
            return;
        }
        // 排除相同设备
        for (int i = 0; i < mDeviceList.size(); i++) {
            if (device.getAddress().equals(mDeviceList.get(i).getAddress())) {
                return;
            }
        }
        mScanCallback.onFindDevice(device);
        mDeviceList.add(device);
    }

    @Override
    public void onConnect(BluetoothDevice device) {
        if (mConnCallBack == null) {
            return;
        }
        CbtClientService.getInstance().isConnection = true;
        mConnCallBack.connectSuccess(CbtClientService.getInstance().getBluetoothSocket(), device);
    }

    private static class CbtManagerHolder {
        private static final CbtManager CBT_MANAGER = new CbtManager();
    }

    /**
     * 初始化
     *
     * @param app
     * @return
     */
    public CbtManager init(Application app) {
        if (mContext == null && app != null) {
            mContext = app;
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothReceiver = new BluetoothReceiver(mContext, this);
        }
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 是否打印日志
     * <p>
     * 默认打印
     *
     * @param isEnable
     * @return BleManager
     */
    public CbtManager enableLog(boolean isEnable) {
        CbtLogs.Config config = CbtLogs.getConfig();
        config.setLogSwitch(isEnable)
                .setConsoleSwitch(isEnable);
        return this;
    }

    /**
     * 设置自定义UUID
     *
     * @param uuid
     * @return BleManager
     */
    public CbtManager setUUID(String uuid) {
        CbtConstant.CBT_UUID = UUID.fromString(uuid);
        return this;
    }

    /**
     * 设置自定义服务名称
     *
     * @param name
     * @return BleManager
     */
    public CbtManager setServiceName(String name) {
        CbtConstant.CBT_NAME = name;
        return this;
    }

    /**
     * 开启蓝牙
     */
    public void enableBluetooth(StateSwitchCallback callback) {

        mStateSwitchCallback = callback;

        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            } else {
                mStateSwitchCallback.onStateChange(true);
            }
        }
    }

    /**
     * 关闭蓝牙
     */
    public void disableBluetooth(StateSwitchCallback callback) {
        mStateSwitchCallback = callback;
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }
        }
    }

    /**
     * 判断是否开启蓝牙
     */
    public boolean isEnableBluetooth() {

        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 开始搜索
     * <p>
     * TODO: 自行判断是否开启蓝牙{@link CbtManager#isEnableBluetooth}
     */
    public void scan(ScanCallback scanCallback) {
        mScanCallback = scanCallback;
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                return;
            }
            mDeviceList.clear();
            mScanCallback.onScanStart(mBluetoothAdapter.startDiscovery());
        }
        // 是否正在搜索
        // mBluetoothAdapter.isDiscovering()

    }

    /**
     * 已配对设备列表
     */
    public List<BluetoothDevice> getBondedDevices() {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        return new ArrayList(bondedDevices);
    }

    /**
     * 设备连接
     *
     * @param callBack
     */
    public void connectDevice(BluetoothDevice device, ConnectDeviceCallback callBack) {
        mConnCallBack = callBack;
        if (mBluetoothAdapter != null) {
            //配对蓝牙
            CbtClientService.getInstance().init(mBluetoothAdapter, device, callBack);
        }
    }

    /**
     * 发送数据
     *
     * @param data
     */
    public void sendData(byte[] data, SendDataCallback callback) {
        if (CbtClientService.getInstance().isConnection) {
            List<byte[]> bytes = new ArrayList<>();
            bytes.add(data);
            CbtClientService.getInstance().sendData(bytes, callback);
        } else {
            callback.sendError(new Exception("设备未连接"));
        }
    }

    /**
     * 发送数据
     *
     * @param data
     */
    public void sendData(List<byte[]> data, SendDataCallback callback) {
        if (CbtClientService.getInstance().isConnection) {
            CbtClientService.getInstance().sendData(data, callback);
        } else {
            callback.sendError(new Exception("设备未连接"));
        }
    }

    /**
     * 发送编码后的字符串数据
     *
     * @param data
     */
    public void sendData(String data, String charsetName, SendDataCallback callback) {
        if (CbtClientService.getInstance().isConnection) {
            try {
                byte[] body = data.getBytes(charsetName);
                List<byte[]> bytes = new ArrayList<>();
                bytes.add(body);
                CbtClientService.getInstance().sendData(bytes, callback);
            } catch (Exception e) {
                callback.sendError(e);
                CbtLogs.e(e);
            }
        } else {
            callback.sendError(new Exception("设备未连接"));
        }
    }

    /**
     * 开启蓝牙服务端
     * <p>
     * TODO: 自行判断是否开启蓝牙{@link CbtManager#isEnableBluetooth}
     */
    public void startServiceListener(ServiceListenerCallback callback) {
        mListenerCallback = callback;
        if (mBluetoothAdapter == null) {
            return;
        }
        CbtServiceListener.getInstance().init(mBluetoothAdapter, callback);
    }

    /**
     * 关闭蓝牙服务端
     */
    public void closeService() {
        if (mBluetoothAdapter == null) {
            return;
        }
        CbtServiceListener.getInstance().cancel();
    }

    /**
     * 关闭连接服务
     */
    public void disableCancelService() {
        try {
            CbtClientService.getInstance().cancel();
        } catch (Exception e) {
            CbtLogs.e(e.getMessage());
        }
    }

    /**
     * 关闭服务
     */
    public void onDestroy() {
        try {
            CbtClientService.getInstance().cancel();
        } catch (Exception e) {
            CbtLogs.e(e.getMessage());
        }
        mContext.unregisterReceiver(mBluetoothReceiver);
    }
}
