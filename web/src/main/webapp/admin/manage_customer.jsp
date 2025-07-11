<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/11/2025
  Time: 11:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.bank.core.service.UserService" %>
<%@ page import="lk.jiat.bank.core.model.User" %>
<%@ page import="javax.naming.NamingException" %>
<html>
<head>
    <title>Manage Customer | National Bank</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/manage_customer.css">
</head>
<body>

<%
    try {

        InitialContext ic = new InitialContext();
        UserService userService = (UserService) ic.lookup("lk.jiat.bank.core.service.UserService");

        String userIdStr = request.getParameter("id");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is missing.");
        }
        Long userId = Long.valueOf(userIdStr);

        User users = userService.getUserById(userId);
        pageContext.setAttribute("user",users);
    } catch (NamingException e) {
        throw new RuntimeException(e);
    }

%>

<div class="container">
    <header>
        <div class="header-left">
            <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="National Bank" class="logo-icon">
            <h1>National Bank Manage Customer</h1>
        </div>
        <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="logout-btn">
            &larr; Back to Dashboard
        </a>
    </header>

    <div class="dashboard-section">
        <div class="section-header">
            <h2 class="section-title">Edit Customer Details</h2>
        </div>

        <div class="section-content">
            <form id="customerForm" method="POST" action="${pageContext.request.contextPath}/admin/user_update">
                <input type="hidden" name="userId" value="${user.id}">

                <div class="form-group">
                    <label for="fullName">Full Name *</label>
                    <input type="text" id="fullName" name="fullName"
                           value="${user.fullName}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email"
                           value="${user.email}" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone"
                           value="${user.phone}">
                </div>

                <div class="form-group">
                    <label>Status</label>
                    <div class="status-toggle">
                        <input type="checkbox" id="active" name="active" ${user.active ? "checked" : ""}>
                        <label for="active" class="toggle-switch">
                            <span class="toggle-slider"></span>
                            <span class="toggle-label active">Active</span>
                            <span class="toggle-label inactive">Inactive</span>
                        </label>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="submit-btn">Update Customer</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Create Account Section -->
    <div class="dashboard-section">
        <div class="section-header">
            <h2 class="section-title">Create Bank Account for ${user.fullName}</h2>
        </div>

        <div class="section-content">
            <form method="POST" action="${pageContext.request.contextPath}/admin/create-account">
                <input type="hidden" name="userId" value="${user.id}">

                <div class="form-group">
                    <label for="accountNumber">Account Number *</label>
                    <input type="text" id="accountNumber" name="accountNumber" required>
                </div>

                <div class="form-group">
                    <label for="accountType">Account Type *</label>
                    <select id="accountType" name="accountType" required>
                        <option value="SAVING">SAVING</option>
                        <option value="CURRENT">CURRENT</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="balance">Deposit Balance *</label>
                    <input type="number" id="balance" name="balance" min="0" step="0.01" required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="submit-btn">Create Account</button>
                </div>
            </form>
        </div>
    </div>

</div>
</body>
</html>