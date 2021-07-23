@ThreadSafe
public class StatelessFactorizer implements Servlet{

    // 대부분의 서블릿처럼 상태가 없다. 즉 선언한 변수가 없고 다른 클래스의 변수를 참조하지도 않는다. 
    // 상태 없는 객체에 접근하는 스레드가 어떤 일을 하든 다른 스레드가 수행하는 동작의 정확성에 영향을 미칠 수 없기 때문에 상태 없는 스레드는 항상 스레드 안전하다. (특정 계산을 위한 일시적인 상태는 스레드의 스택에 저장되는 지역 변수에만 저장하고, 실행하는 해당 스레드에서만 접근할 수 있다.)

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }
}

// 서블릿 기반 인수분해 서비스
// 인수분해할 숫자를 서블릿 요청에서 빼내 인수분해하고, 결과를 서블릿 응답에 인코딩해 넣는다. 