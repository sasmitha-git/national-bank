
        <%@ page import="lk.jiat.bank.core.dto.InterestDTO" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/5/2025
  Time: 8:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.jiat.bank.core.model.Account" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.temporal.TemporalAdjusters" %>
<%@ page import="lk.jiat.bank.core.service.InterestService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>
<html>
<head>

    <title>National Bank</title>
    <link rel="icon" href="${pageContext.request.contextPath}/resources/images/logo.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/customer_dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>


<%
    try {
        InitialContext ic = new InitialContext();
        InterestService interestService = (InterestService) ic.lookup("lk.jiat.bank.core.service.InterestService");

        List<Account> userAccounts = (List<Account>) session.getAttribute("accounts");
        List<InterestDTO> allInterests = new java.util.ArrayList<>();
        double totalInterest = 0;

        LocalDate now = LocalDate.now();
        LocalDateTime firstDayOfMonth = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).atStartOfDay();

        for (Account acc : userAccounts) {
            List<InterestDTO> interestList = interestService.getInterestByAccountIdAndDateRange(
                    acc.getId(), firstDayOfMonth, lastDayOfMonth
            );
            allInterests.addAll(interestList);
            for (InterestDTO i : interestList) {
                totalInterest += i.getBalance();
            }
        }

        BigDecimal roundedTotal = BigDecimal.valueOf(totalInterest).setScale(2, RoundingMode.HALF_UP);
        pageContext.setAttribute("interestList", allInterests);
        pageContext.setAttribute("totalInterest", roundedTotal);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
%>



<input type="hidden" id="userId" value="${sessionScope.user}" />

<div class="dashboard-container">
    <div class="header">
        <div class="logo-section">
            <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="National Bank" class="logo-icon">
            <h2>National Bank</h2>
        </div>

        <div class="header-actions">
            <button type="button" class="user-profile-btn" onclick="openProfileModal()">
                <i class="fas fa-user-circle"></i>
            </button>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
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
        <h2><i class="fa-solid fa-coins"></i> Recent Transactions</h2>
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
            <h2><i class="fa-solid fa-clock"></i>_Schedule Future Transfer</h2>
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
        <h2><i class="fa-solid fa-clock"></i> Scheduled Transactions</h2>
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

    <%--Daily Balanced Updates--%>
    <div class="transaction-history">
        <h2><i class="fa-solid fa-chart-line"></i> Daily Interest Updates</h2>
        <div class="table-container">
            <table class="transaction-table">
                <thead>
                <tr>
                    <th>Account Number</th>
                    <th>Date</th>
                    <th>Interest Amount</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="interest" items="${interestList}">
                    <tr>
                        <td>${interest.accountNumber}</td>
                        <td>${interest.dateFormatted}</td>
                        <td><fmt:formatNumber value="${interest.balance}" type="currency" currencySymbol="Rs."/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="interest-summary">
            <h3>You've earned <fmt:formatNumber value="${totalInterest}" type="currency" currencySymbol="Rs."/> in interest this month.</h3>
        </div>
    </div>


</div>

<!-- User Profile Modal -->
<c:set var="userDTO" value="${sessionScope.userDTO}"/>
<div id="profileModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h3><i class="fas fa-user-circle"></i> My Profile</h3>
            <span class="close-btn" onclick="closeProfileModal()">&times;</span>
        </div>
        <div class="modal-body">
            <form method="POST" action="${pageContext.request.contextPath}/send-verification-code" class="profile-form">
                <input type="hidden" name="userId" value="${userDTO.id}">

                <div class="form-group">
                    <label><i class="fas fa-user"></i> Full Name</label>
                    <div class="readonly-field">${userDTO.fullName}</div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-envelope"></i> Email</label>
                    <div class="readonly-field">${userDTO.email}</div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-phone"></i> Phone</label>
                    <div class="readonly-field">${userDTO.phone}</div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-lock"></i> New Password</label>
                    <label>
                        <input type="password" name="newPassword" placeholder="Enter new password" class="form-control" required>
                    </label>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-lock"></i> Confirm Password</label>
                    <label>
                        <input type="password" name="confirmPassword" placeholder="Confirm new password" class="form-control" required>
                    </label>
                </div>

                <div class="form-actions">
                    <button type="button" class="cancel-btn" onclick="closeProfileModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                    <button type="submit" class="submit-btn" onclick="openVerificationModal()">
                        <i class="fas fa-save"></i> Update Password
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Verification Code Modal -->
<div id="verificationModal" class="modal">
    <div class="modal-content verification-modal">
        <div class="modal-header">
            <h3><i class="fas fa-shield-alt"></i> Verify Your Identity</h3>
            <span class="close-btn" onclick="closeVerificationModal()">&times;</span>
        </div>
        <div class="modal-body">
            <form method="POST" action="${pageContext.request.contextPath}/verified-update-password" id="verificationForm" class="profile-form">
                <div class="form-group">
                    <label><i class="fas fa-envelope"></i> Verification Code</label>
                    <p class="verification-instructions">We've sent a 6-digit code to your email. Please enter it below:</p>
                    <div class="verification-code-input">
                        <input
                                type="text"
                                name="code"
                                maxlength="6"
                                pattern="\d{6}"
                                title="Enter 6-digit verification code"
                                class="code-input"
                                placeholder="Enter 6-digit code"
                                required
                                inputmode="numeric"
                                autocomplete="one-time-code"
                        >
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" class="cancel-btn" onclick="closeVerificationModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                    <button type="submit" class="submit-btn">
                        <i class="fas fa-check"></i> Verify
                    </button>
                </div>
            </form>

        </div>
    </div>
</div>



<div class="footer">
    <i class="fa-solid fa-registered"></i> 2025 National Bank. All rights reserved.
</div>
<c:if test="${not empty param.model and param.model == 'verify'}">
    <script>
        window.addEventListener("DOMContentLoaded", function () {
            document.getElementById("verificationModal").style.display = "block";
        });
    </script>
</c:if>

<script src="${pageContext.request.contextPath}/resources/js/transactionHistory.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/open_close.js"></script>
</body>
</html>