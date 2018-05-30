package com.sdwfqin.bluetoothdemo;

import android.app.Application;

import com.sdwfqin.cbt.CbtManager;

/**
 * 描述：
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CbtManager
                .getInstance()
                .init(this)
                .enableLog(true);
    }
}
