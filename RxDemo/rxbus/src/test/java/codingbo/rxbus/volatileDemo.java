package codingbo.rxbus;

import org.junit.Test;

/**
 * Created by bob
 * on 16.11.19.
 */

public class volatileDemo {

    private static volatile boolean sFlag = false;

    @Test
    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    if (sFlag) {
                        break;
                    }
                    System.out.println(1);
                }
                System.out.println("over!");
            }
        }).start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    sFlag = !sFlag;
                }
            }
        }).start();
    }
}
