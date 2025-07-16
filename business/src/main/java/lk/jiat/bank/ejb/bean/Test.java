package lk.jiat.bank.ejb.bean;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import lk.jiat.bank.core.dto.TransactionDTO;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.Transaction;
import lk.jiat.bank.core.model.TransactionStatus;
import lk.jiat.bank.core.model.TransactionType;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.TransactionService;

import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class Test {

//    @PersistenceContext
//    private EntityManager em;
//
//    @Inject
//    private UserTransaction userTransaction;
//
//    @EJB
//    private AccountService accountService;
//
//    @Override
//    public void transferFunds(String fromAccountNo, String toAccountNo, Double amount) {
//
//        try {
//            userTransaction.begin();
//        } catch (NotSupportedException e) {
//            throw new RuntimeException(e);
//        } catch (SystemException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            if (fromAccountNo == null || toAccountNo == null || amount <= 0) {
//                throw new IllegalArgumentException("Invalid transfer parameters");
//            }
//
//
//            Account fromAccount = accountService.getAccountByAccountNumber(fromAccountNo);
//            Account toAccount = accountService.getAccountByAccountNumber(toAccountNo);
//            if (fromAccount == null || toAccount == null) {
//                throw new IllegalArgumentException("Account not found");
//            }
//            accountService.debitFromAccount(fromAccountNo, amount);
//            accountService.creditToAccount(toAccountNo, amount);
//
//            Transaction transaction = new Transaction(fromAccount, toAccount, amount,
//                    TransactionType.DEBIT, TransactionStatus.SUCCESS);
//            em.persist(transaction);
//
//            userTransaction.commit();
//        } catch (Exception e) {
//            try {
//                userTransaction.rollback();
//            } catch (SystemException ex) {
//                throw new RuntimeException(ex);
//            }
//            try {
//                throw new Exception("Transfer failed: " + e.getMessage(), e);
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }
//
//    @Override
//    public List<TransactionDTO> getTransactionsDTOByUserId(Long userId) {
//        return List.of();
//    }
//
//    @Override
//    public List<TransactionDTO> getAllTransactionsDTO() {
//        return List.of();
//    }
//
//    @Override
//    public long countTransactionsToday() {
//        return 0;
//    }
}