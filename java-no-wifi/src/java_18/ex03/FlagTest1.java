package java_18.ex03;

public class FlagTest1 {

    public static void main(String[] args) {
        White white = new White();
        Blue blue = new Blue();

        Thread t = new Thread(white);
        Thread t2 = new Thread(blue);

        t.start();
        t2.start();

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000000000; i++) {
                System.out.println("파일 업로드 중입니다.");
            }
        });

        thread.start();
    }
}
