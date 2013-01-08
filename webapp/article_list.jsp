<%@page import="com.ddh.database.dao.ArticleDAO"%>
<%@page import="com.ddh.bean.Article"%>
<%@page import="com.ddh.util.Page"%>
<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@page import="com.ddh.bean.Category"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	if (path.equals("/")) {
		path = "";
	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
List<Category> list = CategoryDAO.selectAll();

String categoryIdStr = request.getParameter("categoryId");
Long categoryId = 0L;
if (categoryIdStr != null && !categoryIdStr.equals("")) {
	categoryId = Long.parseLong(categoryIdStr);
}
String pageNOStr = request.getParameter("pageNO");
int pageNO = 1;
try {
	pageNO = new Integer(pageNOStr);
} catch(Exception e) {}
int pageSize = 10;

Page<Article> p = ArticleDAO.selectList(pageNO, pageSize, categoryId);

pageContext.setAttribute("p", p);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript"
	src="${basePath}js/jquery/jquery-1.5.1.js"></script>
<link href="js/dtree2/dtree.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/dtree2/dtree.js"></script>
<script type="text/javascript">
	var contextName = "${basePath}";
	
	function clickNode(id) {
		if (id==undefined || id=="") {
			return;
		}
		window.location.href="pages/title/article_list.jsp?categoryId=" + id;
	}
	
	function del(id) {
		if (!confirm("confirm delete!")) {
			return false;
		}
		var url = "pages/title/article_del.jsp?id=" + id;

		$.get(url, function(data) {
			if (data && $.trim(data) == "SUCCESS") {
				window.location.reload();
			} else {
				alert(data);
			}
		});
	}
	
	$(function() {
		d = new dTree('d', 'treeHtml');
		d.add(0,-1,'ROOT','javascript:clickNode(\'0\')');
		<%
			for (Category c : list) {
		%>
		 d.add('<%= c.getId() %>','<%= c.getPid()%>','<%= c.getName()%>','javascript:clickNode(\'<%=c.getId()%>\')');
		<%
			}
		%>
		d.renderTo();
		d.openTo("<%= categoryId%>", true);
		
		
		$("#add").click(function() {
			var id = d.aNodes[d.selectedNode].id;
			if (id==undefined || id=="") {
				alert("请选中一个类别!");
				return false;
			}
			document.location.href="pages/title/article_edit.jsp?categoryId="+id;
		});
		
	});
	
	var edit = function(id, categoryId) {
		document.location.href="pages/title/article_edit.jsp?categoryId="+categoryId + "&id="+id;
	}
</script>
</head>
<body>

	<%@include file="./heard.jsp" %>
	<div class="content">
	   	<div style="float:left;">
	   		<p><a href="javascript: d.openAll();">展开</a> | <a href="javascript: d.closeAll();">收起</a></p>
			<div id="treeHtml"></div>
	   	</div>
		<div class="main" style="width: 634px;">
			<div class="midmid" style="width: 600px;">
				<!--主要内容区域-->
				<div style="float: right;"><a href="javascript:;" id="add">添加</a> </div>
				<div style="clear: both;"></div>
				<table width="100%">
					<tr>
						<th>标题</th>
						<th>创建时间</th>
						<th>修改时间</th>
						<th>操作</th>
					</tr>
					<%
					if (p.getResult() != null) {
						for (Article article : p.getResult()) {
							%>
							<tr>
								<td><a href="pages/title/article_view.jsp?id=<%=article.getId() %>"><strong><%=article.getTitle() %></strong></a> </td>
								<td><%=article.getCreateTime() %></td>
								<td><%=article.getModTime() %></td>
								<td><a href="javascript:;" onclick="edit('<%=article.getId() %>', '<%=article.getCategoryId() %>')">编辑</a> <a href="javascript:;" onclick="del('<%=article.getId() %>')">删除</a> </td>
							</tr>
							<%
						}
					}
					%>
				</table>
				<!--内容区域结束-->
			</div>
		</div>
	</div>
	<%@ include file="./footer.jsp" %>
</body>
</html>

