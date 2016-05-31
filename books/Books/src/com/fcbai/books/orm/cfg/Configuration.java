package com.fcbai.books.orm.cfg;

import android.content.Context;

import com.fcbai.books.databases.DBManager;
import com.fcbai.books.orm.session.Session;
import com.fcbai.books.orm.session.SessionFactory;
import com.fcbai.books.orm.session.SessionFactoryImpl;
import com.fcbai.books.orm.session.SessionImpl;



public class Configuration {

	private Session session;
	private SessionFactory factory;
	public Configuration configure(Context context) {
		session = SessionImpl.getInstance(DBManager.getInstance(context));
		factory = new SessionFactoryImpl();
		factory.buildSession(session);
		return this;
	}
	
	public SessionFactory buildSessionFactory() {
		return factory;
	}
}
