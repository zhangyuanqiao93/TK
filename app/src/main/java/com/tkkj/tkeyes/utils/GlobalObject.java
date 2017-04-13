package com.tkkj.tkeyes.utils;

import java.io.Serializable;

/**
 * Created by TKKJ on 2017/3/25.
 */

public class GlobalObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private GlobalObject() {
    }

    public static GlobalObject getInstance() {
        return GlobalObjectHolder.INSTANCE;
    }

    /**
     * readResolve方法应对单例对象被序列化时候
     */
    private Object readResolve() {
        return getInstance();
    }

    private static class GlobalObjectHolder {
        static final GlobalObject INSTANCE = new GlobalObject();
    }

    //广播事件标记
    public static final String GLOBAL_BROADCAST = "广播时间标记";

    //蓝牙功能标记
    public static final int STRAT_DEVICE = 24;//启动设备
    public static final int  INTERVENE_DEVICE = 25;//干预设备
    public static final int TEST_DEVICE = 26;//测试设备
    public static final int CONECTE_DEVICE = 27;//连接设备

}
