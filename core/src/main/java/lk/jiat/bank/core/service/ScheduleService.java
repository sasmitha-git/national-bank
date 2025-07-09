package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.model.ScheduledTask;

import java.util.List;

@Remote
public interface ScheduleService {

    Long scheduleTransfer(ScheduledTask scheduledTask, long delayInMillis);
    List<ScheduledTask> getActiveTasks(Long userId);
    void cancelScheduledTask(Long taskId);

}
