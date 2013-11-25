<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="hbase.BlogHbase"%>
<%@page import="bean.Blog"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客编辑</title>
</head>
<body>
	<%
		String blogId = request.getParameter("blogId");
		BlogHbase hbase = BlogHbase.create();
		Blog blog = hbase.getBlogInfoByRowKey(blogId);
	%>
	<form action="BlogEditAction" method="post">
		<input type="hidden" name="rowKey" value="<%=blog.getRowKey()%>">
		标题：<input type="text" name="title" value="<%=blog.getInfoTitle()%>"><br>
		正文：
		<textarea rows="20" cols="80" name="content"><%=blog.getInfoContent()%></textarea>
		<br> <input type="submit" value="提交修改">
	</form>
</body>
</html>