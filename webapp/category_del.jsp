<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");

int row = CategoryDAO.delete(new Integer(id));
if (row == 0) {
	%>
	FAIL
<%
} else if (row == 1){
%>
	SUCCESS
<%
} else {
	%>
	MULTI
<%
}
%>



