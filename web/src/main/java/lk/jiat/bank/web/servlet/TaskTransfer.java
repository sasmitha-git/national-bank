package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.dto.Task;
import lk.jiat.bank.core.service.TaskService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@WebServlet("/schedule-transfer")
public class TaskTransfer extends HttpServlet {

    @EJB
    private TaskService taskService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fromAccountNo = req.getParameter("fromAccount");
        String toAccountNo = req.getParameter("toAccount");
        double amount = Double.parseDouble(req.getParameter("amount"));
        String schedule = req.getParameter("scheduleTime");

        LocalDateTime scheduleTime = LocalDateTime.parse(schedule);
        long delayMillis = Duration.between(LocalDateTime.now(), scheduleTime).toMillis();

        if(delayMillis < 0) delayMillis =0;

        Task task = new Task(fromAccountNo, toAccountNo, amount);
        taskService.scheduleTransfer(task, delayMillis);

        resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp?success=transfer");


    }
}
