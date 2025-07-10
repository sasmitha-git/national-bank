package lk.jiat.bank.ejb.bean;

import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.*;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.ejb.annotation.TimeoutLogger;

import java.time.LocalDate;
import java.util.List;

@Singleton
@TimeoutLogger
public class MonthlyInterestSessionBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AccountService accountService;

//    @Schedule(dayOfMonth = "Last", hour = "", minute = "", second = "", persistent = false)
    @Schedule(hour = "*", minute = "*/1", second = "0", persistent = false)
    @Timeout
    public void creditMonthlyInterest() {
        List<Account> savingAccounts = accountService.getAllSavingAccounts();

        for (Account account : savingAccounts) {

            LocalDate firstDate = LocalDate.now().withDayOfMonth(1);
            LocalDate firstDayNextMonth = firstDate.plusMonths(1);

            Double totalInterest = em.createNamedQuery("Interest.findSumByAccountInterest", Double.class)
                    .setParameter("account",account)
                    .setParameter("startDate",firstDate.atStartOfDay())
                    .setParameter("endDate", firstDayNextMonth.atStartOfDay())
                    .getSingleResult();
            if(totalInterest != null && totalInterest > 0){
                accountService.creditToAccount(account.getAccountNumber(), totalInterest);

                Account systemAccount = accountService.getAccountByAccountNumber("NBBANK0000");
                if (systemAccount == null) {
                    throw new IllegalStateException("System account not found: NBBANK0000");
                }

                Transaction interestTransaction = new Transaction(
                        systemAccount,
                        account,
                        totalInterest,
                        TransactionType.INTEREST,
                        TransactionStatus.SUCCESS
                );
                em.persist(interestTransaction);
            }
        }
        System.out.println("MonthlyInterestSessionBean .........................");

    }

}
