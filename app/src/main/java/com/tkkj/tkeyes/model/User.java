package com.tkkj.tkeyes.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TKKJ on 2017/3/27.
 * Author: ZYQ
 */

@Entity
public class User {

    /**
     * @Entity：将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
     *
     * @Id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，
     * 并且它默认就是自增的
     *
     * @Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，
     * 用来临时存储数据的，不会被持久化
     *
     * */


    /**
     * schemaVersion---->指定数据库schema版本号，迁移等操作会用到
     * daoPackage-------->通过gradle插件生成的数据库相关文件的包名，
     * 默认为你的entity所在的包名
     *
     * targetGenDir-------->这就是我们上面说到的自定义生成数据库文件的目录了，
     * 可以将生成的文件放到我们的java目录中，而不是build中，
     * 这样就不用额外的设置资源目录了
     *
     * */

/********************************************************************/
    /***
     *
     *  greenDAO自动生成,hash = XX;X不可变
     */

    @Id
    private long id;
    private String  name;
    private String age;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 531058479)
    public User(long id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 586692638)
    public User() {
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
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }


}
