package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.service.UserService;
import lk.jiat.bank.core.util.Encrypt;

import java.io.IOException;

@WebServlet("/verified-update-password")
public class PasswordUpdate extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredCode = request.getParameter("code");
        String sessionCode = (String) request.getSession().getAttribute("verificationCode");
        String newPassword = (String) request.getSession().getAttribute("pendingPassword");

        if(enteredCode == null || !enteredCode.equals(sessionCode)) {
            response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp?error=InvalidCode");
            return;
        }

        Long userId = (Long) request.getSession().getAttribute("user");
        userService.passwordUpdate(userId,Encrypt.encrypt(newPassword));

        request.getSession().removeAttribute("verificationCode");
        request.getSession().removeAttribute("pendingPassword");
        response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp?success=passwordUpdated");

    }
}
