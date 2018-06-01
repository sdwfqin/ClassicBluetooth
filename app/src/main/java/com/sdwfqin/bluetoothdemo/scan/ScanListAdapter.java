package com.sdwfqin.bluetoothdemo.scan;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdwfqin.bluetoothdemo.R;
import com.sdwfqin.cbt.model.DeviceModel;

import java.util.List;

/**
 * 描述：
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class ScanListAdapter extends BaseQuickAdapter<DeviceModel, BaseViewHolder> {
    public OnItemOnClick onItemOnClick =null;

    public void setOnItemOnClick(OnItemOnClick onItemOnClick) {
        this.onItemOnClick = onItemOnClick;
    }

    public ScanListAdapter(@Nullable List<DeviceModel> data) {
        super(R.layout.item_dev_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceModel item) {
        helper.setText(R.id.title, item.getName())
                .setText(R.id.sub_title, item.getAddress());
        helper.setOnClickListener(R.id.item_dev_list_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemOnClick!=null){
                    onItemOnClick.onItemOnClick(item);
                }
            }
        });
    }
    interface OnItemOnClick{
        void onItemOnClick(DeviceModel item);
    }
}
