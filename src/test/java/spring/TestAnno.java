package spring;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ddh.action.web.ArticleAction;
import com.ddh.service.ArticleService;

public class TestAnno {
	
	@Test
	public void testAno() {
		ApplicationContext acx = new ClassPathXmlApplicationContext("applicationContext.xml", "applicationContext-dataAccess.xml");
		Object obj = acx.getBeanNamesForType(ArticleService.class);//注解可以获取到
		Object[] objs = (Object[])obj; 
		System.out.println(objs[0]);
		
		obj = acx.getBean(objs[0].toString());
		obj = acx.getBean(ArticleService.class);
		
		obj = acx.getBean(ArticleAction.class);
	}
}
