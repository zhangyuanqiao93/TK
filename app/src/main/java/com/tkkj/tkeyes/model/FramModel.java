package com.tkkj.tkeyes.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TKKJ on 2017/4/13.
 */

@Entity
public class FramModel {
@Id
    private  String  id;
    private String framId;
    private String userId;
    private String data;
    @Generated(hash = 2043367091)
    public FramModel(String id, String framId, String userId, String data) {
        this.id = id;
        this.framId = framId;
        this.userId = userId;
        this.data = data;
    }
    @Generated(hash = 1030502550)
    public FramModel() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFramId() {
        return this.framId;
    }
    public void setFramId(String framId) {
        this.framId = framId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
