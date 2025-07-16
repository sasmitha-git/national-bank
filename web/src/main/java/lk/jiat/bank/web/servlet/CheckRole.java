package lk.jiat.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.jiat.bank.core.dto.UserDTO;
import lk.jiat.bank.core.exception.UnauthorizedException;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.User;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/check-role")
public class CheckRole extends HttpServlet {

    @EJB
    private AccountService accountService;

    @EJB
    private UserService userService;

    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            if(securityContext.isCallerInRole("ADMIN")){
                resp.sendRedirect(req.getContextPath()+"/admin/dashboard.jsp");
            } else if (securityContext.isCallerInRole("CUSTOMER")) {

                Long userId = (Long) req.getSession().getAttribute("user");

                if(userId == null){
                    resp.sendRedirect(req.getContextPath()+"/index.jsp?error=session");
                    return;
                }
                List<Account> accounts = accountService.getAccountsByUserId(userId);
                User user = userService.getUserById(userId);
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setFullName(user.getFullName());
                dto.setEmail(user.getEmail());
                dto.setPhone(user.getPhone());


                req.getSession().setAttribute("accounts",accounts);
                req.getSession().setAttribute("userDTO",dto);
                resp.sendRedirect(req.getContextPath()+"/user/dashboard.jsp");
            }else {
                resp.sendRedirect(req.getContextPath()+"/index.jsp?error=role");
                throw new UnauthorizedException("User is not authorized");
            }


        } catch (UnauthorizedException e) {
            resp.sendRedirect(req.getContextPath()+"/unauthorized.jsp");
        }

    }
}
