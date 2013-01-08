<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@page import="com.ddh.bean.Category"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.ddh.util.SQLiteUtils"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%
List<Category> list = CategoryDAO.selectAll();
%>
<div style="float: left;" class="dtree">
<p><a href="javascript: d.openAll();">展开</a> | <a href="javascript: d.closeAll();">收起</a> | 
<a href="javascript: addNode();">Add</a> | <a href="javascript: delNode();">Del</a></p>
<div id="treeHtml"></div>
<script type="text/javascript">
	<!--
	//click node
	function clickNode(id) {
		if (!id || id=="0") {
			return;
		}
		//onLoad();
		$.ajaxSetup({
			cache : false
		});
		
		var url = "pages/title/category_edit.jsp?id=" + id;

		$.get(url, function(data) {
			$("#nodeinfo").html(data);
		});
	}
	// add node 
	function addNode() {
		var id = d.aNodes[d.selectedNode].id;
		if (id==undefined) {
			alert("请选中一个类别!");
			return;
		}
		$.ajaxSetup({
			cache : false
		});
		
		var url = "pages/title/category_add.jsp?pid=" + id;

		$.get(url, function(data) {
			$("#nodeinfo").html(data);
		});
	}
	
	// del node 
	function delNode() {
		var id = d.aNodes[d.selectedNode].id;
		if (id==undefined) {
			alert("请选中一个类别!");
			return;
		}
		if (!confirm('确定要删除吗?')) {
			return;
		}
		var node = d.get(id);
		$.ajaxSetup({
			cache : false
		});
		
		var url = "pages/title/category_del.jsp?id=" + id;
		$.get(url, function(data) {
			if ($.trim(data) == "SUCCESS") {
				alert("删除成功!");
				d.remove(node.id);
			} else {
				alert("删除失败!");
			}
		});
	}
	
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
	//-->
</script>
</div>