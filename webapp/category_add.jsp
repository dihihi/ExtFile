<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@page import="com.ddh.bean.Category"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String pid = request.getParameter("pid");
if (pid == null || pid.trim().equals("")) {
%>
	<script>
		alert("未选择父类");
	</script>
<%
}
%>
<iframe id="hiddenIFrame" name="hiddenIFrame" style="height: 0;width: 0;display: hidden" height="0" width="0"></iframe>
<form id="articleForm" action="pages/title/category_save.jsp" method="post" target="hiddenIFrame">
    <table width="700" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:80px;">
	  <tr>
	    <td width="100" height="32" align="right" valign="middle">类别名：</td>
	    <td><input type="text" name="name" value="" size="50" class="text">
	    	<input type="hidden" name="id" id="id" value=""/>
	    	<input type="hidden" name="pid" value="<%= pid %>" />
	    </td>
	  </tr>
	  <tr>
	   <td width="100" height="32" align="right" valign="middle">排序：</td>
	    <td><input class="text" name="orderNum" id="order_num" value="0"/></td>
	  </tr>
	  <tr>
	  	<td colspan="2" height="32" align="center" valign="middle">
	  		<input type="submit" value="保存"/>&nbsp;<input type="reset" value="重置" />
	  	</td>
	  </tr>
    </table>
    
</form>

