@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
            return instance;
        }
    }    
}

//늦은 초기화 : 특정 객체가 실제 필요할 때까지 초기화를 미루고 동시에 단 한 번만 초기화 되도록 하기 위한 것이다. 
//getInstance 메소드가 ExpensiveObject이 이미 초기화되었는지 점검하고, 초기화됐다면 해당 객체를 리턴, 초기화되지 않았으면 새 인스턴스를 생성한다. 작업 부담이 큰 초기화 부분을 실행하지 않도록 하기 위함. 
// 스레드 A가 ExpensiveObject 인스턴스를 생성하고 instance 변수에 저장하기 전까지 시간에 스레드가 B가 살펴본다면 위험.
