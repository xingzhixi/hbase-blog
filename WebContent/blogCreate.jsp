<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建博客</title>
</head>
<body>
	<form action="BlogCreateAction" method="post">
		标题：<input type="text" name="title"><br> 正文：
		<textarea name="content" rows="20" cols="80"></textarea>
		<br> <input type="submit" value="提交">
	</form>
</body>
</html>