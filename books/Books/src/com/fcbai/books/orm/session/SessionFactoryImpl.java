package com.fcbai.books.orm.session;


import java.util.ArrayList;
import java.util.List;

public class SessionFactoryImpl implements SessionFactory {

	private Session session;
	private List<Class<?>> classzArray = new ArrayList<Class<?>>();
	private boolean isAutoCreateTable = false;
	@Override
	public void buildSession(Session session) {
		this.session = session;
	}
	
	@Override
	public Session openSession() {
		return session;
	}

	@Override
	public Session currentSession() {
		return session;
	}

	@Override
	public void register(Class<?> classz) {
		classzArray.add(classz);
	}

	@Override
	public void setAutoCreateTable(boolean isAutoCreateTable) {
		this.isAutoCreateTable = isAutoCreateTable;
	}

	@Override
	public void startOrmConfigure() {
		if (isAutoCreateTable) {
			for (Class<?> clazz : classzArray) {
				session.initTable(clazz);
			}
		}
	}

}
