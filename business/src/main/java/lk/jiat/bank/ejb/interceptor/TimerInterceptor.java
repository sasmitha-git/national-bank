package lk.jiat.bank.ejb.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lk.jiat.bank.ejb.annotation.TimeoutLogger;

@TimeoutLogger
@Interceptor
@Priority(1)
public class TimerInterceptor {

    @AroundInvoke
    public Object aroundTimeout(InvocationContext context) throws Throwable {
        System.out.println("TimerInterceptor.aroundTimeout...........");
        return context.proceed();
    }

}
