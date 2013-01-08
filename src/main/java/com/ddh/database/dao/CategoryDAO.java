package com.ddh.database.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.ddh.bean.Condition;
import com.ddh.bean.Page;
import com.ddh.database.model.Category;
import com.ddh.util.ContextUtil;
import com.ddh.util.PageUtil;
import com.ddh.util.ParamUtil;

@Repository
public class CategoryDAO extends CommonDAO {
	private static final Log log = LogFactory.getLog(CategoryDAO.class);
	
	/**
	 * 通过name和pid查询
	 * @param id
	 */
	public  Category selectByNameAndPid(String name, String pid) {
		Object obj = super.getObjectH("from Category where lower(name)=? and pid=?", name.toLowerCase(), pid);
		
		return (Category)obj;
	}
	
	
	/**
	 * 取所有
	 * @return
	 */
	public  Page<Category> selectAll(int pageNumber, int maxRows, String url, Map<String, Object> map) {
		
		StringBuilder sql = new StringBuilder("from Category t ");
		
		List<Object> objectList = new ArrayList<Object>();
		List<Type> typeList = new ArrayList<Type>();
		
		String userId = ParamUtil.getString(map, "userId","");//用户id
		if (!"".equals(userId )) {
			
			objectList.add(userId);
			typeList.add(Hibernate.STRING);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = ParamUtil.getString(map, "startDate", "");
		if (!"".equals(startDate)) {
			sql.append(" and b.dateAdd >= ? ");
			try {
				objectList.add(sdf.parse(startDate));
			} catch (ParseException e) {
				log.error(e);
			}
			typeList.add(Hibernate.DATE);
		}
		
		String endDate = ParamUtil.getString(map, "endDate", "");
		if (!"".equals(endDate)) {
			sql.append(" and b.dateAdd < ? ");
			try {
				objectList.add(new Date(sdf.parse(endDate).getTime()+24*3600*1000));
			} catch (ParseException e) {
				log.error(e);
			}
			typeList.add(Hibernate.DATE);
		}
		
		String orderBySql = " order by orderNum ";
		String method = ContextUtil.getQueryString(map);//取

		Condition condition = new Condition("select count(t) " + sql.toString(), objectList, typeList);
		int totalRow = getIntCount(condition);
		condition.setSql(sql.append(orderBySql).toString());
		Page page = new Page(maxRows, url, method);
		
		page.setCurPage(pageNumber);
		page.setCondition(condition);
		page = PageUtil.createPage(page, totalRow);
		page=findByPage(page);
		
		return page;
	} 
	
	
	/**
	 * 子节点
	 * @return
	 */
	public List<Category> selectChildList(String pid) {
		
		return super.getBySql("from Category t where t.pid=? order by lower(t.name)", 9999, pid);
		
	} 
	
	/**
	 * delete
	 * 没有文章,没有子节点的才能删除
	 * @param category
	 */
	public int delete(String id) {
		return super.updateCommen("delete from Category c where c.id=? and " +
					"not exists(select 1 from Category t2 where t2.pid=?) and " +
					"not exists(select 1 from Article where categoryId=?)", id, id, id);
	}


	/**
	 * 保存或更新
	 * @param category
	 */
	public void saveOrUpdate(Category category) {
		super.saveOrUpdate(category);
	}
}
