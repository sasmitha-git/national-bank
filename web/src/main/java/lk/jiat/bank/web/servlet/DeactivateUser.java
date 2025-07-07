package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.service.UserService;

import java.io.IOException;

@WebServlet("/admin/deactivate-user")
public class DeactivateUser extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = Long.parseLong(request.getParameter("userId"));
        userService.deactivateUser(userId);
        response.sendRedirect(request.getContextPath() + "/admin/users.jsp?success=deactivated");

    }
}
