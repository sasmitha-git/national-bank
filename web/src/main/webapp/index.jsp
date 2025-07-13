<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login.css">
</head>
<body>
<div class="login-container">
    <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="National Bank Logo" class="logo">
    <h1>Login to Your Account</h1>
    <c:if test="${not empty param.error}">
        <div class="error-message">
            <c:choose>
                <c:when test="${param.error == 'invalid'}">Invalid email or password. Please try again.</c:when>
                <c:when test="${param.error == 'inactive'}">Your account is inactive. Please contact support.</c:when>
                <c:when test="${param.error == 'session'}">Session expired. Please log in again.</c:when>
                <c:when test="${param.error == 'role'}">Unauthorized access. Role mismatch.</c:when>
            </c:choose>
        </div>
    </c:if>

    <form method="POST" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" id="email" name="email" placeholder="Enter your email">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter your password">
            <a href="#" class="forgot-password">Forgot password?</a>
        </div>

        <input type="submit" value="Sign In">
    </form>

    <div class="footer">
        © 2023 National Bank. All rights reserved.
    </div>
</div>
</body>
</html>
