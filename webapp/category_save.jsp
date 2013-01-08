<%@page import="com.ddh.database.dao.CategoryDAO"%>
<%@page import="com.ddh.bean.Category"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");
String name = request.getParameter("name");
String orderNum = request.getParameter("orderNum");
String pid = request.getParameter("pid");

Category categoryDB = CategoryDAO.selectByNameAndPid(name, new Integer(pid));
if (categoryDB == null || (id !=null && categoryDB.getId().toString().equals(id)))  {
	Category category = new Category();
	category.setName(name);
	category.setPid(new Integer(pid));
	category.setOrderNum(new Integer(orderNum));


	if (id == null || id.trim().equals("")) {
		CategoryDAO.insert(category);
	%>
		<script type="text/javascript">
			<!--
				parent.document.getElementById("id").value="<%= category.getId() %>";
				parent.d.add('<%= category.getId() %>','<%= category.getPid()%>','<%= category.getName()%>','javascript:clickNode(\'<%=category.getId()%>\')');
				parent.d.reset();
				parent.d.renderTo();
				parent.d.openTo("<%= category.getId() %>", true);
			//-->
		</script>
  <%
	} else {
		category.setId(new Integer(id));
		CategoryDAO.update(category);
	%>
		<script type="text/javascript">
			<!--
				var node = parent.d.get("<%= category.getId() %>");
				node.name = "<%= category.getName()%>";
				parent.d.renderTo();
			//-->
		</script>
	<%
	}
} else {
%>
	<script type="text/javascript">
		<!--
			parent.alert("已存在!");
		//-->
	</script>
<%
}
%>



