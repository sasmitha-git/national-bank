package lk.jiat.bank.ejb.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.AroundTimeout;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lk.jiat.bank.ejb.annotation.TimeoutLogger;

import java.time.LocalDateTime;

@Interceptor
@TimeoutLogger
@Priority(1)
public class TimerInterceptor {

    @AroundTimeout
    public Object aroundTimeout(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();
        System.out.println("Timer triggered for method: " + methodName + " at " + LocalDateTime.now());

        try {
            Object result = ic.proceed();
            System.out.println("Timer method " + methodName + " completed successfully");
            return result;
        } catch (Exception e) {
            System.out.println("Timer method " + methodName + " failed: " + e.getMessage());
            throw e;
        }
    }
}
