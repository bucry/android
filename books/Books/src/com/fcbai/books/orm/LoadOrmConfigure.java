package com.fcbai.books.orm;

import android.content.Context;

import com.fcbai.books.entity.Book;
import com.fcbai.books.orm.cfg.Configuration;
import com.fcbai.books.orm.session.SessionFactory;

public class LoadOrmConfigure {
	
	SessionFactory sessionFactory;
	public void load(Context context) {
		sessionFactory = new Configuration().configure(context).buildSessionFactory();
		sessionFactory.register(Book.class);
		sessionFactory.setAutoCreateTable(true);
		sessionFactory.startOrmConfigure();
	}
}
