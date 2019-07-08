import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GraphicIsland extends JComponent {
    Ellipse2D.Double circle;

/*
    //TODO ЧТОБ КРУГЛЯШОЧКИ НОРМАЛЬНО РАБОТАЛИ
    public GraphicIsland(int radius)
    {
        circle = new Ellipse2D.Double(0, 0, radius, radius);
        setOpaque(false);
    }

    public Dimension getPreferredSize()
    {
        Rectangle bounds = circle.getBounds();
        return new Dimension(bounds.width, bounds.height);
    }
*/

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        g2.drawOval(25,25,100,50);
        int wigth = getWidth();
        int h = getHeight();
        g2.drawOval(wigth/2,h/2,100,100);
        super.repaint();
    }
}
