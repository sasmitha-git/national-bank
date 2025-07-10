package lk.jiat.bank.ejb.bean;

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.model.ScheduleStatus;
import lk.jiat.bank.core.model.ScheduledTask;
import lk.jiat.bank.core.service.AccountService;
import lk.jiat.bank.core.service.ScheduleService;
import lk.jiat.bank.core.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class TimerSessionBean implements ScheduleService {

   @Resource
   private TimerService timerService;

   @EJB
   private TransactionService transactionService;

   @EJB
   private AccountService accountService;

   @PersistenceContext
   private EntityManager em;



    @Override
    public Long scheduleTransfer(ScheduledTask scheduledTask, long delayInMillis) {

        scheduledTask.setStatus(ScheduleStatus.ACTIVE);
        em.persist(scheduledTask);
        em.flush();

        Long taskId = scheduledTask.getId();
        if(taskId == null){
            throw new IllegalStateException("Task id is null");
        }

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(scheduledTask.getId());
        timerService.createSingleActionTimer(delayInMillis, timerConfig);

        return taskId;
    }

    @Timeout
    public void  executeScheduledTransfer(Timer timer) {

        Long taskId = (Long) timer.getInfo();
        if(taskId == null){
            System.out.println("Task id is null");
            return;
        }

        ScheduledTask scheduledTask = em.find(ScheduledTask.class, taskId);

        if(scheduledTask != null && scheduledTask.getStatus() == ScheduleStatus.ACTIVE ) {

            try{
                transactionService.transferFunds(
                        scheduledTask.getFromAccount().getAccountNumber(),
                        scheduledTask.getToAccount().getAccountNumber(),
                        scheduledTask.getAmount()
                );
                scheduledTask.setStatus(ScheduleStatus.COMPLETED);
            } catch (Exception e) {
                scheduledTask.setStatus(ScheduleStatus.CANCELLED);
                e.printStackTrace();
            }
            scheduledTask.setNextExecutionTime(LocalDateTime.now());
            em.merge(scheduledTask);

        }

    }


    @Override
    public List<ScheduledTask> getActiveTasks(Long userId) {
        return em.createNamedQuery("ScheduledTask.findScheduledTaskByUserId",ScheduledTask.class)
                .setParameter("userId", userId)
                .setParameter("status", ScheduleStatus.ACTIVE)
                .getResultList();
    }

    @Override
    public List<ScheduledTask> getAllScheduledTasks(Long userId) {
        return em.createNamedQuery("ScheduledTask.findAllScheduledTaskByUserId",ScheduledTask.class)
                .setParameter("userId",userId).getResultList();
    }

    @Override
    public void cancelScheduledTask(Long taskId) {
        ScheduledTask scheduledTask = em.find(ScheduledTask.class, taskId);
        if(scheduledTask != null && scheduledTask.getStatus() == ScheduleStatus.ACTIVE) {
            scheduledTask.setStatus(ScheduleStatus.CANCELLED);
            em.merge(scheduledTask);
        }

        for(Timer timer : timerService.getTimers()) {
            if(timer.getInfo() instanceof Long && ((Long)timer.getInfo()).equals(taskId)) {
                timer.cancel();
                System.out.println("Timer cancelled"+ taskId);
                break;
            }
        }
    }
}
