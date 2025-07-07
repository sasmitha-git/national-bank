<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/5/2025
  Time: 8:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customer_dashboard.css">
</head>
<body>
<div class="dashboard-container">
    <div class="header">
        <h1>Welcome, ${user.fullName}</h1>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
    </div>


    <div class="account-summary">
        <h2>Your Accounts</h2>
        <div class="account-grid">
            <c:forEach items="${accounts}" var="account">
                <div class="account-card">
                    <h3>${account.accountType} Account</h3>
                    <p>Account Number: ${account.accountNumber}</p>
                    <p>Balance: ${account.balance}</p>
                </div>
            </c:forEach>
        </div>
    </div>


    <div class="transfer-form">
        <h2>Transfer Funds</h2>
        <form action="${pageContext.request.contextPath}/transfer" method="post">
            <div class="form-group">
                <label>From Account:</label>
                <select name="fromAccount" class="form-control">
                    <c:forEach items="${accounts}" var="account">
                        <option value="${account.accountNumber}">${account.accountNumber}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>To Account:</label>
                <input type="text" name="toAccount" class="form-control" required>
            </div>
            <div class="form-group">
                <label>Amount:</label>
                <input type="number" name="amount" step="0.01" class="form-control" required>
            </div>
            <button type="submit" class="btn">Transfer</button>
        </form>
    </div>


    <div class="transaction-history">
        <h2>Recent Transactions</h2>
        <table class="transaction-table">
            <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${transactions}" var="transaction">
                <tr>
                    <td>${transaction.timestamp}</td>
                    <td>${transaction.transactionType}</td>
                    <td>${transaction.amount}</td>
                    <td>${transaction.status}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>