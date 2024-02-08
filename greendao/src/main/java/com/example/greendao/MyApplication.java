package com.example.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


import com.example.greendao.model.DaoMaster;
import com.example.greendao.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class MyApplication extends Application {

    public static DaoSession mSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }

    public void initDb () {
//        获取SQLiteOpenHelper对象devOpenHelper
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "imooc2.db");
//        获取SQLiteDatabase
//        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
//        加密数据库
        Database db = devOpenHelper.getEncryptedWritableDb("123");
//        创建DaoMaster实例
//        DaoMaster保存数据库对象（SQLiteDatabase）并管理特定模式的Dao类（而不是对象）。
//        它具有静态方法来创建表或将它们删除。
//        其内部类OpenHelper和DevOpenHelper是在SQLite数据库中创建模式的SQLiteOpenHelper实现。
        DaoMaster daoMaster = new DaoMaster(db);
//        管理特定模式的所有可用Dao对象
        mSession = daoMaster.newSession();
    }


}
