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

            if(amount <= 0){
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            if(scheduleTime.isBefore(LocalDateTime.now())){
                throw new IllegalArgumentException("Schedule time must be before current time");
            }

            Account fromAccount = accountService.getAccountByAccountNumber(fromAccountNo);
            Account toAccount = accountService.getAccountByAccountNumber(toAccountNo);

            if(fromAccount == null || toAccount == null){
                throw new IllegalArgumentException("Account not found");
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
        }

    }
}
