package com.lte.rn1;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class generate{

    public static String MAIN(byte[] setNum, String iSa, String iSb, byte length)throws RuntimeException{
        long a = 0,b = 0;
        byte lengthNa,lengthNb;
        if (setNum[4]==2) {
            if (length <1| length >20){
                throw new RuntimeException("长度需大于0,不能太长");
            }
        }
        if (Objects.equals(iSa, iSb)){
            throw new RuntimeException("种子不能相等");
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
                throw new RuntimeException("长度太长，或需换个种子");
            }
        }if ((a^b)<0){
            throw new RuntimeException("长度太长");
        }
        return result;
    }
    private static long cR(long a, byte setNum, byte lengthN){
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
    private static String twoGetByte(long getByteNumA, long getByteNumB, byte setNUm){
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
}
