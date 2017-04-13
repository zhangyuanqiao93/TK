package com.tkkj.tkeyes.Entity;

import java.util.List;

/**
 * Created by TKKJ on 2017/4/13.
 */

public class UserInfoEntity {

    private String requeststatus;
    private long userId;
    private List<String> deviceinfo;  //deviceinfo是数组

    public void setRequeststatus(String requeststatus) {
        this.requeststatus = requeststatus;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDeviceinfo(List<String> deviceinfo) {
        this.deviceinfo = deviceinfo;
    }

    public String getRequeststatus() {
        return requeststatus;
    }

    public long getUserId() {
        return userId;
    }

    public List<String> getDeviceinfo() {
        return deviceinfo;
    }
}
