package com.ddh.database.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.ddh.bean.Condition;
import com.ddh.bean.Page;
import com.ddh.database.model.Article;
import com.ddh.util.ContextUtil;
import com.ddh.util.PageUtil;
import com.ddh.util.ParamUtil;

@Repository
public class ArticleDAO extends CommonDAO  {
	private static final Log log = LogFactory.getLog(ArticleDAO.class);
	
	@Resource
	protected void init(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 通过name和pid查询
	 * @param id
	 */
	public Article selectByTitleAndCategoryId(String title, Integer categoryId) {
		
		return (Article) super.getObjectH("from Article where lower(title)=? and categoryId=?", title.toLowerCase(), categoryId);
	}
	
	/**
	 * 通过id查询
	 * @param id
	 */
	public Article selectById(String id) {
		
		return getObject(Article.class, id);
	}
	
	/**
	 * 取列表
	 * @return
	 */
	public List<Article> selectByCategoryId(String categoryId) {
		String hql = "from Article t where t.categoryId=? order by lower(t.title)";
		
		return super.getBySql(hql, 9999, categoryId);
	} 
	
	/**
	 * 取分页
	 * @return
	 */
	public Page<Article> selectList(int pageNumber, int maxRows, String url, Map<String, Object> map) {
		StringBuilder sql = new StringBuilder("from Article t");
		
		List<Object> objectList = new ArrayList<Object>();
		List<Type> typeList = new ArrayList<Type>();
		
		String categoryId = ParamUtil.getString(map, "categoryId","");
		if (!"".equals(categoryId)) {
			objectList.add(categoryId);
			typeList.add(Hibernate.STRING);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = ParamUtil.getString(map, "startDate", "");
		if (!"".equals(startDate)) {
			sql.append(" and t.createTime >= ? ");
			try {
				objectList.add(sdf.parse(startDate));
			} catch (ParseException e) {
				log.error(e);
			}
			typeList.add(Hibernate.DATE);
		}
		
		String endDate = ParamUtil.getString(map, "endDate", "");
		if (!"".equals(endDate)) {
			sql.append(" and t.createTime < ? ");
			try {
				objectList.add(new Date(sdf.parse(endDate).getTime()+24*3600*1000));
			} catch (ParseException e) {
				log.error(e);
			}
			typeList.add(Hibernate.DATE);
		}
		
		String orderBySql = " order by t.createTime desc";
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
	
}
