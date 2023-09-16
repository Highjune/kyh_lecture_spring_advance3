package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // hello.aop.order 패키지와 하위 패키지
    // 이렇게 분리하면 다른 @Around에서도 이것을 가져다가 사용할 수 있다.
    // 메서드명을 통해서 의미 부여 가능
    // 반환타입은 void 여야 한다.
    // 꼭 private일 필요는 없다. public 으로 하면 다른 @Aspect 에서도 사용가능
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} // pointcut signature.

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
