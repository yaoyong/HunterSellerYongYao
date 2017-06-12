import javax.swing.*;
import java.awt.*;

/**
 * Created by Yong YAO  on 2017/6/8.
 */
public class TextFiledExample {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Text Field Example");
        jFrame.setSize(400,500);

        JTextField jTextField = new JTextField("90");
        jTextField.setBorder(BorderFactory.createLineBorder(Color.black));
        jTextField.setSize(100,20);

        jFrame.add(jTextField);
        jFrame.setVisible(true);
    }
}
