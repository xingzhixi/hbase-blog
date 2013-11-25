<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="bean.Blog"%>
<%@ page import="bean.Blog.Comment"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客详情</title>
</head>
<body>
	<%
		Blog blog = (Blog) request.getAttribute("blog");
		if (blog != null) {
	%>
	
<a href="blog.jsp">博客首页</a>	
<br>
<br>
<br>
<br>
	<table>
		<tr>
			<td>博客名称</td>
			<td><%=blog.getInfoTitle()%></td>
		</tr>
		<tr>
			<td>作者</td>
			<td><%=blog.getInfoAuthor()%></td>
		</tr>
		<tr>
			<td>内容</td>
			<td><%=blog.getInfoContent()%></td>
		</tr>
	</table>

	<br>
	<br>
	<br>
	<br>评论
	<br>
	<table>
		<%
			List<Comment> comms = blog.getComments();
				for (Comment comm : comms) {
		%>
		<tr>
			<td>评论</td>
			<td><%=comm.getContent()%></td>
		</tr>
		<tr>
			<td>作者</td>
			<td><%=comm.getReviewer()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>

	<br>
	<br>
	<br>
	<br>添加评论
	<br>
	<form action="BlogCommentAddAction" method="post">
		<input type="hidden" name="blogId" value="<%=blog.getRowKey()%>">
		<textarea rows="20" cols="80" name="comment"></textarea>
		<input type="submit" value="提交">
	</form>

</body>
</html>