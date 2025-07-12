package lk.jiat.bank.core.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class TransferFailedException extends RuntimeException{

    public TransferFailedException(String message){
        super(message);
    }
}
