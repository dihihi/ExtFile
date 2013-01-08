package com.ddh.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddh.bean.Page;
import com.ddh.database.dao.ArticleDAO;
import com.ddh.database.dao.CategoryDAO;
import com.ddh.database.model.Article;
import com.ddh.database.model.Category;
import com.ddh.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired private ArticleDAO dao;
	@Autowired private CategoryDAO categoryDAO;
	
	@Override
	public Page getPageList(int pageNumber, int maxRows, String url,
			Map<String, Object> map) {
		return dao.selectList(pageNumber, maxRows, url, map);
	}

	/* (non-Javadoc)
	 * @see com.ddh.service.impl.f#selectById(java.lang.String)
	 */
	@Override
	public Article selectById(String id) {
		
		return dao.selectById(id);
	}
	
	
	@Override
	public Article deleteById(String id) {
		Article article = dao.selectById(id);
		dao.deleteObject(article);
		return article;
	}

	//articleNew
	@Override
	public void saveOrUpdate(Article articleNew) {
		dao.saveOrUpdate(articleNew);
		
		Category category = categoryDAO.getObject(Category.class, articleNew.getCategoryId());
		if (category.isLeaf()) {
			category.setLeaf(false);
			categoryDAO.updateObject(category);
		}
	}
	
	
}
