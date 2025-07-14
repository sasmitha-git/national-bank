package lk.jiat.bank.core.dto;

import java.io.Serializable;

public class InterestDTO implements Serializable {

    private String accountNumber;
    private String dateFormatted;
    private double balance;

    public InterestDTO() {

    }

    public InterestDTO(String accountNumber, String dateFormatted, double balance) {
        this.accountNumber = accountNumber;
        this.dateFormatted = dateFormatted;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
