package com.sdwfqin.bluetoothdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sdwfqin.bluetoothdemo.scan.ScanListActivity;
import com.sdwfqin.cbt.CbtManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 描述：经典蓝牙示例
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.list)
    ListView mList;

    private Context mContext;

    private String[] mTitle = new String[]{"开启蓝牙", "关闭蓝牙", "设备列表"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        mList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, mTitle));

        initListener();

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "App正常运行需要存储权限、媒体权限", 1, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initListener() {
        mList.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    CbtManager
                            .getInstance()
                            .enableBluetooth(isOn -> {
                                if (isOn) {
                                    Toast.makeText(mContext, "蓝牙已开启", Toast.LENGTH_SHORT).show();
                                }
                            });
                    break;
                case 1:
                    CbtManager
                            .getInstance()
                            .disableBluetooth(isOn -> {
                                if (!isOn) {
                                    Toast.makeText(mContext, "蓝牙已关闭", Toast.LENGTH_SHORT).show();
                                }
                            });
                    break;
                case 2:
                    startActivity(new Intent(mContext, ScanListActivity.class));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CbtManager.getInstance().onDestroy();
    }
}
