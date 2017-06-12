import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yong YAO  on 2017/6/8.
 */
public class JComboBoxExample {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(400,500);
        JLabel jLabel = new JLabel();
        jLabel.setText("Select country");
        jLabel.setBounds(100,100,100,30);

        String[] countries = {"US","UK","CA"};
        final JComboBox jComboBox = new JComboBox(countries);
        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(jComboBox.getSelectedItem());
            }
        });
        jFrame.add(jComboBox);
        jFrame.setVisible(true);
    }
}
