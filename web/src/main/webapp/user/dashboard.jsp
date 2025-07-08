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

    <title>National Bank</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customer_dashboard.css">
</head>
<body>

<div class="dashboard-container">
    <div class="header">
       <div class="logo-section">
           <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="National Bank" class="logo-icon">
           <h2>National Bank </h2>
       </div>
           <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
    </div>

    <div class="account-summary">
        <h1>Welcome, ${pageContext.request.userPrincipal.name}</h1>
        <h2>Your Accounts</h2>
        <div class="account-grid" id="accountGrid">
            <c:forEach items="${sessionScope.accounts}" var="account">
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
        <form method="POST"  action="${pageContext.request.contextPath}/transfer" >
            <div class="form-group">
                <label>From Account:</label>
                <select name="fromAccount" class="form-control">
                    <c:forEach items="${sessionScope.accounts}" var="account">
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
            <tbody id="transactionTableBody">
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