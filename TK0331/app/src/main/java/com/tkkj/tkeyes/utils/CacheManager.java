package com.tkkj.tkeyes.utils;

import android.app.Application;
import android.widget.Toast;

import com.tkkj.tkeyes.DataBase.DaoMaster;
import com.tkkj.tkeyes.DataBase.DaoSession;
import com.tkkj.tkeyes.DataBase.User;
import com.tkkj.tkeyes.DataBase.UserDao;

import java.util.List;

import static com.instabug.library.Instabug.getApplicationContext;

/**
 * Created by TKKJ on 2017/3/27.
 */

public class CacheManager {

    /**************************************/
    /***
     *
     * 核心：增删改查操作
     *
     */

    /**
     * 增加数据
     * */

//    那个参数和blog的不一样，我不知道那传什么

    /**
     * “notes-db”是我们自定的数据库名字，
     * 应为我们之前创建了一个Entity叫做User，
     * 所以greenDAO自定帮我们生成的UserDao，,
     * 拿到了这个UserDao，我们就可以操作User这张表了。
     * 一个DaoMaster就代表着一个数据库的连接；
     * DaoSession可以让我们使用一些Entity的基本操作和获取Dao操作类，
     * DaoSession可以创建多个，每一个都是属于同一个数据库连接的。
     * */

//      实例化一个User对象，然后调用userDao的insert方法就可以了。
//      ????将User对象的id设置为null的时候，数据库会自动为其分配自增的id。
//    User user = new User(null,"ZYQ");

    /**
     * Created by TKKJ on 2017/03/28
     * Function：增删改查数据
     * Author:ZYQ
     * */
public  void test(){
    DaoMaster.DevOpenHelper  devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"note-db",null);
    DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
    DaoSession daoSession = daoMaster.newSession();
    UserDao userDao = daoSession.getUserDao();
//    增加数据
//    userDao.insert(new User((long)8,"ZYQ"));

/**
 * 查找数据
 * 通过userDao的queryBuilder()方法，生成一个查找构造器，
 * 可以给构造器添加where条件判断、按照某某字段排序以及查询的条数等基本的数据库操作。
 * list()方法表示查询的结果为一个集合.
 * */
    List<User> list = userDao.queryBuilder()
            .where(UserDao.Properties.Id.notEq(999))
            .orderAsc(UserDao.Properties.Id)
            .limit(5).build().list();

    /**
     * 修改数据
     * 修改数据的第一步是把需要修改的条目给查询出来，然后修改该条目，
     * 最后调用userDao的update方法即可。unique()表示查询结果为一条数据，
     * 若数据不存在，updateUser为null。
     * */
    User updateUser = userDao.queryBuilder().where(UserDao.Properties.Name.eq("ZYQ")).build().unique();
    if (updateUser!=null){
        updateUser.setName("LDY");
        userDao.update(updateUser);
        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
    }else {
        Toast.makeText(getApplicationContext(),"用户不存在",Toast.LENGTH_SHORT).show();
    }


    /**
     * 删除数据
     *先查询出需要删除的条目，然后调用userDao的deleteByKey将该条目的主键传入即可删除。
     * */
    User deleteUser = userDao.queryBuilder().where(UserDao.Properties.Name.eq("ZYQ")).build().unique();
        if (deleteUser!=null){
        userDao.deleteByKey(deleteUser.getId());
        }
    }
}
