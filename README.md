# 자바 병렬 프로그래밍 예제 코드 실습 저장소 

## 프로젝트 설명
브라리언 게츠 저 자바 병렬 프로그래밍의 예제 코드를 실습하는 저장소입니다. 예제 코드 실습을 통하여 병렬 프로그래밍의 핵심 개념들을 이해하고 자신의 프로젝트에 자바를 통한 병렬 프로그래밍을 구현하고자 합니다. 병렬 프로그래밍과는 별개로도 책에 나온 자바 프로그래밍의 핵심 개념들을 다시금 정리하면서 공부하고 있습니다.

## 환경 및 세팅
* java 8

## 코드 및 사용법 
* 책의 예제 코드를 구현하였으며 @NotThreadSafe와 @ThreadSafe의 어노테이션으로 Concurrency를 체크할 수 있습니다.

## 내용 요약

* 1장 개요
  * 프로세스 : 각자가 서로 격리된 채로 독립적으로 실행되는 프로그램
  * 스레드 : 가벼운 프로세스. 현대 운영체제의 대부분은 프로세스가 아니라 스레드를 기본 단위로 CPU 자원의 스케줄을 정한다. 의도적으로 조율하지 않는 한 하나의 스레드는 다른 스레드와 상관 없이 비동기적으로 실행된다. 스레드는 자신이 포함된 프로세스의 메모리 주소 공간을 공유하기 때문에 한 프로세스 내 모든 스레드는 같은 변수에 접근하고 같은 힙에 객체를 할당한다. 하지만 각 스레드는 각기 별도의 프로그램 카운터, 스택, 지역 변수를 갖는다. 
    * 멀티스레드 프로그램에선 스레드 하나가 I/O가 끝나길 기다리는 동안 다른 스레드가 계속 실행될 수 있다. 즉 I/O 때문에 대기 상태에 들어가는 동안에도 다른 스레드는 동작할 수 있기 때문에 애플리케이션이 계속 실행된다. 
  * 모든 자바 프로그램은 기본적으로 스레드를 사용한다. JVM을 시작시키면 main 메소드를 실행할 주 스레드 뿐 아니라 가비지 컬렉션이나 객체 종료와 같은 JVM 내부 작업을 담당할 스레드도 생성한다. 가비지 컬렉터는 보통 하나 또는 두 개 이상의 전용 스레드에서 실행된다. 
    * AWT와 스윙 사용자 인터페이스 프레임웍은 사용자 인터페이스의 이벤트를 관리할 스레드를 생성한다.
    * Timer는 대기 중인 작업을 실행할 스레드를 생성한다. 
    * 서블릿이나 RMI 같은 컴포넌트 프레임웍 역시 스레드를 관리하는 풀을 여러 개 생성하고 이 스레드를 사용해 컴포넌트의 메소드를 호출한다. 
      * 서블릿과 JSP
      * 원격 메소드 호출(Remote Method Invocation)
    * 프레임워크 : 다수의 애플리케이션을 제작하는 것에 목적을 두고 애플리케이션마다 들어가는 필수적인 코드와 알고리즘, 객체와 컴포넌트를 예비적으로 구성해놓은 것. 
    * 컴포넌트 : 표준으로 정의된 컨테이너 규약 하에서 독립적으로 사용할 수 있는 소프트웨어 모듈.

* 2장 스레드 안정성
  * 객체의 상태는 인스턴스나 static 변수 같은 상태 변수에 저장된 객체의 데이터다. 스레드에 안전한 코드를 작성하는 것은 근본적으로는 상태, 특히 공유되고 변경할 수 있는 상태에 대한 접근을 관리하는 것이다.
  * 스레드가 하나 이상의 상태 변수에 접근하고 그 중 하나라도 변수에 값을 쓰면, 해당 변수에 접근할 때 관련된 모든 스레드가 동기화를 통해 조율해야 한다. 자바에서 동기화를 위한 기본 수단은 synchronized 키워드로서 배타적인 락을 통해 보호 기능을 제공한다. 하지만 volatile 변수, 명시적 락, 단일 연산 변수 atomic variable를 사용하는 경우에도 '동기화'라는 용어를 사용한다. 
    * 해당 상태 변수를 스레드 간에 공유하지 않는다.
    * 해당 상태 변수를 변경할 수 없도록 만든다.
    * 해당 상태 변수에 접근할 땐 언제나 동기화를 사용한다. 
  * 캡슐화: 객체의 속성과 행위를 하나로 묶고, 실제 구현 내용 일부를 외부에 감추어 은닉한다. 
  * 2.1 스레드 안정성이란?
    * 선언한 변수가 없고 다른 클래스의 변수도 참조하지 않는 상태 없는 서블릿: `StatelessFactorizer.java`
  * 2.2 단일 연산이 아니여서 스레드 안전하지 않은 경우: `UnsafeCountingFactorizer.java`
    * 경쟁 조건은 상대적인 시점이나 또는 JVM이 여러 스레드를 교차해서 실행하는 상황에 따라 계산의 정확성이 달라질 때 나타난다.
    * 경쟁 조건을 피하려면 변수가 수정되는 동안 다른 스레드가 해당 변수를 사용하지 못하도록 막을 방법이 있어야 하며, 이런 방법으로 보호해두면 특정 스레드에서 변수를 수정할 때 다른 스레드는 수정 도중이 아닌 수정 이전이나 이후에만 상태를 읽거나 변경을 가할 수 있다. 
    * 작업 A를 실행 중인 스레드 관점에서 다른 스레드가 작업 B를 실행할 때 작업 B가 모두 수행됐거나 또는 전혀 수행되지 않은 두가지 상태로만 파악된다면 작업 A의 눈으로 볼 때 작업 B는 단일 연산이다. 
    * 점검 후 행동과 읽고 수정하고 쓰기 같은 일련의 동작을 복합 동작이라고 한다. 즉 스레드에 안전하기 위해서는 전체가 단일 연산으로 실행돼야 하는 일련의 동작을 지칭한다. 
    * 늦은 초기화시 경쟁 조건으로 제대로 동작하지 않는 경우: `LazyInitRace.java`
  * 2.3 락
    * 암묵적인 락: `SynchronizedFactorizer.java`
      * service 메소드 전체를 동기화.
      * 자바에서는 단일 연산 특성을 보장하기 위해 synchronized라는 구문으로 사용할 수 있는 락을 제공한다. synchronized 구문은 락으로 사용될 객체의 참조 값과 해당 락으로 보호하려는 코드 블록으로 구성된다. 메소드 선언 부분에 synchronized 키워드를 지정하면 메소드 내부의 코드 전체를 포함하면서 메소드가 포함된 클래스의 인스턴스를 락을 사용하는 synchronized 블록을 간략하게 표현할 것으로 볼 수 있다. 
      * 자바에 내장된 락을 암묵적인 락(intrinsic lock) 혹은 모니터락(monitor lock)이라고 한다. 락은 스레드가 synchronized 블록에 들어가기 전에 자동으로 확보되며 정상적으로든 예외가 발생해서든 해당 블록을 벗어날 때 자동으로 해제된다. 해당 락으로 보호된 synchronized 블록이나 메소드에 들어가야만 암묵적인 락을 확보할 수 있다. 
      * 자바에서 암묵적인 락은 상호배제 락으로 동작한다. 즉 한 번에 한 스레드만 특정 락을 소유할 수 있다. 스레드 B가 가지고 있는 락을 스레드 A가 얻으려면, A는 B가 해당 락을 놓을 때까지 기다려야 한다. 
      * 한 스레드가 synchronized 블록을 실행 중이라면 같은 락으로 보호되는 synchronized 블록에 다른 스레드가 들어와 있을 수 없다.
    * 재진입성: 확보 요청 단위가 아닌 스레드 단위로 락을 얻는다는 것을 의미
      * 암묵적인 락은 재진입 가능하기 때문에 특정 스레드가 자기가 이미 획득한 락을 다시 확보할 수 있다.
      * 스레드가 해제된 락을 확보하면 JVM이 락에 대한 소유 스레드를 기록하고 확보 횟수를 1로 지정한다. 같은 스레드가 락을 다시 얻으면 횟수를 증가시키고, 소유한 스레드가 synchronized 블록 밖으로 나가면 횟수를 감소시킨다.
  * 2.4 락으로 상태 보호하기
    * 락은 자신이 보호하는 코드 경로에 여러 스레드가 순차적으로 접근하도록 한다. 
    * 경쟁 조건을 피하려면 접속 카운터를 증가시키거나(CRU) 늦게 초기화하는(확인 후 행동) 경우 하나의 공유된 상태에 대한 복합 동작을 단일 연산으로 만들어야 한다. 
    * 하지만 단순히 복합 동작 부분을 synchronized 블록으로 감싸는 것으로는 부족하다. 
      * 특정 변수에 대한 접근을 조율하기 위해 동기화할 때는 해당 변수에 접근하는 모든 부분을 동기화해야 한다. 
      * 변수에 대한 접근을 조율하기 위해 락을 상용할 땐 해당 변수에 접근하는 모든 곳에서 반드시 같은 락을 사용해야 한다. 
    * 락을 활용함에 있어 일반적인 사용 예는 먼저 모든 변경 가능한 변수를 객체 안에 캡슐화하고, 해당 객체의 암묵적인 락을 사용해 캡슐화한 변수에 접근하는 모든 코드 경로를 동기화함으로써 여러 스레드가 동시에 접근하는 상태에서 내부 변수를 보호하는 방법이다. 
  * 2.5. 활동성과 성능: `CachedFactorizer.java`
    * 전체 메소드를 동기화하는 대신 두 개의 짧은 코드 블록을 synchronized 키워드로 보호.

 * 3장 객체 공유
   * 3.1 가시성: `NoVisibility.java`
     * 3.1.1 스테일 데이터

