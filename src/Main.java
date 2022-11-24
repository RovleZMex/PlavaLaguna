import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        DrawingPanel drawingPanel = new DrawingPanel(600,600);
        mainWindow.setSize(600, 600);
        mainWindow.setTitle("Plava Laguna");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.add(drawingPanel, BorderLayout.CENTER);
        mainWindow.setVisible(true);

    }
}