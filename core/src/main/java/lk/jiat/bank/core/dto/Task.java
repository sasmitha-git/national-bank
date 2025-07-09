package lk.jiat.bank.core.dto;

import java.io.Serializable;

public class Task implements Serializable {


    private String toAccount;
    private String fromAccount;
    private double amount;

    public Task() {
    }

    public Task(String toAccount, String fromAccount, double amount) {
        this.toAccount = toAccount;
        this.fromAccount = fromAccount;
        this.amount = amount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
