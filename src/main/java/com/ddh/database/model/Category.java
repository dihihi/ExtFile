package com.ddh.database.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="T_CATEGORY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {
	private String id;
	private String name;
	
	private Integer orderNum;
	private boolean leaf;//是否叶子节点
	
	private String pid;
	
	private Category parentCategory;
	
	private Set<Article> articles = new HashSet<Article>(0);
	private Set<Category> childCategorys = new HashSet<Category>(0);
	
	@Id
    @GeneratedValue(generator="uuidGenerator")
 	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	@Column(name="order_num")
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    //@OrderBy("title asc")会区分大小写
	public Set<Article> getArticles() {
		return articles;
	}
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	@ManyToOne(cascade = { CascadeType.REFRESH},fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name = "pid",nullable = false,updatable=false,insertable = false)
	public Category getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
	
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
    @JoinColumn(name="pid")
    //@OrderBy("name asc")
	public Set<Category> getChildCategorys() {
		return childCategorys;
	}
	public void setChildCategorys(Set<Category> childCategorys) {
		this.childCategorys = childCategorys;
	}
	
}
