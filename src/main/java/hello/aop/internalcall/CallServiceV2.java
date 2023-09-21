package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext; // 그런데 bean 하나를 꺼내기 위해서 주입받긴 하는데 너무 거대한 객체다.
//
//    public CallServiceV2(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class); // 나중에 꺼낸 것임(지연). proxy임
        CallServiceV2 callServiceV2 = callServiceProvider.getObject(); // 싱글톤이므로 컨테이너 들어가서 다시 꺼내는 것.
        callServiceV2.internal(); // 외부 메서드 호출임. 내부메서드 호출(this.internal()) 이 아니다. 현재는 타켓인 상태이며 proxy 인스턴스인 callServiceV1 를 호출하는 것
    }

    public void internal() {
        log.info("call internal");
    }
}
