# ClassicBluetooth
Android经典蓝牙工具类

已完成：

- 开启蓝牙
- 关闭蓝牙
- 扫描蓝牙设备
- 连接配对
- 发送数据
- 接收数据

TODO：

- 获取已配对设备
- 接收文件
- 功能优化、性能优化、BUG清扫

最近在做一个带有蓝牙打印机的项目，借此完成这个库的开发

# 导入

```
implementation 'com.sdwfqin.cbt:cbt:1.0.0'
```

# 使用

1. 初始化

    ``` java
    public class BaseApp extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            CbtManager
                    .getInstance()
                    // 初始化
                    .init(this)
                    // 是否打印相关日志
                    .enableLog(true);
        }
    }
    ```

2. 开启蓝牙

    ``` java
    CbtManager
        .getInstance()
        .enableBluetooth(isOn -> {
            if (isOn) {
                Toast.makeText(mContext, "蓝牙已开启", Toast.LENGTH_SHORT).show();
            }
        });
    ```

2. 关闭蓝牙

    ``` java
    CbtManager
        .getInstance()
        .disableBluetooth(isOn -> {
            if (!isOn) {
                Toast.makeText(mContext, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
            }
        });
    ```

3. 扫描设备

    ``` java
    CbtManager
        .getInstance()
        .scan(new ScanCallback() {
            @Override
            public void onScanStart(boolean isOn) {
                // 开始扫描
            }

            @Override
            public void onScanStop(List<BluetoothDevice> devices) {
                // 搜索完成
                mScanListAdapter.setNewData(devices);
            }

            @Override
            public void onFindDevice(BluetoothDevice device) {
                // 搜索到设备
                mScanListAdapter.addData(device);
            }
        });
    ```

5. 连接设备

    ``` java
    BluetoothDevice item = mScanListAdapter.getItem(position);
    CbtManager
        .getInstance()
        .connectDevice(item, new ConnectDeviceCallback() {
            @Override
            public void connectSuccess(BluetoothSocket socket, BluetoothDevice device) {
                // 连接成功
                Toast.makeText(mContext, "连接成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectError(Throwable throwable) {
                // 连接失败
                Toast.makeText(mContext, "连接失败：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    ```

5. 发送数据（回调接口目前是在子线程中调用）

    ``` java
    byte[] data;
    try {
        data = (mData.getText().toString() + "\n\n\n\n\n\n").getBytes("GBK");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return;
    }

    List<byte[]> bytes = new ArrayList<>();
    bytes.add(BYTES[0]);
    bytes.add(BYTES[1]);
    bytes.add(data);

    CbtManager
        .getInstance()
        .sendData(bytes, new SendDataCallback() {
            @Override
            public void sendSuccess() {
                // 发送成功
            }

            @Override
            public void sendError(Throwable throwable) {
                // 发送失败
                Toast.makeText(SendDataActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    ```

7. 蓝牙服务端（回调接口目前是在子线程中调用）

    ``` java
    CbtManager
        .getInstance()
        .startServiceListener(new ServiceListenerCallback() {
            @Override
            public void onStartError(Throwable throwable) {
                // 发生错误
                CbtLogs.e(throwable.getMessage());
            }

            @Override
            public void onDataListener(String s, BluetoothDevice device) {
                // 获取到数据
                runOnUiThread(() ->
                        mReceiveDataAdapter.addData(new ReceiveDataModel(device, s))
                );
            }
        });
    ```

# License

```
Copyright 2018 zhangqin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
