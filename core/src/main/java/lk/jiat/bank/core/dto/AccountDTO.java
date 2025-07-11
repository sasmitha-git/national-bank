package lk.jiat.bank.core.dto;

import lk.jiat.bank.core.model.Account;

import java.io.Serializable;

public class AccountDTO implements Serializable {

    private String accountNumber;
    private String accountType;
    private double balance;
    private String userFullName;

    public AccountDTO() {

    }

    public AccountDTO(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType().toString();
        this.balance = account.getBalance();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
