<%@page import="java.sql.Timestamp"%>
<%@page import="com.ddh.database.dao.ArticleDAO"%>
<%@page import="com.ddh.bean.Article"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	
	try {
		ArticleDAO.delete(new Integer(id));
		response.getWriter().println("SUCCESS");
	} catch(Exception e) {
		response.getWriter().println("操作失败" + e);
	}
%>



