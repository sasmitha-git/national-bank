package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.ScheduledTask;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.ScheduleService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/schedule-transfer")
public class ScheduleTransfer extends HttpServlet {


    @EJB
    private ScheduleService scheduleService;

    @EJB
    private AccountService accountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{

            String fromAccountNo = request.getParameter("fromAccount");
            String toAccountNo = request.getParameter("toAccount");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String dateTime = request.getParameter("scheduleTime");


            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime scheduleTime = LocalDateTime.parse(dateTime, formatDate);

            if(amount <= 50){
                request.getSession().setAttribute("errorMessage", "Amount must be greater than Rs.50.0");
                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
                return;
            }

            if(scheduleTime.isBefore(LocalDateTime.now())){
                request.getSession().setAttribute("errorMessage", "Schedule time must be after current time");
                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
                return;
            }

            if (dateTime.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Schedule time is required");
                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
                return;
            }


            Account fromAccount = accountService.getAccountByAccountNumber(fromAccountNo);
            Account toAccount = accountService.getAccountByAccountNumber(toAccountNo);

            if(fromAccount == null || toAccount == null){
                request.getSession().setAttribute("errorMessage", "Account not found");
                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
                return;
            }

            if (fromAccount.getBalance() < amount) {
                request.getSession().setAttribute("errorMessage", "Insufficient balance in the source account.");
                response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
                return;
            }

            Long userId = (Long) request.getSession().getAttribute("user");

            if(userId == null){
                throw new IllegalStateException("User not found");
            }

            ScheduledTask scheduledTask = new ScheduledTask();
            scheduledTask.setUser(userId);
            scheduledTask.setFromAccount(fromAccount);
            scheduledTask.setToAccount(toAccount);
            scheduledTask.setAmount(amount);
            scheduledTask.setNextExecutionTime(scheduleTime);

            long delayInMillis = java.time.Duration.between(LocalDateTime.now(),scheduleTime).toMillis();

            scheduleService.scheduleTransfer(scheduledTask, delayInMillis);

            response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");

        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Something went wrong while scheduling. Please try again.");
            response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp");
        }

    }
}
