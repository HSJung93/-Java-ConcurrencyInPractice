public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) 
                Thread.yield();
            System.out.println(number);
        }
    }

    // 메인 스레드에서 number 변수와 ready 변수에 지정한 값을 읽기 스레드에서 사용할 수 없는 상황
    // 두 변수에 동기화 기법이 필요하다.  
    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
    
}
