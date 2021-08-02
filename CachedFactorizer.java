@ThreadSafe
public class CachedFactorizer implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    // AtomicLong 대신 long
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    public synchronized long getHits() { return hits; }
    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    // 전체 메소드를 동기화하는 대신 두 개의 짧은 코드블록을 synchoronized 키워드로 보호
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        // 캐시된 결과를 갖고 있는지 검사하는 확인 후 동작 부분
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                // 덤으로 접속 카운터를 다시 넣고, 캐시가 사용된 횟수를 세는 카운터도 추가
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            // 캐시된 입력값과 결과를 새로운 값으로 변경하는 부분
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(resp, factors);
    }

    
}
