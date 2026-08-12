package com.lte.rn1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rn1.R;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Config.sNB0[0]=Config.versionName(this);
        Config.loadConfig(this);
        Button toShengChengActivity=findViewById(R.id.g),toInfo=findViewById(R.id.buttonInfo);
        Intent intentSC=new Intent(this, shengChengActivity.class);
        Intent intentST=new Intent(this, settingActivity0.class);
        toShengChengActivity.setOnClickListener(v -> {
            startActivity(intentSC);
        });
        toInfo.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("关于")                // 标题
                    .setMessage(Config.sNB0[0]+"\n由LTE一人制作哟") // 内容
                    .setPositiveButton("确定",null)
                    .setNegativeButton("取消", null) // null 表示点击取消只关闭弹窗，不做事
                    .show();
        });
        Button toSetting=findViewById(R.id.buttonSetting);
        toSetting.setOnClickListener(v -> {
            startActivity(intentST);
        });
    }

}
