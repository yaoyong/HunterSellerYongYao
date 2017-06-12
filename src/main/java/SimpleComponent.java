import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yong YAO  on 2017/6/8.
 */
public class SimpleComponent extends JPanel{
    JFrame jFrame;
    SimpleComponent(){
        JFrame jFrame = new JFrame();
        jFrame.setSize(400,500);
        jFrame.setTitle("Swing example");

        final JButton jButton = new JButton();
        jButton.setSize(100,30);
        jButton.setBounds(100,100,100,30);
        jButton.setText("click");
        final boolean even= true;
        jButton.setBackground(Color.blue);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(even){
                    jButton.setBackground(Color.red);
                }else{
                    jButton.setBackground(Color.DARK_GRAY);
                }
            }
        });

        jFrame.add(jButton);
        jFrame.setVisible(true);

    }
    public static void main(String[] args) {
        new SimpleComponent();
    }
}
