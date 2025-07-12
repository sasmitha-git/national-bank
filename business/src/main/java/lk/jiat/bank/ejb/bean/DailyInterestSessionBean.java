package lk.jiat.bank.ejb.bean;


import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Timeout;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.Account;
import lk.jiat.bank.core.model.Interest;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.util.Env;
import lk.jiat.bank.ejb.annotation.TimeoutLogger;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@TimeoutLogger
public class DailyInterestSessionBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AccountService accountService;

//    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)

    @Schedule(hour = "23", minute = "59", second = "0", persistent = false)
    @Timeout
    public void calculateDailyInterest() {

        double rate = Env.getDouble("interest.rate.savings");
        List<Account> savingAccounts = accountService.getAllSavingAccounts();

        for (Account account : savingAccounts) {
            double interestAmount = account.getBalance() * (rate/30);

            BigDecimal roundInterestAmount = BigDecimal.valueOf(interestAmount).setScale(2, RoundingMode.HALF_UP);

            Interest interest = new Interest();
            interest.setAccount(account);
            interest.setDate(LocalDateTime.now());
            interest.setBalance(roundInterestAmount.doubleValue());
            em.persist(interest);
        }

        System.out.println("Daily interest calculated..............");
    }

}
