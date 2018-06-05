package com.sdwfqin.cbt.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.sdwfqin.cbt.callback.ServiceListenerCallback;
import com.sdwfqin.cbt.utils.CbtConstant;
import com.sdwfqin.cbt.utils.CbtExecutor;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * 描述：蓝牙服务端服务，只连接一个设备
 *
 * @author zhangqin
 * @date 2018/6/1
 */
public class CbtServiceListener {

    private BluetoothServerSocket mBluetoothServerSocket;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mBluetoothSocket;
    private ServiceListenerCallback mListenerCallback;

    public static CbtServiceListener getInstance() {
        return CbtServiceListenerHolder.CBT_SERVICE_SERVICE;
    }

    private static class CbtServiceListenerHolder {
        private static final CbtServiceListener CBT_SERVICE_SERVICE = new CbtServiceListener();
    }

    /**
     * 初始化
     *
     * @return
     */
    public void init(BluetoothAdapter bluetoothAdapter, ServiceListenerCallback callback) {
        mListenerCallback = callback;
        if (mBluetoothAdapter != null || mBluetoothServerSocket != null) {
            return;
        }
        mBluetoothAdapter = bluetoothAdapter;
        BluetoothServerSocket tmp = null;
        try {
            // 明文传输，无需配对
            // adapter.listenUsingInsecureRfcommWithServiceRecord(TAG, SPP_UUID);
            // 加密传输，会自动执行配对
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(CbtConstant.CBT_NAME, CbtConstant.CBT_UUID);
        } catch (IOException e) {
            mListenerCallback.onStartError(e);
            CbtLogs.e(e.getMessage());
            return;
        }
        mBluetoothServerSocket = tmp;
        listener();
    }

    /**
     * 监听连接
     */
    private void listener() {
        CbtExecutor
                .getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // Keep listening until exception occurs or a socket is returned
                        while (true) {
                            try {
                                mBluetoothSocket = mBluetoothServerSocket.accept();
                            } catch (IOException e) {
                                CbtLogs.e(e.getMessage());
                                mListenerCallback.onStartError(e);
                                break;
                            }
                            // 关闭监听，只连接一个设备
                            if (mBluetoothSocket != null) {
                                // 循环读取对方数据(若没有数据，则阻塞等待)
                                loopRead();
                                // 关闭监听，只连接一个设备
                                try {
                                    mBluetoothServerSocket.close();
                                } catch (IOException e) {
                                    CbtLogs.e(e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                });
    }

    /**
     * 循环读取对方数据(若没有数据，则阻塞等待)
     */
    private void loopRead() {
        try {
            if (!mBluetoothSocket.isConnected()) {
                mBluetoothSocket.connect();
            }
            DataInputStream in = new DataInputStream(mBluetoothSocket.getInputStream());
            //死循环读取
            while (true) {
                // 读取文件内容
                int l;
                byte[] b = new byte[1024];
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                while ((l = in.read(b)) != -1) {
                    out.write(b, 0, l);
                    break;
                }
                String s = new String(out.toByteArray(), "GBK");
                mListenerCallback.onDataListener(s, mBluetoothSocket.getRemoteDevice());
            }
        } catch (Throwable e) {
            mListenerCallback.onStartError(e);
            CbtLogs.e(e.getMessage());
        }
    }

    public void cancel() {
        try {
            mBluetoothServerSocket.close();
            mBluetoothSocket.close();
            mBluetoothAdapter = null;
            mBluetoothServerSocket = null;
        } catch (IOException e) {
            CbtLogs.e(e.getMessage());
        }
    }
}
