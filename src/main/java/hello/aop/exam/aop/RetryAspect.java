package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

//    @Around("@annotation(hello.aop.exam.annotation.Retry)")
    @Around("@annotation(retry)") // 타입을 받으면 위의 경로 + 타입이 대체가 됨
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry); // 실패하면 catch 문에서 예외 저장 후 다시 시도(joinPoint.
                return joinPoint.proceed(); // 성공하면 리턴하고 끝
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder; // 여러번 시도 후 결국 성공 못했을 경우에, 마지막에 터진 예외를 던짐.

    }

}
