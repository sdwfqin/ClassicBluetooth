package com.sdwfqin.bluetoothdemo.send;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.sdwfqin.bluetoothdemo.R;
import com.sdwfqin.cbt.CbtManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendDataActivity extends AppCompatActivity {

    @BindView(R.id.data)
    EditText mData;
    @BindView(R.id.send)
    Button mSend;

    final static byte[][] byteCommands = {{0x1b, 0x40},// 0复位打印机
            {0x1b, 0x4d, 0x00},// 标准ASCII字体1
            {0x1b, 0x4d, 0x01},// 压缩ASCII字体2
            {0x1d, 0x21, 0x00},// 字体不放大3
            {0x1d, 0x21, 0x11},// 宽高加倍4
            {0x1b, 0x45, 0x00},// 取消加粗模式5
            {0x1b, 0x45, 0x01},// 选择加粗模式6
            {0x1b, 0x7b, 0x00},// 取消倒置打印7
            {0x1b, 0x7b, 0x01},// 选择倒置打印8
            {0x1d, 0x42, 0x00},// 取消黑白反显9
            {0x1d, 0x42, 0x01},// 选择黑白反显10
            {0x1b, 0x56, 0x00},// 取消顺时针旋转90°11
            {0x1b, 0x56, 0x01},// 选择顺时针旋转90°12
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.send)
    public void onViewClicked() {
        CbtManager.getInstance().sendData(byteCommands[0]);
        CbtManager.getInstance().sendData(byteCommands[1]);
        CbtManager.getInstance().sendData(mData.getText().toString(), "GBK");
        CbtManager.getInstance().sendData("\n\n\n\n\n\n", "GBK");
    }

    @Override
    protected void onDestroy() {
        CbtManager.getInstance().disableCancelService();
        super.onDestroy();
    }
}
