<%@page import="java.sql.Timestamp"%>
<%@page import="com.ddh.database.dao.ArticleDAO"%>
<%@page import="com.ddh.bean.Article"%>
<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@page import="com.ddh.bean.Category"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");
String title = request.getParameter("title");
String content = request.getParameter("content");
String categoryId = request.getParameter("categoryId");

Article articleDB = null;
if (id!=null && !"".equals(id))	 {
	articleDB = ArticleDAO.selectById(new Integer(id));
} else if (categoryId != null && !categoryId.equals("")) {
	articleDB = ArticleDAO.selectByTitleAndCategoryId(title, new Integer(categoryId));
}

if (articleDB == null ||(id!=null && articleDB.getId().toString().equals(id)))  {
	Article article = new Article();
	article.setTitle(title);
	if (categoryId!=null && !categoryId.equals("")) {
		article.setCategoryId(new Integer(categoryId));
	}
	article.setContent(content);
	
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	article.setCreateTime(timestamp);
	article.setModTime(timestamp);


	if (id == null || id.trim().equals("")) {
		ArticleDAO.insert(article);
	%>
		<script type="text/javascript">
			<!--
				parent.document.getElementById("id").value="<%= article.getId() %>";
				parent.document.getElementById("categoryId").value="<%= article.getCategoryId() %>";
				parent.document.getElementById("result_tip").innerHTML="保存成功!";
			//-->
		</script>
  <%
	} else {
		articleDB.setTitle(title);
		articleDB.setContent(content);
		articleDB.setModTime(timestamp);
		
		ArticleDAO.update(articleDB);
		
	%>
		<script type="text/javascript">
			<!--
			parent.document.getElementById("result_tip").innerHTML="更新成功!";
			//-->
		</script>
	<%
	}
} else {
%>
	<script type="text/javascript">
		<!--
		parent.document.getElementById("result_tip").innerHTML="已存在!";
		//-->
	</script>
<%
}
%>



