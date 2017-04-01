package com.tkkj.tkeyes.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TKKJ on 2017/3/27
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {


    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    /**
     * 上述代码第一次启动应用，我们会创建这个my.sqLiteDatabase的文件，
     * 并且会执行onCreate()里的方法，
     * 创建一个Person的表，主键personId和name字段；
     * 接着如我我们修改db的版本号，那么下次启动就会调用onUpgrade()里的方法,往表中再插入一个字段！
     * 另外这里是插入一个字段,所以数据不会丢失，如果是重建表的话，表中的数据会全部丢失
     * 接下来解决这个问题
     *
     * Step 1：自定义一个类继承SQLiteOpenHelper类
     * Step 2：在该类的构造方法的super中设置好要创建的数据库名,版本号
     * Step 3：重写onCreate( )方法创建表结构
     * Step 4：重写onUpgrade( )方法定义版本号发生改变后执行的操作
     * */
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person(personId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }
}
