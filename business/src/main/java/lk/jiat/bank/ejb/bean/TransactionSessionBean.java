package lk.jiat.bank.ejb.bean;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.dto.TransactionDTO;
import lk.jiat.bank.core.exception.TransferFailedException;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.Transaction;
import lk.jiat.bank.core.model.TransactionStatus;
import lk.jiat.bank.core.model.TransactionType;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class TransactionSessionBean implements TransactionService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AccountService accountService;

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void transferFunds(String fromAccountNo, String toAccountNo, Double amount) {


        if (fromAccountNo == null || toAccountNo == null) {
            throw new TransferFailedException("Invalid account number");
        }

        if(fromAccountNo.equals(toAccountNo)){
            throw new TransferFailedException("Cannot transfer to the same account");
        }

        if(amount <= 50){
            throw new TransferFailedException("Amount must be greater than Rs.50.0");
        }


        Account sourceAccountNo = accountService.getAccountByAccountNumber(fromAccountNo);
        Account destinationAccountNo = accountService.getAccountByAccountNumber(toAccountNo);

        if(sourceAccountNo.getBalance() < amount){
            throw new TransferFailedException("Insufficient funds");
        }

        accountService.debitFromAccount(fromAccountNo, amount);
        accountService.creditToAccount(toAccountNo, amount);

        Transaction transaction = new Transaction(
                sourceAccountNo,
                destinationAccountNo,
                amount,
                TransactionType.DEBIT,
                TransactionStatus.SUCCESS
        );
        em.persist(transaction);
    }


    @RolesAllowed({"ADMIN","CUSTOMER"})
    public List<TransactionDTO> getTransactionsDTOByUserId(Long userId) {
        List<Transaction> transactions = em.createNamedQuery("Transaction.findTransactionByUserId", Transaction.class)
                .setParameter("userId", userId).getResultList();

        List<TransactionDTO> dtoList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            String fromAccountNumber = transaction.getFromAccount().getAccountNumber();
            String toAccountNumber = transaction.getToAccount().getAccountNumber();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = transaction.getTimestamp().format(formatter);

            TransactionDTO dto = new TransactionDTO(
                    fromAccountNumber,
                    toAccountNumber,
                    transaction.getAmount(),
                    transaction.getTransactionType().toString(),
                    transaction.getStatus().toString(),
                    formattedDate
            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public List<TransactionDTO> getAllTransactionsDTO() {
      List<Transaction>  transactions = em.createNamedQuery("Transaction.findAllTransactions", Transaction.class)
                .getResultList();

      List<TransactionDTO> dtoList = new ArrayList<>();

      for (Transaction transaction : transactions) {

          TransactionDTO dto = new TransactionDTO();
          dto.setTimestamp(String.valueOf(transaction.getTimestamp()));
          dto.setFromAccountNumber(transaction.getFromAccount().getAccountNumber());
          dto.setToAccountNumber(transaction.getToAccount().getAccountNumber());
          dto.setAmount(transaction.getAmount());
          dto.setTransactionType(transaction.getTransactionType().toString());
          dto.setStatus(transaction.getStatus().toString());

          dtoList.add(dto);
      }

        return dtoList;
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public long countTransactionsToday() {
        LocalDate today = LocalDate.now();

        return em.createNamedQuery("Transaction.countByTodayTransaction", Long.class)
                .setParameter("CURRENT_DAY", today)
                .getSingleResult();
    }


}
