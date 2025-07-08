package lk.jiat.bank.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.Transaction;
import lk.jiat.bank.core.model.TransactionStatus;
import lk.jiat.bank.core.model.TransactionType;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.TransactionService;

import java.util.List;


@Stateless
public class TransactionSessionBean implements TransactionService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AccountService accountService;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void transferFunds(String fromAccountNo, String toAccountNo, Double amount) {

        if (fromAccountNo == null || toAccountNo == null) {
            throw new IllegalArgumentException("Invalid account number");
        }

        if(amount == null || amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        accountService.debitFromAccount(fromAccountNo, amount);
        accountService.creditToAccount(toAccountNo, amount);

        Account sourceAccountNo = accountService.getAccountByAccountNumber(fromAccountNo);
        Account destinationAccountNo = accountService.getAccountByAccountNumber(toAccountNo);



        Transaction transaction = new Transaction(
                sourceAccountNo,
                destinationAccountNo,
                amount,
                TransactionType.DEBIT,
                TransactionStatus.SUCCESS
        );
        em.persist(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return em.createNamedQuery("Transaction.findTransactionByUserId", Transaction.class)
                .setParameter("userId", userId).getResultList();
    }


}
