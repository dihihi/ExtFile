<%@page import="com.ddh.util.Page"%>
<%@page import="com.ddh.bean.Article"%>
<%@page import="com.ddh.database.dao.ArticleDAO"%>
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
	
	String id = request.getParameter("id");
	String categoryId = request.getParameter("categoryId");
	
	if (id!=null && !"".equals(id)) {
		Article article = ArticleDAO.selectById(new Integer(id));
		
		pageContext.setAttribute("bean", article);
	} else {
		Article article = new Article();
		
		if (categoryId==null || categoryId.equals("")) {
			article.setCategoryId(0);
		} else {
			article.setCategoryId(new Integer(categoryId));
		}
		pageContext.setAttribute("bean", article);
	}
	
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/css.css" />
<title>内容编辑</title>
<script type="text/javascript"
	src="${basePath}js/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var contextName = "${basePath}";

$(function() {
	CKEDITOR.replace('content');
	
	$("#return-back").click(function() {
		location.href="admin/gonggao.do";		
	});
	
	$("#articleForm").submit(function() {
		if ($.trim($("#title").val())=="") {
			alert("请输入标题！");
			$("#title").focus();
			return false;
		}
		this.submit();
		return false;
	});
	
	$('#selectAll').click(function() {
		if (this.checked == true) {
			all_select($("input[class=ids]").get());
		} else {
			deSelectAll($("input[class=ids]").get());
		}
	});
	
	var fixSelected = function() {
		var allSelected = true;
		$("input[class=ids]").each(function(i, item) {
			if (!this.checked) {
				allSelected = false;
			}
		});
		if (allSelected) {
			$('#selectAll').attr("checked", true);
		} else {
			$('#selectAll').attr("checked", false);
		}
	}
	
	$("input[class=ids]").click(function() {
		fixSelected();
	});
	
	fixSelected();
});
</script>

</head>
<body>
<%@include file="./heard.jsp" %>
<div class="content">
	<div class="main">
    	<div class="wel"><div class="weltop"><div class="topmid">>></div></div></div>
    	<div class="welmid"><div class="mid"><div class="midmid">
        	<!--主要内容区域-->
            <div class="iframe">
				<div id="result_tip"></div>
               <iframe id="hiddenIFrame" name="hiddenIFrame" style="height: 0;width: 0;display: hidden" height="0" width="0"></iframe>
				<form id="articleForm" action="pages/title/article_save.jsp" method="post" target="hiddenIFrame">
				    <table width="700" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:80px;">
					  <tr>
					    <td width="100" height="32" align="right" valign="middle">标题：</td>
					    <td><input type="text" id="title" name="title" value="${bean.title }" size="50" class="text">
					    	<input type="hidden" name="id" id="id" value="${bean.id }"/>
					    	<input type="hidden" name="categoryId" id="categoryId" value="${bean.categoryId }" />
					    </td>
					  </tr>
					  <tr>
					   <td width="100" height="32" align="right" valign="middle">内容：</td>
					    <td><textarea class="text" name="content" id="content" ">${bean.content }</textarea></td>
					  </tr>
					  <tr>
					  	<td colspan="2" height="32" align="center" valign="middle">
					  		<input type="submit" value="保存"/>&nbsp;<input type="reset" value="重置" />
					  		<a href="javascript:document.location.href='pages/title/article_list.jsp?categoryId=<%= categoryId%>'">返回</a>
					  	</td>
					  </tr>
				    </table>
				    
				</form>

            </div>
        	<!--内容区域结束-->
        </div></div></div>
    	<div class="welbtm"><div class="btm"><div class="btmmid"><img src="images/gif.gif" width="834" height="6"/></div></div></div>
    </div>
</div>
<%@ include file="./footer.jsp" %>

</body>
</html>