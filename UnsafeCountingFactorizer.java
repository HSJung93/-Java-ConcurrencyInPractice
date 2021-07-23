@NotThreadSafe

public class UnsafeCountingFactorizer implements Servlet {
    private long count = 0;
    
    public long getCount() ( return count; )
    
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count; // 단일 연산이 아니다!
        encodeIntoResponse(resp, factors);
    }
}

// 경쟁 조건이 발생한다. 
// 자바는 다중 상속을 지원하지 않는다. 대신 implements(인터페이스)가 등장했다. 
// implements는 부모의 메소드를 반드시 오버라이딩(재정의)해야 한다. 