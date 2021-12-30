package com.myMindLotto.lottoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lottoapp.R;

//스플래쉬 화면 관련 코드
public class Splash extends AppCompatActivity {

    ImageView splashImgView;
    Animation anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();

        splashImgView = (ImageView) findViewById(R.id.splashImgView);
        anim = AnimationUtils.loadAnimation(this, R.anim.loading);
        splashImgView.setAnimation(anim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                splashImgView.clearAnimation();
                finish();
            }
        },1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }  //다른 액티비티가 활성화되면 finish
}
