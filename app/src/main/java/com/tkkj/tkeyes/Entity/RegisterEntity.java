package com.tkkj.tkeyes.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TKKJ on 2017/4/15.
 */

@Entity
public class RegisterEntity {
    @Id
    private long id;
    private String username;
    private String password;
    private String confirmPass;
    private String age;
    private String degree;
    private String astigmatism;
    private String accessID;
    private String phone;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 1746721080)
    public RegisterEntity(long id, String username, String password,
            String confirmPass, String age, String degree, String astigmatism,
            String accessID, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPass = confirmPass;
        this.age = age;
        this.degree = degree;
        this.astigmatism = astigmatism;
        this.accessID = accessID;
        this.phone = phone;
    }
    @Generated(hash = 470144680)
    public RegisterEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPass() {
        return this.confirmPass;
    }
    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getDegree() {
        return this.degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getAstigmatism() {
        return this.astigmatism;
    }
    public void setAstigmatism(String astigmatism) {
        this.astigmatism = astigmatism;
    }
    public String getAccessID() {
        return this.accessID;
    }
    public void setAccessID(String accessID) {
        this.accessID = accessID;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
