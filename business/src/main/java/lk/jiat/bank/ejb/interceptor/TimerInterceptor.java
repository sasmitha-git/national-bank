package lk.jiat.bank.ejb.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.AroundTimeout;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lk.jiat.bank.ejb.annotation.TimeoutLogger;

@Interceptor
@TimeoutLogger
@Priority(1)
public class TimerInterceptor {

    @AroundTimeout
    public Object aroundTimeout(InvocationContext context) throws Exception {
        System.out.println("TimerInterceptor.aroundTimeout...........");
        return context.proceed();
    }
}
