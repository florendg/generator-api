package dev.vultureweb.generator;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@LoggingInterceptor
public class ApiLoggingInterceptor {

    private static final System.Logger LOG = System.getLogger("API_LOGGER");

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        long start = System.nanoTime();
        try {
            return context.proceed();
        } finally {
            long duration = System.nanoTime() - start;
            LOG.log(System.Logger.Level.INFO, "Exiting method: [ " + duration + " ]" + context.getMethod().getName());
        }
    }
}
