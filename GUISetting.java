package rn1;
import static rn1.MAIN.sNB;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUISetting extends JFrame {
    JButton[] panelsB=new JButton[20];
    JLabel labelB=new JLabel("选项"),labelA =new JLabel("索引");
    byte A=-1;
    public GUISetting(byte[] setNum){
        setTitle("设置界面");
        setSize(450,600);
        JPanel mainPanel=new JPanel(new GridLayout(0,2));
        mainPanel.add(leftSet(setNum));
        mainPanel.add(rightSet(setNum));
        add(mainPanel,BorderLayout.CENTER);
        setVisible(true);
    }
    private JPanel leftSet(byte[] setNum){
        JPanel leftJPanel=new JPanel(new GridLayout(21,0,1,1));
        ActionListener SA=e->{
            A= Byte.parseByte(((JButton)e.getSource()).getName());
            if (A>=0&A<MAIN.sNA.length){
                for (int i = 0; i< sNB[A].length; i++){
                    panelsB[i].setText(sNB[A][i]);
                    panelsB[i].setVisible(true);
                }
                          for (int i = 19; i>= sNB[A].length; i--){
                    panelsB[i].setText("");
                    panelsB[i].setVisible(false);
                }
            }
            labelB.setText("已选中"+ sNB[A][setNum[A]]);
            labelA.setText("已选中"+MAIN.sNA[A]);
        };

        leftJPanel.add(labelA);
        JButton[] panelsA =new JButton[20];
        //按钮A的创建，初始化
        for (int i=0;i<MAIN.sNA.length&i<20;i++){
            panelsA[i]=new JButton(MAIN.sNA[i]);
            panelsA[i].setName(Integer.toString(i));
            panelsA[i].addActionListener(SA);
            panelsA[i].setVisible(true);
            leftJPanel.add(panelsA[i]);

        }
        for (int i=19;i>=MAIN.sNA.length;i--){
            panelsA[i]=new JButton("");
            panelsA[i].setName(Integer.toString(i));
            panelsA[i].addActionListener(SA);
            panelsA[i].setVisible(false);
            leftJPanel.add(panelsA[i]);
        }
        return leftJPanel;
    }
    private JPanel rightSet(byte[] setNum){
        JPanel rightJPanel=new JPanel(new GridLayout(21,0,1,1));

        ActionListener SB=e->{
            byte B=Byte.parseByte(((JButton)e.getSource()).getName());
            setNum[A] = B;
            labelB.setText("已选中"+ sNB[A][setNum[A]]);
            switch (A){
                case 4->GUIMain.nowSN0.setText("当前选择"+sNB[A][setNum[A]]);
                case 5->GUIMain.nowSN1.setText("当前选择"+sNB[A][setNum[A]]);
            }

        };

        rightJPanel.add(labelB);
//        按钮B的创建
        for (int i=0;i<20;i++){
            panelsB[i]=new JButton("");
            panelsB [i].setName(Integer.toString(i));
            panelsB[i].addActionListener(SB);
            panelsB[i].setVisible(false);
            rightJPanel.add(panelsB[i]);
        }
        return rightJPanel;
    }
    public static void main(String[] args) {
    new GUISetting(rn1.MAIN.readConfig(MAIN.path));
    }
}
