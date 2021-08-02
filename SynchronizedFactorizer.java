@ThreadSafe
public class SynchronizedFactorizer implements Servlet{
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;

    // service 메소드에 synchronized 키워드를 추가해 한 번에 한 스레드만 실행할 수 있도록 만들었다. 안전하지만 너무 극단적이라 인수분해 서블릿을 여러 클라이언트가 동시에 사용할 수 없고, 이 때문에 응답성이 엄청나게 떨어질 수 있다. 
    public synchronized void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber)){
            encodeIntoResponse(resp, lastFactors);
        }
        else {
            BigInteger[] factors = factor[i];
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }

    
}
