package com.tkkj.tkeyes.utils;

import android.content.Context;

import com.tkkj.tkeyes.DataBase.DaoManager;
import com.tkkj.tkeyes.DataBase.DaoMaster;
import com.tkkj.tkeyes.DataBase.DaoSession;
import com.tkkj.tkeyes.DataBase.User;
import com.tkkj.tkeyes.DataBase.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import retrofit2.http.PUT;

/**
 * Created by TKKJ on 2017/3/30.
 */

public class CommonUtils {
    private DaoManager daoManager;
    private UserDao dao;
    private DaoSession daoSession;
    public CommonUtils(Context context) {
        daoManager = DaoManager.getInstance(context);
        daoManager.initContext(context);
        DaoMaster daoMaster = new DaoMaster(DaoManager.getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        dao = daoSession.getUserDao();
    }

    /**
     * 增加用户
     * */
    public void insertUser(User user){
        dao.insert(user);
    }
    public void insertMultiStudent(final List<User> uList){
//        daoManager.getDaoSession().runInTx(new Runnable() {
//            @Override
//            public void run() {
//                for (User stu:uList) {
//                    dao.insertOrReplace(stu);
//                }
//            }
//            });
    }
    public void deleteStudent(User user){
        dao.delete(user);
    }
    public void modifyStudent(User user){
        dao.update(user);
    }
    public void queryStudent( long id){
        User user = daoSession.load(User.class, id);
    }
    public void queryStudent1(){
        List<User>uList =daoSession.queryRaw(User.class,"where name like ? and _id>?",new String[]{"%张%","1001L"});
    }
    public void queryAllStudent(){
        List<User> uList =  daoSession.loadAll(User.class);
    }
    /**
     * whereOr语句相当于select *from where name like ? or name = ? or age>?
     * ge是 >= 、like 则是包含的意思
     * whereOr是或的意思，比如下面的whereOr的意思就是age>=22||name 包含 张三
     * where则是age>=22 && name 包含 张三
     *greenDao除了ge和like操作之外还有很多其他方法
     * eq == 、 noteq != 、  gt >  、lt <  、le  <=  、between 俩者之间
     * in  在某个值内   、notIn  不在某个值内
     */
    public void queryUser2(){
        QueryBuilder<User> builder = daoSession.queryBuilder(User.class);
        List<User> uList = builder.where(UserDao.Properties.Age.ge(22))
                .where(UserDao.Properties.Name.like("张三")).list();
        uList = builder.whereOr(UserDao.Properties.Age.ge(22),
                UserDao.Properties.Name.like("张三")).list();
    }
}
