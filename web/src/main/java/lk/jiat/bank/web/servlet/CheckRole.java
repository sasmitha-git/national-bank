package lk.jiat.bank.web.servlet;

import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/check-role")
public class CheckRole extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(securityContext.isCallerInRole("ADMIN")){
            resp.sendRedirect(req.getContextPath()+"/admin/dashboard.jsp");
        } else if (securityContext.isCallerInRole("CUSTOMER")) {
            resp.sendRedirect(req.getContextPath()+"/user/dashboard.jsp");
        }else {
            resp.sendRedirect(req.getContextPath()+"/index.jsp?error=role");
        }
    }
}
