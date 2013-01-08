package data;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ddh.service.ArticleService;
import com.ddh.service.CategoryService;

public class ExportFile {
	
	private CategoryService categoryService;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExportFile importFile = new ExportFile();
		importFile.init();
		try {
			importFile.exportToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml", "applicationContext-dataAccess.xml");
		categoryService = acx.getBean(CategoryService.class);
	}
	/*
	 * 导出数据库对象为文件
	 */
	public void exportToFile() throws IOException {
		/*
		 * 找出目录下的文章,目录下的目录
		 */
		 categoryService.exportToFile("0");
		
	}
	
}
