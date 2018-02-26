package holiday.web.aspects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by karthik on 2/2/2017.
 */

@Component
@Aspect
public class HolidayLoggerAspect {  // configure logging aspect here

    //@Autowired
    private static Logger logger = LogManager.getLogger(HolidayLoggerAspect.class);

    @Pointcut("within(holiday.web.services..*)")
    public void inServicesLayer() {}

    @Pointcut("within(holiday.web.controllers..*)")
    public void inControllersLayer() {}



    @Around("inServicesLayer() || inControllersLayer()")
    public Object logAroundMethods(ProceedingJoinPoint pjp)
    {
        Object retVal = null;
        String methodName = pjp.getSignature().toString();
        try {
            logger.log(Level.INFO, "Before Executing "+ methodName);
            retVal = pjp.proceed();
            logger.log(Level.INFO, "After Returning "+ methodName);
        } catch (Throwable e) {
            logger.log(Level.ERROR, "After Exception "+ methodName+" "+ e.getMessage());
        }
        logger.log(Level.INFO, "After Finally "+ methodName);
        return retVal;
    }


}
