package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.dto.TransactionDTO;


import java.util.List;

@Remote
public interface TransactionService {

    void transferFunds(String fromAccountNo, String toAccountNo, Double amount);

    List<TransactionDTO> getTransactionsDTOByUserId(Long userId);
    List<TransactionDTO> getAllTransactionsDTO();

    long countTransactionsToday();
}
