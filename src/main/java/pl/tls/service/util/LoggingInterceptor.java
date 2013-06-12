package pl.tls.service.util;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author tomek
 */
public class LoggingInterceptor {

    @Inject
    private Logger LOG;

    @AroundInvoke
    private Object logMethodExecution(final InvocationContext ic) throws Exception {
        LOG.log(Level.INFO, "*****************************************************");
        LOG.log(Level.INFO, "ENTRY {0}, method: {1}", new Object[]{ic.getTarget().getClass().getName(), ic.getMethod().getName()});
        Object[] parameters = ic.getParameters();
        if (parameters != null) {
            for (Object object : parameters) {
                if (object != null) {
                    LOG.log(Level.INFO, "Param {0}, value: {1}", new Object[]{object.getClass(), object.toString()});
                }

            }
        }
        long startTime = System.nanoTime();
        try {
            return ic.proceed();
        } finally {
            long diffTime = System.nanoTime() - startTime;
            LOG.log(Level.INFO, "RETURN {0} {1}", new Object[]{ic.getTarget().getClass().getName(), ic.getMethod().getName()});
            LOG.log(Level.INFO, "took {0} ms", new Object[]{TimeUnit.MILLISECONDS.convert(diffTime, TimeUnit.NANOSECONDS)});
        }
    }
}
