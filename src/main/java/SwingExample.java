import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

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

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }

        jFrame.add(jButton);
        jFrame.add(jfc);
        jFrame.setVisible(true);

    }
    }
