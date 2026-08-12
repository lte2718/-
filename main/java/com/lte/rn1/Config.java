package com.lte.rn1;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Config{
    public static byte[] config;
    public static void loadConfig(Context context) {
        try (FileInputStream fis = context.openFileInput("config")) {
            // 获取文件大小，创建一个正好容纳数据的 byte[]
            int fileSize = fis.available();
            byte[] data = new byte[fileSize];
            // 一次性读取全部数据
            int bytesRead = fis.read(data);
            fis.close();
            if (bytesRead == fileSize) {
                Toast.makeText(context, "配置读取成功，大小：" + data.length + " 字节", Toast.LENGTH_SHORT).show();
                config=data;
            } else {
                // 理论上不会发生，但保留处理逻辑
                Toast.makeText(context, "配置读取不完整", Toast.LENGTH_SHORT).show();
                config=new byte[8];
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "生成新配置", Toast.LENGTH_SHORT).show();
            config=new byte[8];
        } catch (IOException e) {
            Toast.makeText(context, "配置读取失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
            config=new byte[8];
        }
    }
    public static void saveConfig(Context context){
        String fileName = "config";
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(config);
            Toast.makeText(context, "配置保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "配置保存失败"+ e, Toast.LENGTH_SHORT).show();
        }
    }
    public static String versionName(Context context){
        String versionName;
        try {
            versionName ="version"+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName+",android";
        } catch (PackageManager.NameNotFoundException e) {
            versionName="未知版本号"+e;
        }
       return versionName;
    }
    static String[] sNB0={"version","made by LTe"};
    static String[] sNB1={"中文","其","实","没","有","任何","作用"},sNB2={"",""};
    static String[] sNB3={"rn1"};
    static String[] sNB4={"字符","数字字母","数字"};
    static String[] sNB5={"数字","字符"},sNB7={"n","o"};
    static String[] sNB6={"全手动","复制结果不清除","复制结果退出后清除"};
    static String[] sNA={"关于","语言","暂无","生成方法","输出类型","输入类型","所有结果处理","用户"};
    static String[][] sNB={sNB0,sNB1,sNB2,sNB3, sNB4,sNB5,sNB6,sNB7};
    public static boolean createFolder(Context context, String folderName) {
        // getFilesDir() 指向 /data/data/包名/files/
        File folder = new File(context.getFilesDir(), folderName);
        // 如果文件夹已存在且是目录，直接返回成功
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        // 关键：使用 mkdirs()（带 s）创建目录，包括任何必需但不存在的父目录
        return folder.mkdirs();
    }
}

