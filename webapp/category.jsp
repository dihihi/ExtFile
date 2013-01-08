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
	
	$(function() {
		

	});
</script>
</head>
<body>
	<%@include file="./heard.jsp" %>
	<div class="content">
	   <%@include file="./tree.jsp" %>
		<div class="main" style="width: 634px;">
			<div class="midmid" style="width: 600px;">
				<!--主要内容区域-->
				<div class="iframe" id="nodeinfo">
				</div>
				<!--内容区域结束-->
			</div>
		</div>
	</div>
	<%@ include file="./footer.jsp" %>
</body>
</html>

