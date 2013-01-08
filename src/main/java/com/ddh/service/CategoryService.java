package com.ddh.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.ddh.database.dao.ArticleDAO;
import com.ddh.database.dao.CategoryDAO;
import com.ddh.database.model.Article;
import com.ddh.database.model.Category;

@Service
public class CategoryService {
	@Autowired private CategoryDAO categoryDAO;
	@Autowired private ArticleDAO articleDAO;
	
	/**
	 * 子列表
	 * @param pid
	 * @return
	 */
	public List<Category> selectChildList(String pid) {
		
		return categoryDAO.selectChildList(pid);
	}
	
	/**
	 * 取同名目录
	 * @param name
	 * @param pid
	 * @return
	 */
	public Category selectSameNameCategory(String name, String pid) {
		return categoryDAO.selectByNameAndPid(name, pid);
	}
	
	/**
	 * 通过id删除
	 * @param name
	 * @param pid
	 * @return
	 */
	public int deleteById(String id) {
		Category category = categoryDAO.getObject(Category.class, id);
		//categoryDAO.getHibernateTemplate().initialize(category.getParentCategory());
		//category = category.getParentCategory();
		
		//FIXME 多处要更新是否叶子节点,代码重复
		int rows = categoryDAO.delete(id);
		
		Category parentCategory = categoryDAO.getObject(Category.class, category.getPid());
		if ((parentCategory.getArticles()==null || parentCategory.getArticles().size() < 1) && (parentCategory.getChildCategorys() == null || parentCategory.getChildCategorys().size() < 1)) {
			parentCategory.setLeaf(true);
			categoryDAO.updateObject(parentCategory);
		}
		
		return rows;
	}
	
	/**
	 * 目录文件树
	 * @param pid
	 * @return
	 */
	public String allTree(String pid) {
		Category category = categoryDAO.getObject(Category.class, pid);
		List<Category> list =  categoryDAO.selectChildList(pid);
		List<Article> articleList = articleDAO.selectByCategoryId(pid);
		
		return walkFileList(list, articleList);
	}
	
	
	/**
	 * 组装目录和文件列表
	 * @param categoryList
	 * @param articleList
	 * @return
	 */
	private String walkFileList(List<Category> categoryList, List<Article> articleList) {
		StringBuilder jsonStr = new StringBuilder("");
		jsonStr.append("[");
		if (categoryList != null) {
			for (Category category : categoryList) {
				jsonStr.append(walkCategory(category)).append(",");
			}
		}
		if (articleList != null) {
			for (Article article : articleList) {
				jsonStr.append(walkArticle(article)).append(",");
			}
		}
		if (jsonStr.toString().endsWith(",")) {
			jsonStr.deleteCharAt(jsonStr.length() - 1);
		}
		jsonStr.append("]");
		
		return jsonStr.toString();
	}
	
	/**
	 * 对象组装成tree字符串
	 * @param category
	 * @return
	 */
	private String walkCategory(Category category) {
		StringBuilder jsonStr = new StringBuilder("");
		jsonStr.append("{");
		
		jsonStr.append("id:'" + category.getId() + "'");
		jsonStr.append(",text:'" + category.getName() + "'");
		jsonStr.append(",cls:'folder'");
		if (!category.isLeaf()) {
			jsonStr.append(",leaf:false");
			//jsonStr.append(",children: " + walkCategoryList(new ArrayList<Category>(category.getChildCategorys())));
		} else {
			jsonStr.append(",leaf:true");
			//jsonStr.append(",icon:'../scripts/ext4/resources/themes/images/default/tree/leaf.gif'");
			jsonStr.append(",iconCls:'your-iconCls'");
		}
		
		jsonStr.append("}");
		
		return jsonStr.toString();
	}
	
	/**
	 * 对象组装成tree字符串
	 * @param article
	 * @return
	 */
	private String walkArticle(Article article) {
		StringBuilder jsonStr = new StringBuilder("");
		jsonStr.append("{");
		
		jsonStr.append("id:'" + article.getId() + "'");
		jsonStr.append(",text:'" + article.getTitle() + "'");
		jsonStr.append(",leaf:true");
		jsonStr.append(",cls:'file'");
		
		jsonStr.append("}");
		
		return jsonStr.toString();
	}
	
	
	/**
	 * 通过id查询
	 * @param pid
	 * @return
	 */
	public Category selectById(String id) {
		
		return categoryDAO.getObject(Category.class, id);
	}
	
	/**
	 * 通过id查询
	 * @param pid
	 * @return
	 */
	public void saveOrUpdate(Category category) {
		categoryDAO.saveOrUpdate(category);
		
		category = categoryDAO.getObject(Category.class, category.getPid());
		if (category.isLeaf()) {
			category.setLeaf(false);
			categoryDAO.updateObject(category);
		}
	}
	
	/**
	 * 导入目录及目录下的文件,递归
	 * @param dir
	 * @param pid
	 * @param saveCurrentDir 是否保存当前目录,不保存的话,子目录的父id使用piｄ
	 */
	public void saveImportCategory(File dir, String pid, boolean saveCurrentDir) {
		Category category = this.selectSameNameCategory(dir.getName(), pid);
		
		if (category == null && saveCurrentDir) {
			category = new Category();
			category.setName(dir.getName());
			category.setOrderNum(0);
			category.setPid(pid);
			categoryDAO.saveOrUpdate(category);
		}
		
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				String name = file.getName();
				if (file.isDirectory()
						|| (file.isFile()
								&& (name.toLowerCase().endsWith(".ini")
										||name.toLowerCase().endsWith(".txt")
										||name.toLowerCase().endsWith(".vbs")
										||name.toLowerCase().endsWith(".properties")
										||name.toLowerCase().endsWith(".xml")
										||name.toLowerCase().endsWith(".reg")
									||name.toLowerCase().endsWith(".bat")
									||name.toLowerCase().endsWith(".java")
									)
							)
					) {
					return true;
				}
				return false;
			}
		});
		
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					saveImportCategory(file, (saveCurrentDir? category.getId() : pid), true);
				} else {
					Article article = new Article();
					article.setTitle(file.getName());
					article.setCategoryId(saveCurrentDir? category.getId() : pid);
					
					Date nowDate = new Date(System.currentTimeMillis());
					article.setCreateTime(nowDate);
					article.setModTime(nowDate);
					
					String content = "";
					try {
						content = FileCopyUtils.copyToString(new FileReader(file));
						article.setContent("<pre>"+ content +"</pre>");
						
						String newPath = file.getCanonicalPath().replace("/pages/", "/pages2/");
						File newFile = new File(newPath);
						if (!newFile.getParentFile().exists()) {
							newFile.getParentFile().mkdirs();
						}
						FileCopyUtils.copy(content, new FileWriter(newFile));
						file.deleteOnExit();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println(file.getAbsolutePath());
					}
					
					articleDAO.saveObject(article);
				}
			}
		}
	}
	
	/**
	 * 导出为文件
	 * @throws IOException 
	 */
	public void exportToFile(String pid) throws IOException {
		Category pCategory = this.selectById(pid);
		Set<Article> articles = pCategory.getArticles();
		for (Article article : articles	) {
			exportToFile(article);
		}
		Set<Category> childCategorys = pCategory.getChildCategorys();
		for (Category category : childCategorys ) {
			exportToFile(category.getId());
		}
	}
	
	//导出为文件
	//c:/
	private void exportToFile(Article article) throws IOException {
		String title = article.getTitle();
		File dir = new File(getPath(article));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (! (title.endsWith(".htm") || title.endsWith(".html") || title.endsWith(".mht") )) {
			title = title + ".htm";
		}
		File file = new File(dir,  title.replace(":", "_"));
		FileCopyUtils.copy(article.getContent().getBytes("GBK"), file);
		articleDAO.deleteObject(article);//删除
		
	}
	//获取路径
	private String getPath(Article article) {
		StringBuilder sb = new StringBuilder("");
		Category category = article.getCategory();
		while (!"0".equals(category.getId())) {
			sb.insert(0, category.getName()).insert(0,"/");
			category = category.getParentCategory();
		}
		return  sb.insert(0, "c:/extfile").toString();
	}
}
