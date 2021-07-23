@NotThreadSafe

public class UnsafeCountingFactorizer implements Servlet {
    private long count = 0;
    
    public long getCount() ( return coutn; )
    
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count; // 단일 연산이 아니다!
        encodeIntoResponse(resp, factors);
    }
}

// 경쟁 조건이 발생한다. 