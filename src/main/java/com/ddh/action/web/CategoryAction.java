package com.ddh.action.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.ddh.database.model.Category;
import com.ddh.service.CategoryService;

/**
 * category
 */
public class CategoryAction extends CommonAction {
	
	@Resource private CategoryService categoryService;
	
	private String name;
	private String pid;
	private String node;
	
	
	/**
	 * tree
	 */
	public void tree() throws IOException {
		String jsonStr = categoryService.allTree(node==null? "" : node);
		
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.write(jsonStr);
	}

	/**
	 * 保存
	 * @throws IOException
	 */
	public void save() throws IOException {
		Category category = null;
		
		category = categoryService.selectSameNameCategory(name, pid);
		
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		if (category != null && !category.getId().equals(id)) {
			out.write("{success: false, 'exist': true}");
			return;
		}
		
		if (StringUtils.isNotEmpty(id)) {
			category = categoryService.selectById(id);
		}
		if (category == null) {
			category = new Category();
			category.setPid(pid);
			category.setOrderNum(0);
			category.setLeaf(true);
		}
		
		category.setName(name);
		
		categoryService.saveOrUpdate(category);
		
		out.write("{success: true, id: '" + category.getId() + "', name: '"+ category.getName() +"'}");
		
	}
	
	/**
	 * del
	 * @throws IOException
	 */
	public void del() throws IOException {
		Category category = null;
		
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		
		if (StringUtils.isNotEmpty(id)) {
			category = categoryService.selectById(id);
		}
		if (category == null) {
			category = new Category();
		}
		
		category.setName(name);
		category.setPid(pid);
		category.setOrderNum(0);
		
		int rows = categoryService.deleteById(id);
		
		if (rows < 1) {
			out.write("{success: false}");
		} else {
			out.write("{success: true}");
		}
		
	}
	
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
	
	
}
