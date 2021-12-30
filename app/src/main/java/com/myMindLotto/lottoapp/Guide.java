package com.myMindLotto.lottoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lottoapp.R;

// 원하는 숫자로 조합 클릭시 처음 이 앱을 설치한 사람들에게 가이드 보여줌
public class Guide extends AppCompatActivity {

    Button closeBtn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);

        closeBtn = (Button) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
                finish();
            }
        });  //다시보지않기 버튼 클릭 시 가이드 창을 더이상 보여주지 않음
    }
}
