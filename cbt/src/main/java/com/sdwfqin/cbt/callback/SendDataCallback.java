package com.sdwfqin.cbt.callback;

/**
 * 描述：发送数据
 * <p>
 * 注意线程
 *
 * @author zhangqin
 * @date 2018/6/1
 */
public interface SendDataCallback {

    /**
     * 发送成功
     */
    void sendSuccess();

    /**
     * 发送失败
     *
     * @param throwable
     */
    void sendError(Throwable throwable);
}
