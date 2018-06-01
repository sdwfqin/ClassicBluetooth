package com.sdwfqin.bluetoothdemo.scan;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdwfqin.bluetoothdemo.R;

import java.util.List;

/**
 * 描述：
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class ScanListAdapter extends BaseQuickAdapter<BluetoothDevice, BaseViewHolder> {

    public ScanListAdapter(@Nullable List<BluetoothDevice> data) {
        super(R.layout.item_dev_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BluetoothDevice item) {
        helper.setText(R.id.title, item.getName())
                .setText(R.id.sub_title, item.getAddress());
    }
}
