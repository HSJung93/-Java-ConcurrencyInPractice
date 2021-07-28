@NotThreadSafe
public class UnsafeCachingFactorizer implements Servlet {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<BigInteger[]>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get())){
            encodeIntoResponse(resp, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }
}

// 인수분해 결과를 곱한 값이 lastNumber에 캐시된 값과 같아야 한다는 불변조건이 항상 성립해야 서블릿이 제대로 동작한다. 
// 변수 하나를 갱신할 땐, 다른 변수도 동일한 단일 연산 작업 내에서 함께 변경되어야 한다: 개별적인 set 메소드는 단일 연산으로 동작하지만 단일 연산 참조 클래스를 쓰더라도 lastNumber과 lastFactors라는 두 개의 값을 동시에 갱신하지는 못한다. 