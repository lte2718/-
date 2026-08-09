package com.lte.rn1;

import static com.lte.rn1.setting.*;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.rn1.R;

public class MainActivity extends AppCompatActivity {
    byte[] configuration =new byte[7];
    private TextView tvHello;
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. 将XML布局“加载”到当前Activity中（这是连接的关键一步！）
        setContentView(R.layout.activity_main);
        EditText seedA=findViewById(R.id.editTextSA),seedB=findViewById(R.id.editTextSB),Length=findViewById(R.id.editTextL);
        // 3. 通过之前定义的ID，找到XML中的控件（“连线”）
        tvHello = findViewById(R.id.tvHello);
        Button buttonShengCheng=findViewById(R.id.buttonShengCheng),buttonCopy=findViewById(R.id.buttonCope);
        Button buttonClear=findViewById(R.id.buttonClear);
        buttonShengCheng.setOnClickListener(v -> {
            // 当按钮被点击时，改变TextView的文字
            try {
                tvHello.setText("感谢使用");
                // 还可以改变文字颜色
                tvHello.setTextColor(ContextCompat.getColor(this,R.color.red)); // 红色
                TextView result=findViewById(R.id.result);
                String A=seedA.getText().toString(),B=seedB.getText().toString(),L=Length.getText().toString();
                if (A.isEmpty()){seedA.setHintTextColor(ContextCompat.getColor(this,R.color.red));
                    throw new RuntimeException("请输入种子A");}else {
                    seedA.setHintTextColor(-10197916);}
                if (B.isEmpty()){seedB.setHintTextColor(ContextCompat.getColor(this,R.color.red));
                    throw new RuntimeException("请输入种子B");}else {
                    seedB.setHintTextColor(-10197916);}
                if (L.isEmpty()){Length.setHintTextColor(ContextCompat.getColor(this,R.color.red));
                    throw new RuntimeException("请输入长度");}else {
                    Length.setHintTextColor(-10197916);}
                result.setText(generate.MAIN(configuration,A,B,Byte.parseByte(L)));
            } catch (RuntimeException e) {
                ErrorDialog(e);
            }
        });
        buttonCopy.setOnClickListener(v->{
            try {
                String A=seedA.getText().toString(),B=seedB.getText().toString(),L=Length.getText().toString();
                copy(generate.MAIN(configuration,A,B,Byte.parseByte(L)));
            } catch (RuntimeException e) {
                ErrorDialog(e);
            }
        });
        buttonClear.setOnClickListener(v -> {
            copy("");
        });
        TextView setAN1=findViewById(R.id.setAN1);
        setAN1.setText(setting.sNA[3]);
        TextView setAN2=findViewById(R.id.setAN2);
        setAN2.setText(sNA[4]);
        TextView setAN3=findViewById(R.id.setAN3);
        setAN3.setText(sNA[5]);
        LinearLayout buttons1=findViewById(R.id.buttons1);
        setButtons((byte)3,buttons1);
        LinearLayout buttons2=findViewById(R.id.buttons2);
        setButtons((byte)4,buttons2);
        LinearLayout buttons3=findViewById(R.id.buttons3);
        setButtons((byte)5,buttons3);
        Button buttonFinish=findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(v -> {
            Intent result=new Intent();
            result.putExtra("configuration", configuration);
            setResult(-1,result);
            finish();
        });
    }

    private void setButtons(byte setA,LinearLayout buttons) {
        Button[] sB1=new Button[sNB[setA].length];
        for (byte i=0;i<sNB[setA].length;i++){
            sB1[i]=new Button(this, null, android.R.attr.buttonStyle);
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f  // weight = 1
            );
            int marginPx = dpToPx(4);
            @SuppressLint("ResourceType") ColorStateList colorStateList = new ColorStateList(
                    new int[][] {
                            new int[] { android.R.attr.state_enabled }, // 不启用状态
                            new int[]{}
                    },
                    new int[] {
                            ContextCompat.getColor(this,R.color.white),
                            ContextCompat.getColor(this,R.color.blue)//颜色（深蓝）
                            });
            btnParams.setMargins(0, marginPx, marginPx, marginPx);
            sB1[i].setLayoutParams(btnParams);
            sB1[i].setText(sNB[setA][i]);
            sB1[i].setId(View.generateViewId());
            sB1[i].setBackgroundTintList(colorStateList);
            byte finalI = i;
            if (i== configuration[setA]){
                sB1[i].setEnabled(false);
            }
            sB1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Button button : sB1) {
                        button.setEnabled(true);
                    }
                    configuration[setA]= finalI;
                    sB1[finalI].setEnabled(false);
                }
            });
            buttons.addView(sB1[i]);
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
    public void copy(String input){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("密码", input);
        clipboard.setPrimaryClip(clip);
    }
    public void ErrorDialog(RuntimeException e){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("错误")                // 标题
                .setMessage(e.toString()) // 内容
                .setPositiveButton("确定",null)
                .setNegativeButton("取消", null) // null 表示点击取消只关闭弹窗，不做事
                .show();
    }
}