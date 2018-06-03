package com.sdwfqin.bluetoothdemo.send;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdwfqin.bluetoothdemo.R;
import com.sdwfqin.cbt.CbtManager;
import com.sdwfqin.cbt.callback.SendDataCallback;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：发送数据
 *
 * @author zhangqin
 * @date 2018/6/2
 */
public class SendDataActivity extends AppCompatActivity {

    @BindView(R.id.data)
    EditText mData;
    @BindView(R.id.send)
    Button mSend;

    final static byte[][] BYTES = {
            // 0复位打印机
            {0x1b, 0x40},
            // 标准ASCII字体1
            {0x1b, 0x4d, 0x00},
            // 压缩ASCII字体2
            {0x1b, 0x4d, 0x01},
            // 字体不放大3
            {0x1d, 0x21, 0x00},
            // 宽高加倍4
            {0x1d, 0x21, 0x11},
            // 取消加粗模式5
            {0x1b, 0x45, 0x00},
            // 选择加粗模式6
            {0x1b, 0x45, 0x01},
            // 取消倒置打印7
            {0x1b, 0x7b, 0x00},
            // 选择倒置打印8
            {0x1b, 0x7b, 0x01},
            // 取消黑白反显9
            {0x1d, 0x42, 0x00},
            // 选择黑白反显10
            {0x1d, 0x42, 0x01},
            // 取消顺时针旋转90°11
            {0x1b, 0x56, 0x00},
            // 选择顺时针旋转90°12
            {0x1b, 0x56, 0x01},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.send)
    public void onViewClicked() {

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
                .sendData(data, new SendDataCallback() {
                    @Override
                    public void sendSuccess() {

                    }

                    @Override
                    public void sendError(Throwable throwable) {
                        Toast.makeText(SendDataActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        CbtManager.getInstance().disableCancelService();
        super.onDestroy();
    }
}
