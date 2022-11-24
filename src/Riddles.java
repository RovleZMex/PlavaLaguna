import javax.swing.*;
import java.awt.*;

public class Riddles extends JPanel implements Runnable {
    public int radius = 20;
    public final int x, y;
    private DrawingPanel drawingPanel;

    public Riddles(DrawingPanel drawingPanel, int x, int y) {
        this.drawingPanel = drawingPanel;
        this.x = x;
        this.y = y;
        System.out.println(x + " " + y);
        Thread t1 = new Thread(this);
        t1.run();
    }

    @Override
    public void run() {
        System.out.println("created a new thread");
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(this.x,this.y,this.radius,this.radius);
    }
}
