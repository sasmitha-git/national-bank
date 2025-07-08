package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.model.Account;

import java.util.List;

@Remote
public interface AccountService {

    Account getAccountById(Long id);
    Account getAccountByAccountNumber(String accountNumber);
    void createAccount(Account account);
    void updateAccount(Account account);

    List<Account> getAccountsByUserId(Long id);

    void debitFromAccount(String accountNo, Double amount);
    void creditToAccount(String accountNo, Double amount);

}
