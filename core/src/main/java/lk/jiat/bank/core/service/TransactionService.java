package lk.jiat.bank.core.service;

import lk.jiat.bank.core.model.Transaction;

import java.util.List;

public interface TransactionService {

    void transferFunds(String fromAccountNo, String toAccountNo, Double amount);

    List<Transaction> getTransactionsByUserId(Long userId);
}
