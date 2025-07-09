package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.dto.Task;

@Remote
public interface TaskService {

    void scheduleTransfer(Task task,long delayInMilliSeconds);
}
