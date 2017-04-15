package com.tkkj.tkeyes.CacheManger;

/**
 * Created by TKKJ on 2017/3/30.
 */


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tkkj.tkeyes.DataBase.DaoMaster;
import com.tkkj.tkeyes.DataBase.DaoSession;
import com.tkkj.tkeyes.DataBase.RegisterEntityDao;
import com.tkkj.tkeyes.DataBase.UserDao;
import com.tkkj.tkeyes.Entity.RegisterEntity;
import com.tkkj.tkeyes.base.BasicApplication;
import com.tkkj.tkeyes.model.User;
import com.tkkj.tkeyes.utils.DataEncryptUtil;

import org.greenrobot.greendao.query.Query;
import org.json.JSONObject;

import java.util.List;

/**
 * 缓存管理
 * */
public class CacheManager {
    private static final String TAG = CacheManager.class.getSimpleName();
    private static final String APP_CACHE = "cache_manager_file";
    private static final String APP_CACHE_TIME = "cache_manager_file_time";
    private static final long maxTime = 60 * 60 * 24 * 1000;



    public Context getApplicationContext(){
        return BasicApplication.getContext();
    }
    //轨迹数据保存
    public static void setData(String url, String response){
        //如果有就更新，否则保存
        if (hasCache(url)) {
            updateData(url, response);
        } else {
            insert(url, response);
        }
    }

    /**
     * 插入数据
     * @param url
     * @param response
     */
    public static void insert(final String url, final String  response) {
        UserDao modelDao = GreenDaoManager.getInstance().getSession().getUserDao();
        User model = new User();
        model.setAge(url);
        model.setName(response);

       long i=modelDao.insert(model);//主键必须为long类型，且自动自增

        Log.e("tag","缓存存储序号："+i);

    }

    private static void updateData(String url, String response) {

        User model = (User) getQuery(url).unique();
        if (model != null) {
            model.setName(response);
            GreenDaoManager.getInstance().getSession().getUserDao().update(model);
        } else {
            Log.e("tag", "更新数据失败");
        }
    }

    /**
     * 读取数据
     *
     * @param url
     * @return
     */
    public static String getData(String url) {

        User model = (User) getQuery(url).unique();
        if (model != null) {
            return model.getName();
        } else {
            return "";
        }
    }

    public static boolean hasCache(String url) {

        User model = (User) getQuery(url).unique();
        if (model != null) {
            return true;
        } else {
            return false;
        }
    }


    public static Query getQuery(String url) {
        return GreenDaoManager.getInstance().getSession().getUserDao().queryBuilder().where(UserDao.Properties.Age.eq(DataEncryptUtil.getUserId())).build();
    }


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
