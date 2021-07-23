@NotThreadSafe 
public class UnsafeSequence {
    private int value;

    // 유일한 값을 리턴
    public int getNext() {
        return value++;
    } 
}

// 타이밍이 좋지 않은 시점에 두 개의 스레드가 getNext 메소드를 동시에 호출했을 때 같은 값을 얻을 가능성이 있다. 

// value++ 가 값을 읽고, 값에 1을 더하고, 값을 기록하는 3개의 연산을 구성되어 있다. 그런데 스레드 두 개가 동시에 같은 값을 읽고 각자 1을 더할 가능성이 있다. 

// 이 책에서 쓰는 어노테이션 @NotThreadSafe @ThreadSafe

// "경쟁 조건"의 위험성을 보여주는 대표적인 코드. 멀티 스레드 프로그램이 동작하는 모습을 예측하려면 스레드가 서로 간섭하지 않도록 공유된 변수에 접근하는 시점에 적절하게 조율해야 한다. 자바에서는 공유 변수 접급을 조율하기 위한 동기화 수단을 제공한다.