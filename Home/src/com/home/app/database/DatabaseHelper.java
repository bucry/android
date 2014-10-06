package com.home.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    private static final String DB_NAME = "home.db"; //数据库名称
    private static final int version = 1; //数据库版本
    protected SQLiteDatabase db;
    protected DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	this.db = db;
        String sql = "create table user(username varchar(20) not null , password varchar(60) not null );";          
        db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
    }
 
}