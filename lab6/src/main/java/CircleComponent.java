import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Date;
import java.util.HashMap;

public class CircleComponent extends JPanel {
    public boolean isMoving = false;
    Ellipse2D.Double circle;
    public String name1;
    public String direction1;
    public int weight1;
    public String creator;

    //TODO ЧТОБ КРУГЛЯШОЧКИ НОРМАЛЬНО РАБОТАЛИ
    public CircleComponent(int radius, String name1, String direction1, int weight1, String creator) {
        this.name1 = name1;
        this.direction1 = direction1;
        this.weight1 = weight1;
        this.creator = creator;
        circle = new Ellipse2D.Double(0, 0, radius, radius);
        setOpaque(false);
    }

    public String getName1() {
        return name1;
    }

    public String getDirection1() {
        return direction1;
    }

    public int getWeight1() {
        return weight1;
    }

    public String getCreator() {
        return creator;
    }

    public Dimension getPreferredSize() {
        Rectangle bounds = circle.getBounds();
        return new Dimension(bounds.width, bounds.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int j = 0; j < UserPage1.users.size(); j++) {
            if (creator.equals(UserPage1.users.get(j))) {
                g2.setColor(UserPage1.colors.get(j));
            }

        }
        g2.fill(circle);
    }

     void shakeCircle(JPanel jPanel) {
        final Point point = getLocation();
        final int delay = 75;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) {
                    try {

                        setLocation(getX() + 5, getY());
                        jPanel.repaint();
                        Thread.sleep(delay);
                        setLocation(getX() -5, getY());
                        Thread.sleep(delay);
                        jPanel.repaint();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    void moveCircle(JPanel jPanel){

        final Point point = getLocation();
        final int delay = 10;
        isMoving = true;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int  dx = 1;
                int dy = 1;
                while (isMoving) {
                    try {

                        setLocation(getX() + dx, getY() + dy);
                        jPanel.repaint();
                        Thread.sleep(delay);
                        if (getX() >= jPanel.getWidth()-20  || getX() <= -100 || getX()<1) {
                            dx = -dx;
                            Sound.playSound("brosok-snejkom-po-kapotu-avtomobilya (online-audio-converter.com).wav");
                        }
                        if (getY() >= jPanel.getHeight()-20 || getY() <= -100 || getY() < 1) {
                            dy = -dy;
                            Sound.playSound("brosok-snejkom-po-kapotu-avtomobilya (online-audio-converter.com).wav");
                        }
                        } catch(InterruptedException ex){
                            ex.printStackTrace();
                        }

                }
                setLocation(point);
            }
        };
        Thread t = new Thread(r);
        t.start();

    }
}