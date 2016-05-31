package com.fcbai.books.orm.session;


public interface Session {
	public void close();
	public void save(Object obj);
	public void delete(Object obj);
	public void update(Object obj);
	public void initTable(Class<?> clazz);
	
	public Session openSession();
	public Session currentSession();
}
