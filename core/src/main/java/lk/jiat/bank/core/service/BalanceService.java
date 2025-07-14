package lk.jiat.bank.core.service;

import jakarta.ejb.Remote;
import lk.jiat.bank.core.model.Balance;

import java.util.List;

@Remote
public interface BalanceService {

   Balance findLatestBalance();
   void saveBalance(Balance balance);
   List<Balance> findAllBalances();

}
