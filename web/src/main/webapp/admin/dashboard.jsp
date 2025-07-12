<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/5/2025
  Time: 8:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.bank.core.service.UserService" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="lk.jiat.bank.core.service.TransactionService" %>
<%@ page import="lk.jiat.bank.core.service.AccountService" %>
<%@ page import="lk.jiat.bank.core.dto.AccountDTO" %>
<%@ page import="lk.jiat.bank.core.dto.UserDTO" %>
<%@ page import="lk.jiat.bank.core.dto.TransactionDTO" %>
<html>
<head>
    <title>National Bank</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin_dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="container">
    <header>
        <div class="header-left">
            <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="National Bank" class="logo-icon">
            <h1>National Bank Admin Dashboard</h1>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i> Logout
        </a>
    </header>

    <%
        try {
            InitialContext ic = new InitialContext();
            UserService userService = (UserService) ic.lookup("lk.jiat.bank.core.service.UserService");
            AccountService accountService = (AccountService) ic.lookup("lk.jiat.bank.core.service.AccountService");
            TransactionService transactionService = (TransactionService) ic.lookup("lk.jiat.bank.core.service.TransactionService");

            List<UserDTO> users = userService.getAllCustomers();
            List<AccountDTO> accounts = accountService.getAllAccountsDTO();
            List<TransactionDTO> transactions = transactionService.getAllTransactionsDTO();

            int totalCustomers = users.size();
            long transactionsToday = transactionService.countTransactionsToday();
            double totalDeposits = accountService.getTotalDeposits();

            pageContext.setAttribute("totalCustomers",totalCustomers);
            pageContext.setAttribute("transactionsToday",transactionsToday);
            pageContext.setAttribute("totalDeposits",totalDeposits);
            pageContext.setAttribute("user", users);
            pageContext.setAttribute("accounts", accounts);
            pageContext.setAttribute("transactions", transactions);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    %>

    <!-- Overview Widgets -->
    <div class="widgets-container">
        <div class="widget">
            <div class="widget-icon">
                <i class="fas fa-users"></i>
            </div>
            <div class="widget-content">
                <h3>Total Active Customers</h3>
                <p class="widget-value">${totalCustomers}</p>
            </div>
        </div>

        <div class="widget">
            <div class="widget-icon">
                <i class="fas fa-exchange-alt"></i>
            </div>
            <div class="widget-content">
                <h3>Transactions Today</h3>
                <p class="widget-value">${transactionsToday}</p>
            </div>
        </div>

        <div class="widget">
            <div class="widget-icon">
                <i class="fas fa-piggy-bank"></i>
            </div>
            <div class="widget-content">
                <h3>Total Deposits</h3>
                <p class="widget-value">${totalDeposits}</p>
            </div>
        </div>
    </div>

    <!-- Search and User Management Section -->
    <div class="dashboard-section">
        <div class="section-header">
            <h2 class="section-title"><i class="fas fa-users"></i> User Management</h2>
            <button class="action-btn add-btn" onclick="openAddUserModal()">
                <i class="fas fa-user-plus"></i> Add User
            </button>
        </div>

        <div class="section-content">
            <table class="usersTable">
                <thead>
                <tr>
                    <th>Customer Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Manage</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="users" items="${user}">
                    <tr>
                        <td>${users.fullName}</td>
                        <td>${users.email}</td>
                        <td>${users.phone}</td>
                        <td>${users.userRole}</td>
                        <td><span class="status-active">${users.active}</span></td>
                        <td>
                            <button class="simple-action-btn"
                                    onclick="window.location.href='${pageContext.request.contextPath}/admin/manage_customer.jsp?id=${users.id}'">
                                Manage
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


    <!-- Add Customer Modal -->
    <div id="userModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="modalTitle">Add Customer</h3>
                <span class="close-btn" onclick="closeModal()">&times;</span>
            </div>
            <div class="modal-body">
                <form id="userForm" method="POST" action="${pageContext.request.contextPath}/register">
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <input type="text" id="fullName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone</label>
                        <input type="text" id="phone" name="phone">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="cancel-btn" onclick="closeModal()">Cancel</button>
                        <input type="submit" id="saveUserBtn" class="submit-btn" value="Register">
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="dashboard-section">
        <div class="section-header">
            <h2 class="section-title"> <i class="fa fa-university"></i> Customer Accounts</h2>
        </div>
        <div class="section-content">
            <table class="usersTable">
                <thead>
                <tr>
                    <th>Customer Name</th>
                    <th>Account Number</th>
                    <th>Account Type</th>
                    <th>Balance</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="accounts" items="${accounts}">
                    <tr>
                        <td>${accounts.userFullName}</td>
                        <td>${accounts.accountNumber}</td>
                        <td>${accounts.accountType}</td>
                        <td>${accounts.balance}</td>
                        <td class="status-active">Active</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>

    <!-- Transactions Section -->
    <div class="dashboard-section">
        <div class="section-header">
            <h2 class="section-title"><i class="fa-solid fa-money-check-dollar"></i> Recent Transactions</h2>
        </div>
        <div class="section-content">
            <table class="usersTable">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>From → To</th>
                    <th>Amount</th>
                    <th>Type</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="transactions" items="${transactions}">
                    <tr>
                        <td>${transactions.timestamp}</td>
                        <td>${transactions.fromAccountNumber} → ${transactions.toAccountNumber}</td>
                        <td>${transactions.amount}</td>
                        <td><span class="transaction-type type-transfer">${transactions.transactionType}</span></td>
                        <td class="status-active">${transactions.status}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>

    <!-- Reports Section -->
    <div class="dashboard-section">
        <div class="section-header">
            <h2 class="section-title">Reports</h2>
            <div class="report-controls">
                <select id="reportType">
                    <option value="monthly">Monthly Transactions</option>
                    <option value="quarterly">Quarterly Summary</option>
                    <option value="customers">Customer Activity</option>
                </select>
                <input type="month" id="reportMonth" value="2023-06">
                <button class="generate-btn"><i class="fas fa-file-download"></i> Generate Report</button>
            </div>
        </div>
        <div class="section-content">
            <div class="report-preview">
                <h3>Monthly Transactions Report - June 2023</h3>
                <div class="chart-container">
                    <!-- Placeholder for chart -->
                    <div class="chart-placeholder">
                        <i class="fas fa-chart-bar"></i>
                        <p>Transaction data visualization will appear here</p>
                    </div>
                </div>
                <div class="report-summary">
                    <div class="summary-item">
                        <span class="summary-label">Total Transactions:</span>
                        <span class="summary-value">1,245</span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">Total Amount:</span>
                        <span class="summary-value">$842,750.00</span>
                    </div>
                    <div class="summary-item">
                        <span class="summary-label">New Accounts:</span>
                        <span class="summary-value">87</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/open_close.js"></script>

</body>
</html>
