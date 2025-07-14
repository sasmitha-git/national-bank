package lk.jiat.bank.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.bank.core.dto.InterestDTO;
import lk.jiat.bank.core.model.Interest;
import lk.jiat.bank.core.service.InterestService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class InterestSessionBean implements InterestService {

    @PersistenceContext
    private EntityManager em;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    @Override
    public List<InterestDTO> getInterestByAccountIdAndDateRange(Long accountId, LocalDateTime start, LocalDateTime end) {
        List<Interest> interests = em.createNamedQuery("Interest.findInterestByAccountAndDate", Interest.class)
                .setParameter("accountId",accountId)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .getResultList();

        List<InterestDTO> interestDTOS = new ArrayList<>();
        for (Interest interest : interests) {
            String formattedDate = interest.getDate().format(formatter);

            InterestDTO dto = new InterestDTO(
                    interest.getAccount().getAccountNumber(),
                    formattedDate,
                    interest.getBalance()
            );
            interestDTOS.add(dto);
        }
        return interestDTOS;

    }
}
