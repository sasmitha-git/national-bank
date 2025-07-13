package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.mail.VerificationCode;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.provider.MailServiceProvider;
import lk.jiat.bank.core.service.UserService;

import java.io.IOException;
import java.util.Random;

@WebServlet("/send-verification-code")
public class SendVerifyCode extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");


        if(newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()){
            response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp?error=EmptyFields");
            return;
        }

        if(!newPassword.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp?error=PasswordMismatch");
            return;
        }

        Long userId = Long.parseLong(request.getParameter("userId"));
        User user = userService.getUserById(userId);

        String verificationCode = String.valueOf(new Random().nextInt(900000)+100000);

        request.getSession().setAttribute("verificationCode", verificationCode);
        request.getSession().setAttribute("pendingPassword", newPassword);

        MailServiceProvider.getInstance().sendmail(new VerificationCode(user.getEmail(),verificationCode));

        response.sendRedirect(request.getContextPath() + "/user/dashboard.jsp?model=verify");

    }
}
