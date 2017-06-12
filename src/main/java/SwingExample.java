import javax.swing.*;
import java.awt.*;

/**
 * Created by Yong YAO  on 2017/6/8.
 */
public class SwingExample {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(400,500);
        jFrame.setTitle("Swing example");

        JButton jButton = new JButton();
        jButton.setSize(100,30);
        jButton.setText("click");
        jButton.setBackground(Color.blue);

        jFrame.add(jButton);
        jFrame.setVisible(true);

    }
    }
