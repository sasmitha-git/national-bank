package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.AccountType;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/admin/create-account")
public class CreateBankAccount extends HttpServlet {

    @EJB
    private AccountService accountService;

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = Long.valueOf(request.getParameter("userId"));
        String accountNumber = request.getParameter("accountNumber");
        String accountType = request.getParameter("accountType");
        double balance = Double.parseDouble(request.getParameter("balance"));

        User user = userService.getUserById(userId);

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setAccountType(AccountType.valueOf(accountType));
        account.setUser(user);
        account.setCreatedAt(LocalDateTime.now());

        accountService.createAccount(account);

        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");

    }
}
