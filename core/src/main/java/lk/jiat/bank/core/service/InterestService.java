package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.dto.InterestDTO;

import java.time.LocalDateTime;
import java.util.List;

@Remote
public interface InterestService {

    List<InterestDTO> getInterestByAccountIdAndDateRange(Long accountId, LocalDateTime start, LocalDateTime end);



}
