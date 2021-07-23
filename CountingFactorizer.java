// java.util.concurrent.atomic

@ThreadSafe
public class CountingFactorizer implements Servlet{
    private final AtomicLong count = new AtomicLong(0); //long 에서 AtomicLong으로 카운터에 접근하는 모든 동작이 단일 연산으로 처리.

    public long getCount() ( return count.get(); )
    
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        count.incrementAndGet(); // 
        encodeIntoResponse(resp, factors);
    }

}

// 서블릿 상태가 카운터의 상태이고 카운터가 스레드에 안전하기 때문에 서블릿도 스레드에 안전.
// 1. 카운터 추가 2. 스레드 안전한 클래스를 카운터의 상태를 관리할 때 사용. 
// 상태가 0 -> 1 이 되는 이 예제 말고, 1 -> 2가 되는 상황은 또 다르다. 