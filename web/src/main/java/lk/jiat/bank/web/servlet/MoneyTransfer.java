package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.TransactionService;

import java.io.IOException;
import java.util.List;

@WebServlet("/transfer")
public class MoneyTransfer extends HttpServlet {

    @EJB
    private TransactionService transactionService;

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fromAccountNo = req.getParameter("fromAccount");
        String toAccountNo = req.getParameter("toAccount");
        String money = req.getParameter("amount");



        try{
            Double amount = Double.parseDouble(money);

            transactionService.transferFunds(fromAccountNo, toAccountNo, amount);

            Long userId = (Long) req.getSession().getAttribute("user");
            List<Account> updatedAccounts =  accountService.getAccountsByUserId(userId);
            req.getSession().setAttribute("accounts", updatedAccounts );

            resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp?success=transfer");

        }catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp?error=transfer");
        }
    }
}

