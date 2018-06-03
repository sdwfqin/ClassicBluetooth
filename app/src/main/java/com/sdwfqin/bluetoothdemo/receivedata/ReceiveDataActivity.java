package com.sdwfqin.bluetoothdemo.receivedata;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sdwfqin.bluetoothdemo.R;
import com.sdwfqin.cbt.CbtManager;
import com.sdwfqin.cbt.callback.ServiceListenerCallback;
import com.sdwfqin.cbt.utils.CbtLogs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：接收数据
 *
 * @author zhangqin
 * @date 2018/6/3
 */
public class ReceiveDataActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private Context mContext;
    private ReceiveDataAdapter mReceiveDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_data);
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
        mReceiveDataAdapter = new ReceiveDataAdapter(null);
        mRv.setAdapter(mReceiveDataAdapter);
    }

    /**
     * 加载数据
     */
    private void initData() {
        CbtManager.getInstance().startServiceListener(new ServiceListenerCallback() {
            @Override
            public void onStartError(Throwable throwable) {
                CbtLogs.e(throwable.getMessage());
            }

            @Override
            public void onDataListener(String s, BluetoothDevice device) {
                CbtLogs.e("onDataListener：" + s);
                runOnUiThread(() ->
                        mReceiveDataAdapter.addData(new ReceiveDataModel(device, s))
                );
            }
        });
    }
}
