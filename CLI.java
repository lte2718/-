package rn1;

import static rn1.MAIN.*;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Scanner;

public class CLI {
    public static void CLIMain(byte[] setNum,String[] arg){
        if (!(Objects.equals(arg[0], ""))){
            if (Objects.equals(arg[2], "")){
                throw new RuntimeException("未输入长度");
            }
            result(generate(setNum,arg[0],arg[1],Byte.parseByte(arg[2])),setNum);
        }
        label:
        while (true){
            String v;
            System.out.println("按E退出，按S进入设置，按N加密数据，否则生成密码");
            String gb;
            gb = new Scanner(System.in).next();
            switch (gb) {
                case "S":
                    CLISetting(setNum, path);
                    continue;
                case "N":
                    System.out.println("n1已运行，第一次输入数据，第二次输入密钥");
                    v = pu1(setNum,new Scanner(System.in).next(),new Scanner(System.in).next());
                    break;
                case "E":
                    try (OutputStream os = Files.newOutputStream(path,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.WRITE)) {
                        os.write(setNum);
                    } catch (IOException e) {
                        System.out.println("写入配置文件错误");
                    }
                    break label;
                default:
                    System.out.println("请第一次输入种子a，第二次输入种子b，第三次输入至少位数<11。");
                    try {
                        v = generate(setNum, new Scanner(System.in).next(), new Scanner(System.in).next(), new Scanner(System.in).nextByte());
                    }catch (Exception e) {
                        System.out.println("错误"+e);v="输入错误";
                    }
                    break;
            }
            result(v,setNum);
        }
    }
    private static void result(String v,byte[] setNum){
        if (setNum[6]==1|setNum[6]==2) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(v), null);
            System.out.println("结果已复制");
        }else {
            System.out.println("结果为\n"+v);
        }
    }
    private static void CLISetting(byte[] setNum, Path path){
        while (true){
            System.out.println("--------------------");
            for(int i=0;i<sNA.length;i++){
                System.out.println((char)iPAN[i]+" ·· "+sNA[i]);
            }
            System.out.println("其他键退出");
            byte[] iPA;
            byte PA=-1;
            iPA=new Scanner(System.in).next().getBytes();
            if(setNum[0]==1){System.out.println("输入A为"+iPA[0]);}
            for (int i=0;i<sNB.length;i++){
                if (iPA[0]==iPAN[i]){PA= (byte) i;}
            }
            if (PA==-1){
                return;
            }
            System.out.println("--------------------");
            for (int i=0;i< sNB[PA].length;i++){
                System.out.println(i+" ·· "+sNB[PA][i]);
            }
            try{System.out.println("其他键直接退出，当前"+sNB[PA][setNum[PA]]);} catch (Exception e) {
                System.out.println("数据异常，请重新设置");
            }
            byte[] iPB;
            iPB=new Scanner(System.in).next().getBytes();
            byte PB= (byte) (iPB[0]-0x30);
            if(setNum[0]==1){System.out.println("输入B为"+PB);}
            if(PB>=0&PB<sNB[PA].length){
                setNum[PA]=PB;
                System.out.println("成功设为"+sNB[PA][PB]);
            }
        }
    }
}
