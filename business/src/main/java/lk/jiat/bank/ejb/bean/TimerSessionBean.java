package lk.jiat.bank.ejb.bean;


import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;
import lk.jiat.bank.core.dto.Task;
import lk.jiat.bank.core.service.TaskService;
import lk.jiat.bank.core.service.TransactionService;

import java.io.Serializable;
import java.util.UUID;

@Singleton
public class TimerSessionBean implements TaskService {

    @Resource
    private TimerService timerService;

    @EJB
    private TransactionService transactionService;


    @Override
    public void scheduleTransfer(Task task,long delayInMilliSeconds) {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(task);
        timerService.createSingleActionTimer(delayInMilliSeconds, timerConfig);

        String taskId = UUID.randomUUID().toString();
        System.out.println("Transfer scheduled with Task Id: " + taskId);

    }

    @Timeout
    public void startScheduleTransfer(Timer timer) {
        Serializable info = timer.getInfo();
        if(info instanceof Task) {
            Task task = (Task) info;
            System.out.println("Executing Task: " + task.getFromAccount() + " => " + task.getToAccount());

            try{
                transactionService.transferFunds(task.getFromAccount(), task.getToAccount(), task.getAmount());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Schedule transfer failed");
            }
        }
    }


}
