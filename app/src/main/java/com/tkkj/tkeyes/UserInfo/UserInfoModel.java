package com.tkkj.tkeyes.UserInfo;

import com.tkkj.tkeyes.DBManager.DBManager;
import com.tkkj.tkeyes.DataBase.DaoMaster;
import com.tkkj.tkeyes.DataBase.DaoSession;
import com.tkkj.tkeyes.DataBase.User;
import com.tkkj.tkeyes.DataBase.UserDao;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by TKKJ on 2017/4/5.
 */


/**
 * 然后就可以根据UserInfoModel .getInstance()拿到单例进行增删改查了
*/

public class UserInfoModel {

    //增
//    private void insertData() {
//        name = et_Name.getText().toString().trim();
//        age = et_age.getText().toString();
//        sex = et_sex.getText().toString().trim();
//        salary = et_Salary.getText().toString().trim();
//        User insertData = new User(null, name, age, sex, salary);
//        dao.insert(insertData);
//    }

    //删
//
//    private voiddeleteData(Long id){
//        userInfoDao.deleteByKey(id);
//    }

    //改
//    private void updateData(int i) {
//        name = et_Name.getText().toString().trim();
//        age = et_age.getText().toString();
//        sex = et_sex.getText().toString().trim();
//        salary = et_Salary.getText().toString().trim();
//        User updateData = new User(listsUser.get(i).getId(), name, age, sex, salary);    dao.update(updateData);
//    }

    //查
//    private void queryData() {
//        List<User> users = dao.loadAll();
//        String userName = "";
//        for (int i = 0; i < users.size(); i++) {
//            userName += users.get(i).getName() + ",";
//        }
//        Toast.makeText(this, "查询全部数据==>" + userName, Toast.LENGTH_SHORT).show();
//    }

    /**
     * 用户管理类
     */
        private DaoMaster daoMaster;
        private DaoSession daoSession;
        private UserDao userInfoDao;
        private static UserInfoModel mUserInfoModel;

        private UserInfoModel() {
            daoMaster = new DaoMaster(DBManager.getInstance().getWritableDatabase());
            daoSession = daoMaster.newSession();
            userInfoDao = daoSession.getUserDao();
        }

        public static UserInfoModel getInstance() {
            if (mUserInfoModel == null) {
                mUserInfoModel = new UserInfoModel();
            }
            return mUserInfoModel;
        }


        /**
         * 用户登陆
         *
         * @param uid
         * @param upwd
         * @return
         */
        public User login(String uid, String upwd) {
            QueryBuilder<User> qb = userInfoDao.queryBuilder();
            //逻辑有问题uid与name
            qb.where(UserDao.Properties.Name.eq(uid), UserDao.Properties.Age.eq(upwd));
            User userInfos = qb.build().unique();
            return userInfos;
        }


        /**
         * 根据用户ID删除用户
         */
        public void deleteUserById(String uId) {
            QueryBuilder<User> qb = userInfoDao.queryBuilder();
            DeleteQuery<User> dq = qb.where(UserDao.Properties.Name.eq(uId)).buildDelete();
            dq.executeDeleteWithoutDetachingEntities();
        }

        /**
         * 修改用户密码
         */
        public User modifyUserPwd(String uId, String oldPwd, String newPwd) {
            //验证旧密码
            User mUserInfo = login(uId, oldPwd);
            if (mUserInfo == null) {
                return null;
            }
            QueryBuilder<User> qb = userInfoDao.queryBuilder();
            qb.where(UserDao.Properties.Name.eq(uId));
            User userInfo = qb.build().unique();
            if (userInfo != null) {
                //setName;
                userInfo.setName(newPwd);
                //更新密码
                userInfoDao.update(userInfo);
                mUserInfo = login(uId, newPwd);
                return mUserInfo;
            }
            return null;
        }

        /**
         * 获取用户列表
         *
         * @return
         */
        public List<User> queryAllUser() {
            QueryBuilder<User> qb = userInfoDao.queryBuilder();
            List<User> userInfos = qb.build().list();
            return userInfos;
        }

        /**
         * 根据用户UID查询UserInfo
         *
         * @param uid
         * @return
         */
//        public UserqueryUserById(String uid) {
//            QueryBuilder<User> qb = userInfoDao.queryBuilder();
//            qb.where(UserDao.Properties.UId.eq(uid));
//            User userInfo = qb.build().unique();
//            return userInfo;
//        }
}
