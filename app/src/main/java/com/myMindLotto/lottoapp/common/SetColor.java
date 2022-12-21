package com.myMindLotto.lottoapp.common;

import android.widget.ImageView;

import com.example.lottoapp.R;

//번호에 따른 색상 지정
public class SetColor{
    public void changeColor(ImageView[] numImg, int i, int num){
        if (num<11){
            numImg[i].setImageResource(R.drawable.ball_shape_yellow);
        }
        else if (num<21){
            numImg[i].setImageResource(R.drawable.ball_shape_blue);
        }
        else if (num<31){
            numImg[i].setImageResource(R.drawable.ball_shape_red);
        }
        else if (num<41){
            numImg[i].setImageResource(R.drawable.ball_shape_gray);
        }
        else if (num<46){
            numImg[i].setImageResource(R.drawable.ball_shape_green);
        }
    }
}