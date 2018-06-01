package com.sdwfqin.bluetoothdemo.scan;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sdwfqin.bluetoothdemo.R;
import com.sdwfqin.bluetoothdemo.send.SendDataActivity;
import com.sdwfqin.cbt.CbtManager;
import com.sdwfqin.cbt.callback.ConnectDeviceCallBack;
import com.sdwfqin.cbt.callback.ScanCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：扫描设备
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class ScanListActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private Context mContext;
    private ScanListAdapter mScanListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_list);
        ButterKnife.bind(this);
        mContext = this;

        initList();
        initData();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mScanListAdapter = new ScanListAdapter(null);
        mRv.setAdapter(mScanListAdapter);
        mScanListAdapter.setOnItemClickListener(
                (adapter, view, position) -> {
                    BluetoothDevice item = mScanListAdapter.getItem(position);
                    CbtManager
                            .getInstance()
                            .connectDevice(item, new ConnectDeviceCallBack() {
                                @Override
                                public void connectSuccess(BluetoothSocket socket, BluetoothDevice device) {
                                    startActivity(new Intent(mContext, SendDataActivity.class));
                                    Toast.makeText(mContext, "连接成功！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void connectError(Throwable throwable) {
                                    Toast.makeText(mContext, "连接失败：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
        );
    }

    /**
     * 加载数据
     */
    private void initData() {
        CbtManager
                .getInstance()
                .scan(new ScanCallback() {
                    @Override
                    public void onScanStart(boolean isOn) {

                    }

                    @Override
                    public void onScanStop(List<BluetoothDevice> devices) {
                        mScanListAdapter.setNewData(devices);
                    }

                    @Override
                    public void onFindDevice(BluetoothDevice device) {
                        mScanListAdapter.addData(device);
                    }
                });
    }
}
