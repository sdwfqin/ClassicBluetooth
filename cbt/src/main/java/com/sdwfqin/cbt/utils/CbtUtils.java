package com.sdwfqin.cbt.utils;

/**
 * 描述：工具
 *
 * @author zhangqin
 * @date 2018/6/2
 */
public class CbtUtils {

    /**
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1, byte[] byte2) {
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }
}
