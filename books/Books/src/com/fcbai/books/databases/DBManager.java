package com.fcbai.books.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private Context context;
	
	private static DBManager instance = null;
	
	public static DBManager getInstance(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
		}
		return instance;
	}
	
	public void executeQuery(String sql) {
		
	}
	
	public void execute(String sql) {
		SQLiteDatabase db = getWriteDB();
		db.execSQL(sql);
		db.close();
	}
	
	public void executeUpdate(String sql) {
		SQLiteDatabase db = getWriteDB();
		db.execSQL(sql);
		db.close();
	}
	
	private DBManager(Context context) {
		this.context = context;
	}


	private SQLiteDatabase getWriteDB() {
		DatabaseHelper database = new DatabaseHelper(context);
		SQLiteDatabase db = null;
		db = database.getWritableDatabase();
		return db;
	}
}
