package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV1 { // 이것을 다시 Bean 으로 등록해야 함

    @Around("execution(* hello.aop.order..*(..))") // pointcut
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처. // 어드바이스
        return joinPoint.proceed(); // 실제 target이 호출
    }
}
