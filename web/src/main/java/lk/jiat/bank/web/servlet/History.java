package lk.jiat.bank.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.dto.TransactionDTO;
import lk.jiat.bank.core.model.Transaction;
import lk.jiat.bank.core.service.TransactionService;

import java.io.IOException;
import java.util.List;

@WebServlet("/transaction-history")
public class History extends HttpServlet {

    @EJB
    private TransactionService transactionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user");

        if(userId == null){
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<TransactionDTO> dto =transactionService.getTransactionsDTOByUserId(userId);

        Gson gson = new Gson();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(dto));

    }
}
