package com.tkkj.tkeyes.DBManager;

import android.database.sqlite.SQLiteDatabase;

import com.tkkj.tkeyes.DataBase.DaoMaster;
import com.tkkj.tkeyes.base.BasicApplication;

/**
 * Created by TKKJ on 2017/4/5.
 */

/**
 * 数据库管理工具
 */

public class DBManager {
    private DaoMaster.DevOpenHelper devOpenHelper;
    private static DBManager instance;
    private static final String DB_NAME = "user.db";

    private DBManager() {
        devOpenHelper = new DaoMaster.DevOpenHelper(BasicApplication.getInstance(), DB_NAME, null);
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public SQLiteDatabase getWritableDatabase() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(BasicApplication.getInstance(), DB_NAME, null);
        }
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        return db;
    }
}
