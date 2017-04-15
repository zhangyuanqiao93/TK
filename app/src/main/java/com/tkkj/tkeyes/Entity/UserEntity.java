package com.tkkj.tkeyes.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TKKJ on 2017/4/13.
 */

@Entity
public class UserEntity {

    @Id
    private long id;
    private String name;
    private String password;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 715080455)
    public UserEntity(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
