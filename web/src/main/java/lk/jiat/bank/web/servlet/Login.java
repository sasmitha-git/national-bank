package lk.jiat.bank.web.servlet;

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

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AuthenticationParameters parameters = AuthenticationParameters.withParams()
                .credential(new UsernamePasswordCredential(email, password))
                .newAuthentication(true);

        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);

        System.out.println("Authentication status: " + status);

        if(status == AuthenticationStatus.SUCCESS) {
            response.sendRedirect(request.getContextPath()+"/check-role");

        }else{
            response.sendRedirect(request.getContextPath()+"/index.jsp?error=true");
            //throw exception here.
        }
    }
}
