package lk.jiat.bank.ejb.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lk.jiat.bank.ejb.annotation.Loggable;

@Interceptor
@Loggable
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethod(InvocationContext ctx) throws Exception {
        String methodName = ctx.getMethod().getName();
        System.out.println("Entering: " + methodName);

        try {
            Object result = ctx.proceed();
            System.out.println("Exiting: " + methodName);
            return result;
        } catch (Exception e) {
            System.err.println("Exception in " + methodName + ": " + e.getMessage());
            throw e;
        }
    }
}
