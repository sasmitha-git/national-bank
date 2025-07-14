package lk.jiat.bank.ejb.bean;


import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.Balance;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.BalanceService;

import java.time.LocalDate;
import java.util.List;


@Stateless
public class BalanceSessionBean implements BalanceService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AccountService accountService;

    @Override
    public Balance findLatestBalance() {
        return em.createNamedQuery("Balance.findBalanceByDate", Balance.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public void saveBalance(Balance balance) {
        em.persist(balance);
    }

    @Override
    public List<Balance> findAllBalances() {
        return em.createNamedQuery("Balance.findBalanceByDate", Balance.class)
                .setMaxResults(30)
                .getResultList();
    }


    public void recordDailyBankBalance(double todayTotal) {
        String changePercentage ="0.00%";

        try{
            Balance previous = findLatestBalance();
            if(previous != null && previous.getBalance() > 0){
                double change = todayTotal - previous.getBalance();
                double percent = (change/ previous.getBalance())*100;
                changePercentage = String.format("%.2f",percent);
            }

        Balance today = new Balance(LocalDate.now(),todayTotal,changePercentage);
        saveBalance(today);
        }catch (Exception e){
            changePercentage = "Not Available";
            System.out.println(e);
        }
    }

    @Schedule(hour = "*", minute = "*/1", persistent = false)
    private void runDailyBankBalanceUpdate() {
        double todayBalance = accountService.getTotalDeposits();
        recordDailyBankBalance(todayBalance);
    }

}
