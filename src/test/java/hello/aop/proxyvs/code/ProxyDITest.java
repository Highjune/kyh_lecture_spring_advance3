package hello.aop.proxyvs.code;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시 사용. DI 예외 발생한다. 옵션을 넣지 않으면 스프링은 기본적으로 CGLIB 를 사용한다.
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 사용. DI 가능함
@SpringBootTest
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    // 그래서 JDK 동적 프록시를 사용하면 항상 인터페이스에만 의존관계를 사용해야 한다. 구체클래스에 의존관계 사용 안된다.
    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
