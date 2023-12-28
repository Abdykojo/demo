package transfer.demo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final String LOG_TEMPLATE = "Method '{}' called with args '{}' and return value '{}'. Timing {}.";

    @Around(value ="@annotation(Auditable)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        var args = Arrays.stream(joinPoint.getArgs()).map(object -> object == null ? null : object.toString())
                .collect(Collectors.joining(", "));
        var startTime = Instant.now();
        var result = joinPoint.proceed();
        var duration = Duration.between(startTime, Instant.now());
        log.info(LOG_TEMPLATE, methodName, args, result, duration.toMillis());
        return result;
    }

}
