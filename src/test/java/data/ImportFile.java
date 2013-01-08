package data;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ddh.service.CategoryService;

public class ImportFile {
	
	private CategoryService categoryService;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImportFile importFile = new ImportFile();
		importFile.init();
		importFile.importFileToDB();
	}

	public void init() {
		ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml", "applicationContext-dataAccess.xml");
		categoryService = acx.getBean(CategoryService.class);
	}
	/*
	 * 导入到数据库
	 */
	public void importFileToDB() {
		categoryService.saveImportCategory(new File("/home/ddh/workspace/MyTest/webapp/pages/"), "0", false);
		
		
	}
	
}
