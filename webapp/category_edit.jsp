<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@page import="com.ddh.bean.Category"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");
Category category = CategoryDAO.selectById(new Integer(id));
%>

<iframe id="hiddenIFrame" name="hiddenIFrame" style="height: 0;width: 0;display: hidden" height="0" width="0"></iframe>
<form id="articleForm" action="pages/title/category_save.jsp" method="post" target="hiddenIFrame">
    <table width="700" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:80px;">
	  <tr>
	    <td width="100" height="32" align="right" valign="middle">类别名：</td>
	    <td><input type="text" name="name" value="<%= category.getName()==null? "":category.getName() %>" size="50" class="text">
	    	<input type="hidden" name="id" value="<%= category.getId()==null? "":category.getId().toString() %>"/>
	    	<input type="hidden" name="pid" value="<%= category.getPid()==null? "":category.getPid().toString() %>" />
	    </td>
	  </tr>
	  <tr>
	   <td width="100" height="32" align="right" valign="middle">排序：</td>
	    <td><input class="text" name="orderNum" id="order_num" value="<%= category.getOrderNum()==null? "":category.getOrderNum().toString() %>"/></td>
	  </tr>
	  <tr>
	  	<td colspan="2" height="32" align="center" valign="middle">
	  		<input type="submit" value="保存"/>&nbsp;<input type="reset" value="重置" />
	  	</td>
	  </tr>
    </table>
    
</form>

