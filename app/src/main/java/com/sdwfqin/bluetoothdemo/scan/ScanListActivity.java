package com.sdwfqin.bluetoothdemo.scan;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sdwfqin.bluetoothdemo.R;
import com.sdwfqin.cbt.CbtManager;
import com.sdwfqin.cbt.callback.ScanCallback;
import com.sdwfqin.cbt.model.DeviceModel;

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
        Log.e("haha1","hahah");
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
                    public void onScanStop(List<DeviceModel> deviceList) {
                        mScanListAdapter.setNewData(deviceList);
                    }

                    @Override
                    public void onFindDevice(DeviceModel deviceModel) {
                        mScanListAdapter.addData(deviceModel);
                    }
                });
    }
}
