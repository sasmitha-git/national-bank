package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.model.Account;

@Remote
public interface AccountService {

    Account getAccountById(Long id);
    Account getAccountByAN(String accountNumber);
    void createAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(Account account);

}
