<%@page import="java.util.List"%>
<%@page import="hbase.BlogHbase"%>
<%@page import="bean.Blog"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客</title>
</head>
<body>
	<form action="blogCreate.jsp" method="post">
		<input type="submit" value="创建">
	</form>

	<%
		String uname = (String) request.getSession().getAttribute("uname");
		BlogHbase blogHbase = BlogHbase.create();
		List<Blog> list = blogHbase.getBlogInfoByUser(uname);
		List<Blog> allBlogs = blogHbase.getAllBlogInfo();
	%>
	个人博客
	<table>
		<tr>
			<td>标题</td>
			<td>创建时间</td>
			<td>查看</td>
			<td>修改</td>
			<td>删除</td>
		</tr>
		<%
			if (list != null) {
				for (Blog b : list) {
		%>
		<tr>
			<td><%=b.getInfoTitle()%></td>
			<td></td>
			<td><a href="BlogViewAction?blogId=<%=b.getRowKey()%>">查看</a></td>
			<td><a href="blogEdit.jsp?blogId=<%=b.getRowKey()%>">修改</a></td>
			<td><a href="BlogDeleteAction?blogId=<%=b.getRowKey()%>">删除</a></td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<br>
	<br>
	<br>
	所有博客
	<br>
	<table>
		<tr>
			<td>标题</td>
			<td>创建时间</td>
			<td>博主</td>			
			<td>查看</td>
		</tr>
		<%
			if (allBlogs != null) {
				for (Blog b : allBlogs) {
		%>
		<tr>
			<td><%=b.getInfoTitle()%></td>
			<td></td>
			<td><%=b.getInfoAuthor() %></td>
			<td><a href="BlogViewAction?blogId=<%=b.getRowKey()%>">查看</a></td>
		</tr>
		<%
			}
			}
		%>
	</table>
</body>
</html>