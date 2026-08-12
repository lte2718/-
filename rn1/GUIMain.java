package rn1;

import static rn1.MAIN.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class GUIMain extends JFrame {
    JTextField seedField1,seedField2, lengthField;
    public static JLabel nowSN0,nowSN1;
    public GUIMain(byte[] setNum,String[] args) {

        setTitle("rn1生成器");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // 窗口关闭前执行清理
                System.out.println("窗口关闭，执行清理...");
                try (OutputStream os = Files.newOutputStream(path,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE)) {
                    os.write(setNum);
                    System.out.println("成功写入配置文件");
                } catch (IOException a) {
                    System.out.println("写入配置文件错误");
                }
                if (setNum[6]==2){
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
                }
                // 退出程序
                System.exit(0);
            }}
        );
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("icon.png").getImage());
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 添加各个子面板
        mainPanel.add(createSettingPanel(setNum),BorderLayout.WEST);
        mainPanel.add(createSeedPanel(args), BorderLayout.NORTH);
        mainPanel.add(createOptionsPanel(setNum), BorderLayout.CENTER);
        mainPanel.add(createResultPanel(setNum), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }//设置面板
    private JPanel createSettingPanel(byte[] setNum){
        JPanel panels=new JPanel(new GridLayout(10,0,0,0));
        JPanel panel=new JPanel(new BorderLayout(20,0));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(43, 45, 48)), "",
                TitledBorder.LEFT, TitledBorder.TOP
        ));
        panel.setBorder(BorderFactory.createTitledBorder(""));
        JButton aButton =new JButton("关于"), settingB =new JButton("设置");
        settingB.setBackground(new Color(183, 183, 183, 255));
        settingB.addActionListener(_->{
            new GUISetting(setNum);
        });
        aButton.addActionListener(_ ->{
            StringBuilder massage= new StringBuilder();
            for (String s : sNB0) {
                massage.append(s).append("\n");
            }
            JOptionPane.showMessageDialog(this,
                    massage.toString(),
                    "关于",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        panels.add(aButton);
        panels.add(settingB);
        panel.add(panels);
        return panel;
    }
    // 创建种子输入面板
    private JPanel createSeedPanel(String[] arg){
        JPanel panel = new JPanel(new GridLayout(4, 0, 0, 0));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLUE),
                "输入"
        ));

        JLabel label1 = new JLabel("种子a,种子b,长度");
        label1.setFont(new Font("宋体", Font.BOLD, 14));
        seedField1 = new JTextField(arg[0]);
        seedField1.setToolTipText("输入a种子");
        seedField2 = new JTextField(arg[1]);
        seedField2.setToolTipText("输入b种子");
        lengthField =new JTextField(arg[2]);
        lengthField.setToolTipText("输入至少长度");
        seedField1.setColumns(100);
        panel.add(label1);
        panel.add(seedField1);
        panel.add(seedField2);
        panel.add(lengthField);
        return panel;
    }
    public ActionListener commonListener(byte[] setNum,byte A) {return e -> {
        byte B = Byte.parseByte(((JButton) e.getSource()).getName());
        setNum[A] = B;
        if (setNum[0] == 1) {
            System.out.println("按钮被点击: "+A+"_"+B);
        }
        switch (A){
            case 4->nowSN0.setText("已选择" + sNB[A][setNum[A]]);
            case 5->nowSN1.setText("已选择" + sNB[A][setNum[A]]);
        }
    };}
    // 创建选项面板
    public JPanel createOptionsPanel(byte[] setNum) {
        JPanel panel = new JPanel(new GridLayout(5,0,2,2));
        panel.setBorder(BorderFactory.createTitledBorder("生成选项"));

        //字符类型选择
        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel setName0=new JLabel(sNA[4]);
        JLabel setName1=new JLabel(sNA[5]);

        JPanel buttonPanel0=new JPanel(new FlowLayout()),buttonPanel1=new JPanel(new FlowLayout());
        JButton[] bt0 = new JButton[sNB4.length],bt1=new JButton[sNB5.length];
        buttonPanel0.add(setName0);
        buttonPanel1.add(setName1);
        for (byte i = 0; i< sNB4.length; i++){
            bt0[i]=new JButton(sNB4[i]);
            bt0[i].setName(Byte.toString(i));
            buttonPanel0.add(bt0[i]);
            bt0[i].addActionListener(commonListener(setNum,(byte) 4));
        }
        for (byte i=0;i<sNB5.length;i++){
            bt1[i]=new JButton(sNB5[i]);
            bt1[i].setName(Byte.toString(i));
            buttonPanel1.add(bt1[i]);
            bt1[i].addActionListener(commonListener(setNum,(byte) 5));
        }
        nowSN0=new JLabel("当前选择"+sNB4[setNum[4]]);
        nowSN1=new JLabel("当前选择"+sNB5[setNum[5]]);
        buttonPanel0.add(nowSN0);
        buttonPanel1.add(nowSN1);
        panel.add(buttonPanel0);
        panel.add(buttonPanel1);
        return panel;
    }

    // 创建结果显示面板
    private JPanel createResultPanel(byte[] setNum) {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("生成的密码"));
        // 密码显示区域
        JTextField passwordField = new JTextField();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("Monospaced", Font.BOLD, 20));
        passwordField.setBackground(new Color(240, 248, 255));
        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearBtn = new JButton("清空剪贴板");
        JButton generateBtn = new JButton("生成密码");
        generateBtn.setBackground(new Color(0, 120, 215, 219));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("宋体", Font.BOLD, 14));
        JButton copyBtn = new JButton("复制");
        String[] v = new String[1];
        copyBtn.addActionListener(_ ->{
            if (v[0]==null) {
                JOptionPane.showMessageDialog(this,
                        "没有可复制的密码！",
                        "错误",
                        JOptionPane.INFORMATION_MESSAGE);
            }else {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(v[0]), null);
            }
        });
        generateBtn.addActionListener(_ -> {
            String seedA;
            String seedB;
            seedA = seedField1.getText();
            seedB = seedField2.getText();
            byte length;
            try {
                length=Byte.parseByte(lengthField.getText());
                try {
                    v[0] = MAIN.generate(setNum, seedA, seedB,length);
                } catch (RuntimeException e) {
                    JOptionPane.showMessageDialog(this,
                            "错误："+e,"错误",JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "错误：长度请使用数字","错误",JOptionPane.INFORMATION_MESSAGE);
            }
            if (setNum[6]>0){
                copyBtn.setEnabled(true);
                copyBtn.doClick();
                copyBtn.setEnabled(false);
                passwordField.setText("密码已自动复制并隐藏");
            }else {
                passwordField.setText(v[0]);
            }
        });
        clearBtn.addActionListener(_ -> Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null));
        buttonPanel.add(generateBtn);
        buttonPanel.add(copyBtn);
        buttonPanel.add(clearBtn);
        panel.add(passwordField, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
//    public static void main(String[] args) {
//        new GUIMain(MAIN.readConfig(path));
//        //SwingUtilities.invokeLater(GUIMain::new);
//    }
}
