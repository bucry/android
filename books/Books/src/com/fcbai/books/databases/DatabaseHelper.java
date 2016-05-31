package com.fcbai.books.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    private static final String DB_NAME = "books.db";
    private static final int version = 1;
     
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	String createBookSql = "create table book (id integer primary key autoincrement , name varchar(1024), desc varchar(1024), start_ate varchar(1024), end_date varchar(1024), is_notify boolean, start_number int, type int, current_page_no int, total_page_no int, category int, image_path varchar(1024)";
    	db.execSQL(createBookSql);
    	
    	String createUpdateSql ="";
    	
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
    }
 
}
