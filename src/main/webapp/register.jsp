<%--
  Created by IntelliJ IDEA.
  User: druva
  Date: 06/07/2024
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form method="post" action="/register">
    <input type="text" name="username" placeholder="Enter username" required/>
    <input type="password" name="password"  placeholder="Enter password" required/>
    <button type="submit">register</button>
</form>


<a href="login.jsp">already a user? Login</a>
</body>
</html>
