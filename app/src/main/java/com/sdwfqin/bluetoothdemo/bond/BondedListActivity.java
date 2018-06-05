package com.sdwfqin.bluetoothdemo.bond;

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
import com.sdwfqin.cbt.callback.ConnectDeviceCallback;
import com.sdwfqin.cbt.utils.CbtLogs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：已配对设备
 *
 * @author zhangqin
 * @date 2018/6/5
 */
public class BondedListActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private Context mContext;
    private BondedListAdapter mBondedListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonded_list);
        ButterKnife.bind(this);

        mContext = this;

        initList();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mBondedListAdapter = new BondedListAdapter(null);
        mRv.setAdapter(mBondedListAdapter);
        mBondedListAdapter.setOnItemClickListener(
                (adapter, view, position) -> {
                    BluetoothDevice item = mBondedListAdapter.getItem(position);
                    CbtManager
                            .getInstance()
                            .connectDevice(item, new ConnectDeviceCallback() {
                                @Override
                                public void connectSuccess(BluetoothSocket socket, BluetoothDevice device) {
                                    Toast.makeText(mContext, "连接成功！", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, SendDataActivity.class));
                                }

                                @Override
                                public void connectError(Throwable throwable) {
                                    CbtLogs.e(throwable.getMessage());
                                    Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
        );

        List<BluetoothDevice> bondedDevices = CbtManager.getInstance().getBondedDevices();
        mBondedListAdapter.setNewData(bondedDevices);
    }
}
