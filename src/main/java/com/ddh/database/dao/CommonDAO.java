package com.ddh.database.dao;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ddh.bean.Condition;
import com.ddh.bean.Page;

public class CommonDAO extends HibernateDaoSupport {

	@Autowired
	private void init(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * 通过 HQL 取LIST
	 * @param hql
	 *            语句
	 * @param rows
	 *            一共取几条
	 * @param value
	 *            一系列的参数
	 * @return
	 */
	public List getBySql(String hql, Integer rows, Object... value) {
		if (rows != null) {
			getHibernateTemplate().setMaxResults(rows);
		}
		if (hql.indexOf("?") != -1) {
			return getHibernateTemplate().find(hql, value);
		} else {
			return getHibernateTemplate().find(hql);
		}
	}

	/**
	 * 取实体
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	public <T> T getObject(Class<T> c, Serializable id) {
		try {
			return (T) getHibernateTemplate().get(c, id);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通过 HQL 取实体
	 * 
	 * @param hql
	 * @param value
	 * @return
	 */
	public Object getObjectH(String hql, Object... value) {
		List l = getBySql(hql, 1, value);
		Iterator iter = l.iterator();
		if (iter.hasNext()) {
			return iter.next();
		} else {
			return null;
		}
	}


	/**
	 * 取实体
	 * 
	 * @param modelName
	 *            实体对象名称
	 * @param property
	 *            变量名称
	 * @param value
	 *            变量的值
	 * @return
	 */
	public Object getObjectByProperty(String modelName, String property,
			String value) {
		List l = getBySql("from " + modelName + " a where a." + property
				+ " = ?", 1, value);
		Iterator iter = l.iterator();
		if (iter.hasNext()) {
			return iter.next();
		} else {
			return null;
		}
	}

	/**
	 * 取实体 （用模糊匹配）
	 * 
	 * @param modelName
	 *            实体对象名称
	 * @param property
	 *            变量名称
	 * @param value
	 *            变量的值
	 * @return
	 */
	public Object getObjectByPropertyLike(String modelName, String property,
			String value) {
		List l = getBySql("from " + modelName + " a where a." + property
				+ " like ?", 1, "%" + value + "%");
		Iterator iter = l.iterator();
		if (iter.hasNext()) {
			return iter.next();
		} else {
			return null;
		}
	}

	/**
	 * 公用保存记录
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	public boolean saveObject(Object o) {
		getHibernateTemplate().save(o);
		return true;
	}

	/**
	 * 保存或更新记录
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	public boolean saveOrUpdate(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
		return true;
	}
	
	/**
	 * 公用更新记录
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	public boolean updateObject(Object o) {
		getHibernateTemplate().update(o);
		return true;
	}

	/**
	 * 删除实体
	 * 
	 * @param o
	 */
	public void deleteObject(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 批量删除一系列实体
	 * 
	 * @param classes
	 * @param ids
	 * @param values
	 * @return
	 */
	public boolean deleteObjects(List<String> classes, List<String> ids,
			List<String> values) {
		if (classes == null || classes.size() < 1) {
			return false;
		}
		for (int i = 0; i < classes.size(); i++) {
			String className = classes.get(i);
			String id = ids.get(i);
			String value = values.get(i);
			getHibernateTemplate()
					.bulkUpdate(
							"delete from " + className + " where " + id
									+ " = ?", value);
		}
		return true;
	}

	/**
	 * 保存删除多个对象
	 * 
	 * @param updateList
	 * @param delList
	 * @return
	 */
	public boolean updateObjects(List updateList, List delList) {
		if (delList != null && delList.size() > 0) {
			this.getHibernateTemplate().deleteAll(delList);
		}
		if (updateList != null && updateList.size() > 0) {
			this.getHibernateTemplate().saveOrUpdateAll(updateList);
		}
		return true;
	}


	/**
	 * 通用更新语句
	 * @param hql
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public int updateCommen(String hql, Object... value) {
		return getHibernateTemplate().bulkUpdate(hql, value);
	}

	public int getIntCount(String hql, String... value) {
		List l;
		if (hql.indexOf("?") != -1) {
			l = getHibernateTemplate().find(hql, value);
		} else {
			l = getHibernateTemplate().find(hql);
		}
		return new Integer(String.valueOf(l.get(0)));
	}

	
	public int getIntCount(Condition c) {
		String hql = c.getSql();
		getHibernateTemplate().setMaxResults(1000000000);
		List l;
		if (hql.indexOf("?") != -1){
			l = getHibernateTemplate().find(hql, c.getObject());
		}else{
			l = getHibernateTemplate().find(hql);
		}
		return new Integer(String.valueOf(l.get(0)));
	} 
 
	
	public Page findByPage(final Page page) {
		final Condition condition = page.getCondition();
		final String hql = condition.getSql();
		getHibernateTemplate().setCacheQueries(false);
		List l = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
				throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					if (condition.getSql().indexOf("?") != -1)
						query.setParameters(condition.getObject(), condition.getType());
					query.setFirstResult(page.getBeginIndex());
					query.setMaxResults(page.getPageSize());
					List list = query.list();
					return list;
				}
			}); 
		page.setResult(l);
		return page;
	}
}
