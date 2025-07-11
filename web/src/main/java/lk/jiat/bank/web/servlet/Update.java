package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.service.UserService;

import java.io.IOException;

@WebServlet("/admin/user_update")
public class Update extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = Long.valueOf(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        boolean status = request.getParameter("active") != null;

        System.out.println("Received ID: " + request.getParameter("userId"));

        User user = userService.getUserById(userId);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setActive(status);

        userService.updateUser(user);

        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");

    }
}
