package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    // ProceedingJoinPoint 파라미터는 @Around에서만 사용할 수 있고 사용해야 한다.(타겟을 실행해야 하므로) proceed()는 다음 어드바이스나 타겟을 호출한다.
    @Around("hello.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
        // 추가로 내가 따로 실행할 로직이 없음. @Around 에서의 joinPoint.proceed(); 처럼 실행하는 로직 없음.
    }

    @AfterReturning(value = "hello.aop.order.aop.PointCuts.orderAndService()", returning = "result") // 파라미터의 result와 동일
    public void doReturn(JoinPoint joinPoint, Object result) {
        // result 를 여기서 조작은 할 수 있지만 return 을 실제로 하는 것은 아니므로 @Around 에서처럼 return 값을 변경해서 리턴할 수는 없다.
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.PointCuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
//        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
        log.info("[ex] {} message={}", ex); // 자동으로 throw ex; 가 됨.
    }

    @After(value = "hello.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
