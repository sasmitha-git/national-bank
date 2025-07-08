<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/5/2025
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>National Bank | Login</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/logo.png">
</head>
<body>

<h1>National Bank Login to your account.</h1>

<form method="POST" action="${pageContext.request.contextPath}/login">
    <table>
        <tr>
            <th>Email</th>
            <td><input type="text" name="email"></td>
        </tr>
        <tr>
            <th>Password</th>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td><input type="submit" value="login"></td>
        </tr>
    </table>
</form>


</body>
</html>
