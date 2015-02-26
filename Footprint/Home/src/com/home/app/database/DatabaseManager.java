package com.home.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DatabaseManager extends DatabaseHelper {
	
	private static DatabaseManager instance;
	
	public static DatabaseManager getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseManager(context);
		}
		return instance;
	}
	
	private DatabaseManager(Context context) {
        super(context);
    }

	public void insert() {
		ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
		cv.put("password","iLovePopMusic"); //添加密码
		db.insert("user",null,cv);//执行插入操作
		
		String sql = "insert into user(username,password) values ('Jack Johnson','iLovePopMuisc')";//插入操作的SQL语句
		db.execSQL(sql);//执行SQL语句
	}
	
	public void delete() {
		String whereClause = "username=?";//删除的条件
		String[] whereArgs = {"Jack Johnson"};//删除的条件参数
		db.delete("user",whereClause,whereArgs);//执行删除
		String sql = "delete from user where username='Jack Johnson'";//删除操作的SQL语句
		db.execSQL(sql);//执行删除操作
	}
	
	public void update() {
		ContentValues cv = new ContentValues();//实例化ContentValues
		cv.put("password","iHatePopMusic");//添加要更改的字段及内容
		String whereClause = "username=?";//修改条件
		String[] whereArgs = {"Jack Johnson"};//修改条件的参数
		db.update("user",cv,whereClause,whereArgs);//执行修改
		String sql = "update [user] set password = 'iHatePopMusic' where username='Jack Johnson'";//修改的SQL语句
		db.execSQL(sql);//执行修改
	}
	
	public void query() {
		Cursor c = db.query("user",null,null,null,null,null,null);//查询并获得游标
		if(c.moveToFirst()){//判断游标是否为空
		    for(int i=0;i<c.getCount();i++){
		        c.move(i);//移动到指定记录
		        //String username = c.getString(c.getColumnIndex("username"));
		        //String password = c.getString(c.getColumnIndex("password"));
		    }
		}
		Cursor c1 = db.rawQuery("select * from user where username=?", new String[]{"Jack Johnson"});
		if(c1.moveToFirst()) {
		    //String password = c1.getString(c.getColumnIndex("password"));
		}
	}
	
}
