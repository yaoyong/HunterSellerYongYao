/**
 * Created by Yong Yao} on 2017/6/13.
 */
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WindowReader extends JFrame implements ActionListener {
    JFileChooser fileDialog; // 文件对话框
    JMenuBar menubar; // 菜单条
    JMenu menu; // 菜单
    JMenuItem itemSave, itemOpen; // 菜单项
    JTextArea text; // 文本区
    BufferedReader in; // 缓冲读取流
    FileReader fileReader; // 文件读取流
    BufferedWriter out; // 缓冲写入流
    FileWriter fileWriter; // 文件写入流

    WindowReader() {
        init();
        setSize(300, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        text = new JTextArea(10, 10);
        text.setFont(new Font("楷体_gb2312", Font.PLAIN, 28));
        add(new JScrollPane(text), BorderLayout.CENTER);

        menubar = new JMenuBar();
        menu = new JMenu("文件");
        itemSave = new JMenuItem("保存文件");// 初始化菜单项
        itemOpen = new JMenuItem("打开文件");

        itemSave.addActionListener(this); // 注册监视器
        itemOpen.addActionListener(this);

        menu.add(itemSave);// 菜单中加入菜单项
        menu.add(itemOpen);

        menubar.add(menu); // 菜单条中加入菜单
        setJMenuBar(menubar);
        fileDialog = new JFileChooser();
    }

    // 监听事件
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemSave) {
            int state = fileDialog.showSaveDialog(this);
            if (state == JFileChooser.APPROVE_OPTION) {
                try {
                    // 保存文件
                    File dir = fileDialog.getCurrentDirectory();
                    String name = fileDialog.getSelectedFile().getName();
                    File file = new File(dir, name);
                    fileWriter = new FileWriter(file);
                    out = new BufferedWriter(fileWriter);
                    out.write(text.getText());
                    out.close();
                    fileWriter.close();
                } catch (IOException exp) {
                }
            }
        } else if (e.getSource() == itemOpen) {
            int state = fileDialog.showOpenDialog(this);
            if (state == JFileChooser.APPROVE_OPTION) {
                text.setText(null);
                try {
                    // 打开文件
                    File dir = fileDialog.getCurrentDirectory();
                    String name = fileDialog.getSelectedFile().getName();
                    File file = new File(dir, name);
                    fileReader = new FileReader(file);
                    in = new BufferedReader(fileReader);
                    String s = null;
                    AmazonDemo sellHunter = new AmazonDemo();
                    while ((s = in.readLine()) != null) {
                        String ISBN = AmazonDemo.getStrignNum(s);
                        String condition = null;
                        if(s.contains("New")){
                            condition = "New";
                        }else condition = "Used";
                        sellHunter.sellerHunter(ISBN,condition);
                        String hunting = sellHunter.sellerCarwler();
                        text.append(s + hunting + "\n");
                    }
                    in.close();
                    fileReader.close();
                } catch (IOException exp) {
                }
            }
        }
    }
    public static void main(String args[]) {
        WindowReader win = new WindowReader();
        win.setTitle("seller Hunter by Yong Yao");
        //win.setBackground(java.awt.Color.GREEN);
        win.setBounds(80, 90, 600, 700);
    }
}
