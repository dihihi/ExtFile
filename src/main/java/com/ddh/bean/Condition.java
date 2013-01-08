package com.ddh.bean;

import java.util.List;

import org.hibernate.type.Type;

public class Condition {
	private String sql;
	private Object[] object;
	private Type[] type;

	public Condition() {

	}

	public Condition(String sql) {
		this.sql = sql;
	}

	public Condition(String sql, Object[] object, Type[] type) {
		this.sql = sql;
		this.object = object;
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	public Condition(String sql, List objectList, List typeList){
		this.sql = sql;
		this.object = objectList.toArray();
		this.type = (Type[]) typeList.toArray(new Type[] {});
	}
	
	public Object[] getObject() {
		return object;
	}

	public void setObject(Object[] object) {
		this.object = object;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Type[] getType() {
		return type;
	}

	public void setType(Type[] type) {
		this.type = type;
	}
}
