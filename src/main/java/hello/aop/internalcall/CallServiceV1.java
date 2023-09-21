package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    // 참고로 생성자로 주입받으면 안됨. 순환참조 문제. 나 자신이 생성되지도 않았는데 나 자신을 생성받는 것이므로.
    // setter로 주입하면 됨. 생성을 다 한 후에 setter로 주입하는 단계가 다 분리되어 있음
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}", callServiceV1.getClass()); // 프록시 호출임
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메서드 호출임. 내부메서드 호출(this.internal()) 이 아니다. 현재는 타켓인 상태이며 proxy 인스턴스인 callServiceV1 를 호출하는 것
    }

    public void internal() {
        log.info("call internal");
    }
}
