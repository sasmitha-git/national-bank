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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<input type="hidden" id="userId" value="${sessionScope.user}" />

<div class="dashboard-container">
    <div class="header">
        <div class="logo-section">
            <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="National Bank" class="logo-icon">
            <h2>National Bank</h2>
        </div>

        <div class="header-actions">
            <a href="#" class="user-profile-btn" onclick="openProfileModal()">
                <i class="fas fa-user-circle"></i>
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a>
        </div>
    </div>


    <div class="account-summary">
        <h1>Welcome, ${pageContext.request.userPrincipal.name}</h1>
        <h2>Your Accounts</h2>
        <div class="account-grid" id="accountGrid">

        </div>
    </div>


    <div class="transfer-form">
        <h2><i class="fas fa-users"></i> Transfer Funds</h2>
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
        <div class="table-container">
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

                </tbody>
            </table>
        </div>
    </div>

    <div class="scheduled-transfer-container">
        <input type="checkbox" id="scheduleToggle" class="toggle-checkbox">
        <label for="scheduleToggle" class="toggle-header">
            <h2>Schedule Future Transfer</h2>
        </label>
        <div class="scheduled-transfer-wrapper">
            <div class="scheduled-transfer-form">
                <form method="POST" action="${pageContext.request.contextPath}/schedule-transfer">
                    <div class="form-group">
                        <label>From Account:</label>
                        <select name="fromAccount" class="form-control" required>
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
                    <div class="form-group">
                        <label>Schedule Date/Time:</label>
                        <input type="datetime-local" name="scheduleTime" class="form-control" required>
                    </div>
                    <button type="submit" class="btn schedule-btn">Schedule Transfer</button>
                </form>
            </div>
        </div>
    </div>

    <div class="transaction-history">
        <h2>Scheduled Transactions</h2>
        <div class="table-container">
            <table class="transaction-table">
                <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Next Execution</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody id="scheduledTaskTableBody"></tbody>
            </table>
        </div>
    </div>

</div>

<!-- User Profile Modal -->
<div id="profileModal" class="modal" style="display:none;">
    <div class="modal-content">
        <div class="modal-header">
            <h3><i class="fas fa-user-circle"></i> My Profile</h3>
            <span class="close-btn" onclick="closeModal()">&times;</span>
        </div>
        <div class="modal-body">
            <form method="POST" action="${pageContext.request.contextPath}/update-profile" class="profile-form">
                <input type="hidden" name="userId" value="">

                <div class="form-group">
                    <label><i class="fas fa-user"></i> Full Name</label>
                    <div class="readonly-field"></div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-envelope"></i> Email</label>
                    <div class="readonly-field"></div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-phone"></i> Phone</label>
                    <div class="readonly-field"></div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-lock"></i> New Password</label>
                    <input type="password" name="newPassword" placeholder="Enter new password" class="form-control">
                </div>

                <div class="form-group">
                    <label><i class="fas fa-lock"></i> Confirm Password</label>
                    <input type="password" name="confirmPassword" placeholder="Confirm new password" class="form-control">
                </div>

                <div class="form-actions">
                    <button type="button" class="cancel-btn" onclick="closeProfileModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                    <button type="submit" class="submit-btn">
                        <i class="fas fa-save"></i> Update Password
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/transactionHistory.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/open_close.js"></script>
</body>
</html>