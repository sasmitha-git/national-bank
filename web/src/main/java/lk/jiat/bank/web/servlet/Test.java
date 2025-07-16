package lk.jiat.bank.web.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.AccountType;
import lk.jiat.bank.core.model.User;

import java.io.IOException;

@WebServlet("/test")
public class Test extends HttpServlet {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User testUser = new User();

        for (int i = 1; i <= 500; i++) {
            Account acc = new Account();
            acc.setAccountNumber("TEST" + i);
            acc.setAccountType(AccountType.SAVING);
            acc.setBalance(100000.0);
            acc.setUser(testUser);
            em.persist(acc);
        }



    }
}
