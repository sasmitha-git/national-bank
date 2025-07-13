package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.service.UserService;
import lk.jiat.bank.core.util.Encrypt;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String email = request.getParameter("email");
        String password = request.getParameter("password");



        if(!userService.isActiveUser(email)){
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=inactive");
            return;
        }

        String encryptedPassword = Encrypt.encrypt(password);

        AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(new UsernamePasswordCredential(email, encryptedPassword))
                .newAuthentication(true);

        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);

        System.out.println("Authentication status: " + status);

        if(status == AuthenticationStatus.SUCCESS) {
            //new
            User user = userService.getUserByEmail(email);
            request.getSession().setAttribute("user", user.getId());

            response.sendRedirect(request.getContextPath()+"/check-role");

        }else{
            response.sendRedirect(request.getContextPath()+"/index.jsp?error=invalid");
            //throw exception here.
        }
    }
}
