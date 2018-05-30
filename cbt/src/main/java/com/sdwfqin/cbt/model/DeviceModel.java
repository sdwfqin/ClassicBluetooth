package com.sdwfqin.cbt.model;

/**
 * 描述：蓝牙设备信息
 *
 * @author zhangqin
 * @date 2018/5/30
 */
public class DeviceModel {

    private String name;
    private String address;

    public DeviceModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
