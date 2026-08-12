package com.lte.rn1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rn1.R;

import java.util.Objects;

public class settingActivity0 extends AppCompatActivity {
    boolean a=false;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.setting_activity0);
        LinearLayout settingMain=findViewById(R.id.settingMain);
        Button buttonFinish=findViewById(R.id.SettingFinish);
        buttonFinish.setOnClickListener(v -> {
            finish();
        });
        String Goto=getIntent().getStringExtra("goto");

        for (byte i=0;i<Config.sNA.length;i++){
            if (Objects.equals(Goto, Config.sNA[i])){
                shengChengActivity.setButtons(this,i,settingMain);
                a=true;
                break;
            }
        }
        if (!a){
            Main(settingMain);
        }
    }

    private void Main(LinearLayout settingMain) {
        Button[] setA=new Button[Config.sNA.length];
        for (byte i=0;i<Config.sNA.length;i++){
            setA[i]=new Button(this, null, android.R.attr.buttonStyle);
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f  // weight = 1
            );
            int marginPx =shengChengActivity.dpToPx(this);
            btnParams.setMargins(0, marginPx, marginPx, marginPx);
            setA[i].setLayoutParams(btnParams);
            setA[i].setText(Config.sNA[i]);
            byte finalI = i;
            setA[i].setOnClickListener(v -> {
                Intent intent=new Intent(this, settingActivity0.class);
                intent.putExtra("goto", Config.sNA[finalI]);
                startActivity(intent);
            });
            settingMain.addView(setA[i]);
        }
    }
}
