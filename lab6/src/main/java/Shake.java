import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;

public class Shake {
    static void shakeButton(JButton button) {
        final Point point = button.getLocation();
        final int delay = 75;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) {
                    try {

                        moveButton(new Point(point.x + 5, point.y),button);
                        Thread.sleep(delay);
                        moveButton(point,button);
                        Thread.sleep(delay);
                        moveButton(new Point(point.x - 5, point.y),button);
                        Thread.sleep(delay);
                        moveButton(point,button);
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    static void moveButton(final Point p, JButton button) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                button.setLocation(p);
            }
        });
    }
}