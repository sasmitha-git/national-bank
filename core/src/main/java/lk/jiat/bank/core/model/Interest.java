package lk.jiat.bank.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "interests")
@NamedQueries({
        @NamedQuery(
                name = "Interest.findSumByAccountInterest",
                query = "select sum(i.balance) from Interest i where i.account = :account and i.date >= :startDate and i.date < :endDate"
        ),
        @NamedQuery(
                name = "Interest.findInterestByAccountAndDate",
                query = "select i from Interest i where i.account.id = :accountId and i.date >= :startDate and i.date < :endDate"
        )
})

public class Interest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private LocalDateTime date;
    private double balance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
