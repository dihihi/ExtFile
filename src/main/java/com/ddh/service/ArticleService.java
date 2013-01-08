package com.ddh.service;

import java.util.Map;

import com.ddh.bean.Page;
import com.ddh.database.model.Article;


public interface ArticleService {
    
    /**
	 * 取分页列表
	 * @param pageNumber
	 * @param maxRows
	 * @param url
	 * @param map
	 * @return
	 */
    Page getPageList(int pageNumber, int maxRows, String url,
			Map<String, Object> map);

    /**
     * 通过id查询
     * @param id
     * @return
     */
	Article selectById(String id);

	/**
	 * 通过id删除
	 */
	Article deleteById(String id);
	/**
	 * 保存or更新 
	 * @param articleNew
	 */
	void saveOrUpdate(Article articleNew);
    
    
}
