package lk.jiat.bank.web.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lk.jiat.bank.core.dto.AccountDTO;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.service.AccountService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @EJB
    private AccountService accountService;

    @GET
    public Response getAllAccounts(@QueryParam("userId") Long userId) {
        if(userId == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("userId is null").build();
        }

        List<Account> accounts = accountService.getAccountsByUserId(userId);

        List<AccountDTO> accountDTOs = accounts.stream()
                .map(AccountDTO::new).collect(Collectors.toList());
        return Response.ok(accountDTOs).build();
    }
}
