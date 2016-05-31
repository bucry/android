package com.fcbai.books.orm.session;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fcbai.books.databases.DBManager;
import com.fcbai.books.entity.Book;
import com.fcbai.books.orm.annotation.ColumnFiled;
import com.fcbai.books.orm.annotation.DBTable;

public class SessionImpl implements Session {

	private SessionImpl(DBManager manager) {
		this.manager = manager;
	}

	private DBManager manager;
	private static Session instance;

	public static Session getInstance(DBManager manager) {
		if (instance == null) {
			instance = new SessionImpl(manager);
		}
		return instance;
	}

	@Override
	public void close() {

	}

	@Override
	public void save(Object obj) {

	}

	@Override
	public void delete(Object obj) {
		int id = getPrimaryKeyValue(obj);
		String tableName = getTableName(obj);
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(tableName).append(" where id = ").append(id);
		manager.executeQuery(sb.toString());
	}

	@Override
	public void update(Object obj) {

	}

	
	private int getPrimaryKeyValue(Object obj) {
		Class<? extends Object> ownerClass = obj.getClass();
		Method method = null;
		try {
			method = ownerClass.getMethod("getId");
			return (int) method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private Object invokeMethod(Object owner, String fieldName, Object[] args) {
		Class<? extends Object> ownerClass = owner.getClass();

		String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);

		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return "";
		}

		try {
			return method.invoke(owner);
		} catch (Exception e) {
			return "";
		}
	}

	private String getTableName(Object obj) {
		Class<?> clazz = obj.getClass();
		if (clazz.isAnnotationPresent(DBTable.class)) {
			DBTable annotation = (DBTable) clazz.getAnnotation(DBTable.class);
			return annotation.name();
		}
		return null;
	}

	private String getTableName(Class<?> clazz) {
		if (clazz.isAnnotationPresent(DBTable.class)) {
			DBTable annotation = (DBTable) clazz.getAnnotation(DBTable.class);
			return annotation.name();
		}
		return null;
	}

	private String createInitTableSql(Map<String, Class<?>> inputMap, Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(getTableName(clazz));
		sb.append("(id integer primary key autoincrement, ");
		int columnsNumber = 0;
		for (String columnName : inputMap.keySet()) {
			columnsNumber += 1;
			if (!"id".equals(columnName)) {
				sb.append(columnName);
				System.out.println(inputMap.get(columnName));
				if (inputMap.get(columnName).equals(Integer.class)) {
					sb.append(" int");
				}

				if (inputMap.get(columnName).equals(String.class)) {
					sb.append(" varchar(256)");
				}

				if (inputMap.get(columnName).equals(Long.class)) {
					sb.append(" int");
				}

				if (inputMap.get(columnName).equals(Boolean.class)) {
					sb.append(" boolean");
				}

				if (columnsNumber < inputMap.keySet().size()) {
					sb.append(" ,");
				}

			}
		}
		sb.append(" )");
		return sb.toString();
	}

	private Map<String, Class<?>> getInitTableColumnInfo(Class<?> clazz) {
		Map<String, Class<?>> columns = new HashMap<String, Class<?>>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			ColumnFiled meta = f.getAnnotation(ColumnFiled.class);
			if (meta != null) {
				columns.put(meta.name(), f.getType());
			}
		}
		return columns;
	}

	private List<Object> getColumn() {

		List<Object> columns = new ArrayList<Object>();
		Field[] fields = Book.class.getDeclaredFields();
		for (Field f : fields) {
			ColumnFiled meta = f.getAnnotation(ColumnFiled.class);
			if (meta != null) {
				columns.add(meta.name());
				System.out.println(f.getName() + "--" + meta.name());
			}
		}

		Method[] methods = Book.class.getMethods();
		for (Method m : methods) {
			ColumnFiled meta = m.getAnnotation(ColumnFiled.class);
			if (meta != null) {
				System.out.println(m.getName());
			}
		}
		return columns;
	}

	@Override
	public void initTable(Class<?> clazz) {
		String sql = createInitTableSql(getInitTableColumnInfo(clazz), clazz);
		System.out.println(sql);
	}

	@Override
	public Session openSession() {
		instance = new SessionImpl(manager);
		return instance;
	}

	@Override
	public Session currentSession() {
		return instance;
	}
}
