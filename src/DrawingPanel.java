import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final Color FIRST_COLOR = new Color(7, 156, 222);
    private final Color SECOND_COLOR = new Color(0, 210, 234);
    private final Color THIRD_COLOR = new Color(27, 67, 93);
    private final Color FOURTH_COLOR = new Color(9, 0, 137);
    private final Color FIRST_WAVE_COLOR = new Color(0,234,212);
    private final Color SECOND_WAVE_COLOR = new Color(0,50,250);
    private final Color THIRD_WAVE_COLOR = new Color(255,137,93);
    private final Color FOURTH_WAVE_COLOR = new Color(252,220,116);
     private int[] firstQuadrant = new int[4]; //[0] = xPos, [1] = yPos, [2] = width, [3] = height
    private int[] secondQuadrant = new int[4];
    private int[] thirdQuadrant = new int[4];
    private int[] fourthQuadrant = new int[4];
    private final Color LINE_COLOR = Color.WHITE;
    private final int STROKE_WIDTH = 6;
    private final Stroke LINE_STROKE = new BasicStroke(STROKE_WIDTH);
    private DrawingPanel drawingPanel = this;
    public List<Circle> circles = new ArrayList<>();
    private int firstLineYPosition;
    private int secondLineXPosition;
    private int windowWidth;
    private int windowHeight;
    private Graphics2D g2;

    public DrawingPanel(final int windowWidth, final int windowHeight) {
        initComponents();

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        //we set the first values to the middle
        firstLineYPosition = windowHeight / 2;
        secondLineXPosition = windowWidth / 2;
        MouseAdapter ma = new MouseAdapter() {
            boolean verticalLineMove = false, horizontalLineMove = false, intersectionLineMove = false;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getY() < firstLineYPosition + STROKE_WIDTH && e.getY() > firstLineYPosition - STROKE_WIDTH &&
                        e.getX() < secondLineXPosition + STROKE_WIDTH && e.getX() > secondLineXPosition - STROKE_WIDTH) {
                    intersectionLineMove = true;
                } else if (e.getY() < firstLineYPosition + STROKE_WIDTH && e.getY() > firstLineYPosition - STROKE_WIDTH) {
                    horizontalLineMove = true;
                } else if (e.getX() < secondLineXPosition + STROKE_WIDTH && e.getX() > secondLineXPosition - STROKE_WIDTH) {
                    verticalLineMove = true;
                } else {
                    //add ripples
                    Thread t = new Thread(new RunnableTest(drawingPanel, e.getX(), e.getY()));
                    t.start();
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (intersectionLineMove) {
                    firstLineYPosition = e.getY();
                    secondLineXPosition = e.getX();
                    repaint();
                } else if (horizontalLineMove) {
                    firstLineYPosition = e.getY();
                    repaint();
                } else if (verticalLineMove) {
                    secondLineXPosition = e.getX();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                verticalLineMove = false;
                horizontalLineMove = false;
                intersectionLineMove = false;
            }
        };
        addMouseMotionListener(ma);
        addMouseListener(ma);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        windowWidth = this.getWidth();
        windowHeight = this.getHeight();
        drawRectangles(firstLineYPosition, secondLineXPosition, g2);
        g2.setStroke(LINE_STROKE);
        g2.setColor(LINE_COLOR);
        //draw horizontal line
        g2.drawLine(0, firstLineYPosition, windowWidth, firstLineYPosition);
        //draw vertical line
        g2.drawLine(secondLineXPosition, 0, secondLineXPosition, windowHeight);
        //draw circles
        g2.setStroke(new BasicStroke(2));
        for (Circle circle : circles) {
            g2.setColor(Color.BLACK);

            //check quadrant
            if(circle.getX() < secondLineXPosition && circle.getY() < firstLineYPosition){
                g2.setColor(FIRST_WAVE_COLOR);
            }else if(circle.getX() > secondLineXPosition && circle.getY() < firstLineYPosition){
                g2.setColor(SECOND_WAVE_COLOR);
            }else if(circle.getX() < secondLineXPosition && circle.getY() > firstLineYPosition){
                g2.setColor(THIRD_WAVE_COLOR);
            }else{
                g2.setColor(FOURTH_WAVE_COLOR);
            }

            int radius = circle.getRadius();
            g2.drawOval(circle.getX() - (radius / 2), circle.getY() - (radius / 2), radius, radius);
        }
    }

    private void drawRectangles(final int firstLineYPosition, final int secondLineXPosition, final Graphics2D g2) {
        calculateQuadrants(firstLineYPosition, secondLineXPosition);
        g2.setColor(FIRST_COLOR);
        g2.fillRect(firstQuadrant[0], firstQuadrant[1], firstQuadrant[2], firstQuadrant[3]);
        g2.setColor(SECOND_COLOR);
        g2.fillRect(secondQuadrant[0], secondQuadrant[1], secondQuadrant[2], secondQuadrant[3]);
        g2.setColor(THIRD_COLOR);
        g2.fillRect(thirdQuadrant[0], thirdQuadrant[1], thirdQuadrant[2], thirdQuadrant[3]);
        g2.setColor(FOURTH_COLOR);
        g2.fillRect(fourthQuadrant[0], fourthQuadrant[1], fourthQuadrant[2], fourthQuadrant[3]);
        validate();
        repaint();
    }

    private void calculateQuadrants(final int firstLineYPosition, final int secondLineXPosition) {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    firstQuadrant[i] = 0;
                    secondQuadrant[i] = secondLineXPosition;
                    thirdQuadrant[i] = 0;
                    fourthQuadrant[i] = secondLineXPosition;
                    break;
                case 1:
                    firstQuadrant[i] = 0;
                    secondQuadrant[i] = 0;
                    thirdQuadrant[i] = firstLineYPosition;
                    fourthQuadrant[i] = firstLineYPosition;
                    break;
                case 2:
                    firstQuadrant[i] = secondLineXPosition;
                    secondQuadrant[i] = windowWidth;
                    thirdQuadrant[i] = secondLineXPosition;
                    fourthQuadrant[i] = windowWidth;
                    break;
                case 3:
                    firstQuadrant[i] = firstLineYPosition;
                    secondQuadrant[i] = firstLineYPosition;
                    thirdQuadrant[i] = windowHeight;
                    fourthQuadrant[i] = windowHeight;
                    break;
            }
        }
    }
}
