package lk.jiat.bank.web.servlet;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.mail.WelcomeMessage;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.provider.MailServiceProvider;
import lk.jiat.bank.core.service.UserService;
import lk.jiat.bank.core.util.Encrypt;

import java.io.IOException;

@DeclareRoles({"ADMIN"})
@ServletSecurity(@HttpConstraint(rolesAllowed = {"ADMIN"}))
@WebServlet("/register")
public class Register extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");


        String encryptPassword = Encrypt.encrypt(password);

        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(encryptPassword);

        userService.addUser(user);

        WelcomeMessage welcomeMessage = new WelcomeMessage(email,name,password);
        MailServiceProvider.getInstance().sendmail(welcomeMessage);

        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");


    }
}
