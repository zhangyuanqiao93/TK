package com.tkkj.tkeyes.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.tkkj.tkeyes.model.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by TKKJ on 2017/3/30.
 * GreenDao Manager
 */

public class DaoManager {
    private static DaoManager mInstance;
    private static DaoMaster.DevOpenHelper openHelper;
    private static final String DB_NAME = "tk.sqlite";

    private volatile static DaoManager daoManager;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static Context context;

    public DaoManager(Context context) {
        return;
    }

    public void initContext(Context context){
        this.context = context;
    }
    public DaoMaster getDaoMaster(){
        if (daoMaster == null){
            //通过DaoMaster的内部类DevOpenHelper，我们可以得到一个SQLiteOpenHelper对象。
            helper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
            daoMaster =new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
    /**
     * 获取单例引用
     * @param context
     * @return
     */
    public static DaoManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DaoManager.class) {
                if (mInstance == null) {
                    mInstance = new DaoManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    public static SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
    */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }
    /**
     * 插入一条记录
     *
     * @param user
     */
    public void insertUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        long i = userDao.insert(user);
    }
    /**
     * 插入用户集合
     *
     * @param users
     */
    public void insertUserList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.insertInTx(users);
    }

    /**
     * 删除一条记录
     *
     * @param user
     */
    public void deleteUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.delete(user);
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.update(user);
    }
    /**
     * 查询用户列表
     */
    public List<User> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        return list;
    }
    /**
     * 查询用户列表
     */
    public List<User> queryUserList(int age) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        List<User> list = qb.list();
        return list;
    }

    public void dbTest(){

        DaoManager daoManager = DaoManager.getInstance(context);
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(i);
            user.setAge(String.valueOf(i * 3));
            user.setName("第" + i + "人");
            daoManager.insertUser(user);
        }
        List<User> userList = daoManager.queryUserList();
        for (User user : userList) {
            Log.e("TAG", "queryUserList--before-->" + user.getId() + "--" + user.getName() +"--"+user.getAge());
            if (user.getId() == 0) {
                daoManager.deleteUser(user);
            }
            if (user.getId() == 3) {
                user.setAge(String.valueOf(10));
                daoManager.updateUser(user);
            }
        }
        userList = daoManager.queryUserList();
        for (User user : userList) {
            Log.e("TAG", "queryUserList--after--->" + user.getId() + "---" + user.getName()+"--"+user.getAge());
        }
    }
}
