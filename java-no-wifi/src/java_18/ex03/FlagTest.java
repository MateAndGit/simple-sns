package java_18.ex03;

public class FlagTest {

    public static void main(String[] args) {
        White white = new White();
        Blue blue = new Blue();

        Thread t = new Thread(white);
        Thread t2 = new Thread(blue);

        t.start();
        t2.start();
    }
}
