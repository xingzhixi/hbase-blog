<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body>
注册页面<br>
	<form action="UserRegisterAction" method="post">
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" name="uname"></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" name="upwd"></td>
			</tr>
			<tr>
				<td><input type="submit" value="注册"></td>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>