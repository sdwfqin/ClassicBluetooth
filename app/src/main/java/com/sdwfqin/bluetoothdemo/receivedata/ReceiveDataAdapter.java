package com.sdwfqin.bluetoothdemo.receivedata;

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
public class ReceiveDataAdapter extends BaseQuickAdapter<ReceiveDataModel, BaseViewHolder> {

    public ReceiveDataAdapter(@Nullable List<ReceiveDataModel> data) {
        super(R.layout.item_receive_data, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceiveDataModel item) {
        helper.setText(R.id.title, item.getDevice().getName())
                .setText(R.id.sub_title, item.getDevice().getAddress())
                .setText(R.id.msg, item.getMsg());
    }
}
