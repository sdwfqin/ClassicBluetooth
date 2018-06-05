package com.sdwfqin.cbt.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.sdwfqin.cbt.callback.ConnectDeviceCallback;
import com.sdwfqin.cbt.callback.SendDataCallback;
import com.sdwfqin.cbt.utils.CbtConstant;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：蓝牙设备客户端服务
 *
 * @author zhangqin
 * @date 2018/6/1
 */
public class CbtClientService {

    private BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private ConnectDeviceCallback mCallBack;
    public boolean isConnection = false;

    public static CbtClientService getInstance() {
        return CbtClientServiceHolder.CBT_CLIENT_SERVICE;
    }

    private static class CbtClientServiceHolder {
        private static final CbtClientService CBT_CLIENT_SERVICE = new CbtClientService();
    }

    /**
     * 初始化
     *
     * @return
     */
    public void init(BluetoothAdapter bluetoothAdapter, BluetoothDevice device, ConnectDeviceCallback callBack) {
        mCallBack = callBack;
        if (mBluetoothDevice != null) {
            if (mBluetoothDevice.getAddress().equals(device.getAddress())) {
                mCallBack.connectSuccess(mBluetoothSocket, mBluetoothDevice);
                return;
            } else {
                cancel();
            }
        }

        mBluetoothAdapter = bluetoothAdapter;
        mBluetoothDevice = device;
        BluetoothSocket tmp = null;
        try {
            //尝试建立安全的连接
            tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(CbtConstant.CBT_UUID);
        } catch (IOException e) {
            mCallBack.connectError(e);
        }
        mBluetoothSocket = tmp;
        connect();
    }

    private void connect() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothSocket.connect();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mBluetoothAdapter = null;
                        mBluetoothDevice = null;
                        mCallBack.connectError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public BluetoothSocket getBluetoothSocket() {
        return mBluetoothSocket;
    }

    /**
     * 发送数据
     */
    public void sendData(final List<byte[]> data, final SendDataCallback callback) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                OutputStream outputStream = mBluetoothSocket.getOutputStream();
                for (int i = 0; i < data.size(); i++) {
                    outputStream.write(data.get(i));
                    outputStream.flush();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        callback.sendSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.sendError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        try {
            mBluetoothSocket.close();
            mBluetoothAdapter = null;
            mBluetoothDevice = null;
            isConnection = false;
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
    }
}
