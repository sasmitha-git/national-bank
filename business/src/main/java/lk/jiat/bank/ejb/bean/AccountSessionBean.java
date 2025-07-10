package lk.jiat.bank.ejb.bean;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.AccountType;
import lk.jiat.bank.core.service.AccountService;

import java.util.List;

@Stateless
public class AccountSessionBean implements AccountService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Account getAccountById(Long id) {
        return em.find(Account.class, id);
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        try {
            return em.createNamedQuery("Account.findByAccountNumber",Account.class)
                    .setParameter("accNo",accountNumber).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public void createAccount(Account account) {
        em.persist(account);
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public void updateAccount(Account account) {
        em.merge(account);
    }

    @Override
    public List<Account> getAccountsByUserId(Long id) {
        return em.createNamedQuery("Account.findAccountByUserId",Account.class)
                .setParameter("userId",id).getResultList();
    }

    @Override
    public List<Account> getAllSavingAccounts() {
        return em.createNamedQuery("Account.findSavingAccounts", Account.class)
                .setParameter("type", AccountType.SAVING).getResultList();
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void debitFromAccount(String accountNo, Double amount) {
        Account account = getAccountByAccountNumber(accountNo);
        if(account != null && account.getBalance() >= amount){
            account.setBalance(account.getBalance() - amount);
            em.merge(account);
        }
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void creditToAccount(String accountNo, Double amount) {
        Account account = getAccountByAccountNumber(accountNo);
        if(account != null && amount > 0){
            account.setBalance(account.getBalance() + amount);
            em.merge(account);
        }
    }
}
