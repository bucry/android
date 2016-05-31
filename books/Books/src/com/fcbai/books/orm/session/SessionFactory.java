package com.fcbai.books.orm.session;


public interface SessionFactory {
	public Session openSession();
	public Session currentSession();
	void buildSession(Session session);
	public void register(Class<?> classz);
	public void setAutoCreateTable(boolean isAutoCreateTable);
	public void startOrmConfigure();
	
}
