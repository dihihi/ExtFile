package com.ddh.database.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="T_ARTICLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {
	private String id;
	private String title;
	private String content;
	private String categoryId;//类别ＩＤ
	private Date createTime;//创建时间
	private Date modTime;
	private boolean isDelete;
	
	private Category category;//目录
	
	@Id
	@GeneratedValue(generator="uuidGenerator")
 	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Basic
	@Column(name="CATEGORY_ID")
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Basic
	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Basic
	@Column(name="MOD_TIME")
	public Date getModTime() {
		return modTime;
	}
	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}
	
	@Basic
	@Column(name="is_delete")
	public boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	@ManyToOne(cascade = { CascadeType.REFRESH},fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name = "category_id",nullable = false,updatable=false,insertable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
