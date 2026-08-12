package rn1;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class MAIN {
     static String[] sNB0={"version2.26.0711,windows","made by LTe"};
    static String[] sNB1={"中文","其","实","没","有","任何","作用"},sNB2={"GUI","CLI"};
    static String[] sNB3={"偏移","反偏移"};
     static String[] sNB4={"字符","数字字母","数字"};
    static String[] sNB5={"数字","字符"};
    static String[] sNB6={"全手动","复制结果不清除","复制结果退出后清除"};
     static String[] sNA={"关于","语言","界面","加密方法","生成输出类型","生成输入类型","所有结果处理"};
     static String[][] sNB={sNB0,sNB1,sNB2,sNB3, sNB4,sNB5,sNB6};
    static byte[] iPAN={'A','0','1','2','3','4','5'};
    static Path path= Paths.get(System.getProperty("user.home")+"\\AppData\\Local\\r1data");
    static String[] IArgs= {"","","",""};
    static String[] SArgs= {"",""};
    //历史2,3,20,字符,输入数字dIf)M"42\,
    public static byte[] readConfig(Path path){
        byte[] result;
        try {
            result = Files.readAllBytes(path);
            if (result.length< sNA.length){
                System.out.println("配置文件异常，已初步修复，进入设置全面修复。");
                result =new byte[sNA.length];
            }else {
                System.out.println("成功读取配置");
            }
        } catch (IOException e) {
            System.out.println("读取配置文件错误"+e);
            result =new byte[sNA.length];
        }
        return result;
    }
    public static void main(String[] args){
        byte[] setNum=readConfig(path);

        if (args.length>0) {
            Args(args,setNum);
        }
        if (setNum[2]==1){
            CLI.CLIMain(setNum,IArgs);
        }else {
            new GUIMain(setNum,IArgs);
        }
        if (setNum[6]==2){
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
        }
    }

    private static void Args(String[] args,byte[] setNum) {
        int ArgsLength =args.length;
        for (int i = 0; i< ArgsLength; i++){
            if (args[i].charAt(0)=='G' & args[i].charAt(1)=='{'){
                int No=0;
                IArgs= new String[]{"", "", "", ""};
                for (int ii = 2; ii< args[i].length()-1; ii++){
                    char add= args[i].charAt(ii);
                    if (add==','){
                        No++;
                        if (No>= IArgs.length){
                            throw new RuntimeException("输入参数格式不对，{ }内不超过"+ IArgs.length+"组");
                        }
                    }else {
                        IArgs[No]+=add;
                    }
                }
                byte a=-1;
                if (setNum[0]==1) {
                    System.out.println("IArgs="+Arrays.toString(IArgs));
                }
                try {
                    a=Byte.parseByte(IArgs[3]);
                    if (a>=sNB4.length|a<0){
                        throw new RuntimeException("输出类型序号最大为"+(sNB4.length-1));
                    }
                } catch (NumberFormatException _) {
                    for (byte ii = 0; ii <sNB4.length; ii++){
                        if (Objects.equals(IArgs[3], sNB4[ii])){
                            a= ii;
                        }
                    }
                }
                System.out.println("成功传入");
                if (a!=-1) {
                    setNum[4]=a;
                }
            } else if (args[i].charAt(0)=='S'& args[i].charAt(1)=='{') {
                int No=0;
                SArgs= new String[]{"", ""};
                for (int ii = 2; ii< args[i].length()-1; ii++){
                    char add= args[i].charAt(ii);
                    if (add==','){
                        No++;
                        if (No>= SArgs.length){
                            throw new RuntimeException("设置参数格式不对，{ }内不超过"+ SArgs.length+"组");
                        }
                    }else {
                        SArgs[No]+=add;
                    }
                }
                if (setNum[0]==1) {
                    System.out.println("SArgs="+Arrays.toString(SArgs));
                }
                byte setA=-1,setB=-1;
                if (Objects.equals(SArgs[0],"")|Objects.equals(SArgs[1],"")){
                    throw new RuntimeException("缺少数据");
                }
                try {
                    setA=Byte.parseByte(SArgs[0]);
                    if (setA>=sNA.length|setA<0){
                        throw new RuntimeException("设置项序号最大为"+(sNA.length-1));
                    }
                } catch (NumberFormatException _) {
                    for (byte ii=0;ii<sNA.length;ii++){
                        if (Objects.equals(SArgs[0], sNA[ii])){
                            setA=ii;
                        }
                    }
                }
                if (setA==-1){
                    throw new RuntimeException("未知设置类型第1位");
                }
                try {
                    setB=Byte.parseByte(SArgs[1]);
                    if (setB>=sNB[setA].length|setB<0){
                        throw new RuntimeException("设置项"+sNA[setA]+"的序号最大为"+(sNB[setA].length-1));
                    }
                } catch (NumberFormatException _) {
                    for (byte ii=0;ii<sNB[setA].length;ii++){
                        if (Objects.equals(SArgs[1], sNB[setA][ii])){
                            setB=ii;
                        }
                    }
                }
                if (setB==-1){
                    throw new RuntimeException("未知设置类型第2位");
                }else {
                    setNum[setA]=setB;
                    System.out.println("成功设置"+sNA[setA]+"为"+sNB[setA][setB]);
                }
            }
        }
    }
    public static String generate(byte[] setNum,String iSa,String iSb,byte length)throws RuntimeException{
        long a = 0,b = 0;
        byte lengthNa,lengthNb;
        if (setNum[4]==2) {
            if (length <1| length >20){
                throw new RuntimeException("长度需大于0,不能太长");
            }
        }
        if (Objects.equals(iSa, "")|Objects.equals(iSb, "")| Objects.equals(iSa, iSb)){
            throw new RuntimeException("种子不能为空或相等");
        }
        if (setNum[5]==1){
            byte[] ia,ib;
            ia=iSa.getBytes();
            ib=iSb.getBytes();
            for (byte value : ia) {
                a = a * 256;
                a += value;
            }for (byte value : ib) {
                b = b * 256;
                b += value;
            }
            if (setNum[0]==1) {
                System.out.println("a="+a+"b="+b);
            }
        }
        else {
            //System.out.println("iSa/iSb="+iSa+"-"+iSb);
            try {
                a = Long.parseLong(iSa);
                b = Long.parseLong(iSb);
            } catch (NumberFormatException e) {
                throw new RuntimeException("种子必须数字");
            }
            //System.out.println("a/d="+a+"-"+b);
        }String result="";byte i=0;
        while (result.length()<length){
            lengthNa=(byte)Long.toString(a).length();
            lengthNb=(byte)Long.toString(b).length();
            a = cR(a,setNum[0],lengthNa);
            b = cR(b,setNum[0],lengthNb);
            if (setNum[0]==1) {
                System.out.println(a + "_" + b);
            }
            if (setNum[4]<=1) {
                    result=twoGetByte(a, b,setNum[4]);
            } else {
                result= String.valueOf(a + b);
            }
            if (result.charAt(0)=='-'){
                result=result.replace('-','9');
            }
            i++;
            if (i>10) {
                throw new RuntimeException("长度不能太长，或需换个种子");
            }
        }if ((a^b)<0){
            throw new RuntimeException("长度不能太长");
        }
        return result;
    }
    private static long cR(long a, byte setNum,byte lengthN){
        long ten = 10, lengthNum = 1;
        for (int ix = 1; ix < (lengthN +1) / 2; ix++) {
            ten = ten * 10;
        }
        long ia = a / ten, ab = a % ten;
        if (lengthN % 2 == 1) {
            char divideNum = Long.toString(a).charAt(lengthN / 2);
            ia = (divideNum - 48) / 2 + 10 * ia;
            ab = (a - ten / 10 * ia);
        }
        long addNum;
        if (ab==0){
            addNum=9;
        }else {
            addNum=ia%ab*ten/ab;
        }
        long addNumLength=Long.toString(addNum).length();
        for (int ix = 0; ix < addNumLength; ix++) {
            lengthNum = lengthNum * 10;
        }
        a =addNum+a*lengthNum;
        lengthN = (byte) Long.toString(a).length();
        if(setNum==1){System.out.println("Num="+a+"-<"+addNum+"/Length="+lengthN);}
        return a;
    }
    private static String twoGetByte(long getByteNumA,long getByteNumB,byte setNUm){
        StringBuilder result = new StringBuilder();long num=getByteNumA^getByteNumB;

        if (setNUm == 0) {
            while (!(num==0)){
                byte[] i={(byte) ((num%0x5E)+0x21)};
                result.append(new String(i, StandardCharsets.UTF_8));
                num /= 0x5E;
            }
        } else {
            while (!(num==0)){
                byte[] i = {(byte) (num%62)};
                if (i[0]<10){
                    i[0]= (byte) (0x30+i[0]);
                } else if (i[0]<36) {
                    i[0]= (byte) (55+i[0]);
                }else {
                    i[0]= (byte) (61+i[0]);
                }
                result.append(new String(i, StandardCharsets.UTF_8));
                //System.out.println("num="+num%62+"i[0]="+i[0]);
                num=num/62;
            }
        }
        return result.toString();
    }
    public static String pu1(byte[] set,String U,String K){
        byte[] u=U.getBytes(),k=K.getBytes(),result=new byte[u.length];
        switch (set[3]){
            case 1->{for (int i=0;i<u.length;i=i+k.length){
                for (int ii=0;ii<k.length;ii++){
                    if (i+ii>= result.length){
                        break;
                    }
                    result[i+ii]= (byte) ( (u[i + ii] - k[ii]+0x5d) % 0x5e + 0x21);
                    if (set[0]==1){
                        System.out.println("ii="+ii+"U="+u[i+ii]+"k="+k[ii]);
                    }
                }
            }}
            case 2->{}
            default -> {for (int i=0;i<u.length;i=i+k.length){
                for (int ii=0;ii<k.length;ii++){
                    if (i+ii>= result.length){
                        break;
                    }
                    result[i+ii]= (byte) ((u[i + ii]+k[ii]-0x41) % 0x5e + 0x21);
                    if (set[0]==1){
                        System.out.println("ii="+ii+"U="+u[i+ii]+"k="+k[ii]);
                    }
                }
            }}
        }
        return new String(result, StandardCharsets.UTF_8);
    }
}