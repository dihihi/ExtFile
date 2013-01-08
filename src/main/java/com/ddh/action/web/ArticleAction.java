package com.ddh.action.web;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ddh.database.model.Article;
import com.ddh.service.ArticleService;

/**
 * Article
 */
public class ArticleAction extends CommonAction {
	@Autowired private ArticleService articleService;
	private Article article;
	
	/*
	 * view
	 */
	public String view() {
		article = articleService.selectById(id);
		
		return "view";
	}

	/*
	 * edit
	 */
	public String edit() {
		article = articleService.selectById(id);
		if (article == null) {
			article = new Article();
			article.setTitle("");
			article.setContent("");
		}
		return "edit";
	}
	
	/*
	 * save
	 */
	public void save() throws IOException {
		
		Article articleNew = articleService.selectById(id);
		
		Date nowDate = new Date(System.currentTimeMillis());
		if (articleNew == null) {
			articleNew = article;
			articleNew.setCreateTime(nowDate);
		} else {//update object
			articleNew.setTitle(article.getTitle());
			articleNew.setContent(article.getContent());
		}
		articleNew.setModTime(nowDate);
		articleService.saveOrUpdate(articleNew);
		
		jsonReturn("[{'success':true, 'categoryId': '"+ articleNew.getCategoryId() +"', 'id': '"+ articleNew.getId() +"','title':'"+ articleNew.getTitle() +"'}]");
	}
	
	/**
	 * 删除
	 * @throws IOException
	 */
	public void dell() throws IOException {
		
		Article article = articleService.deleteById(id);
		
		jsonReturn("[{'success':true, 'categoryId': '"+ article.getCategoryId() +"', 'id': '"+ article.getId() +"','title':'"+ article.getTitle() +"'}]");
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
}
