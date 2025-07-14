package lk.jiat.bank.core.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "balances")
@NamedQueries({
        @NamedQuery(name = "Balance.findBalanceByDate",query = "select b from Balance b order by b.date desc"),
})
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;
    private double balance;
    private String changePercentage;

    public Balance() {

    }

    public Balance(LocalDate date, double balance, String changePercentage) {
        this.date = date;
        this.balance = balance;
        this.changePercentage = changePercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(String changePercentage) {
        this.changePercentage = changePercentage;
    }
}
