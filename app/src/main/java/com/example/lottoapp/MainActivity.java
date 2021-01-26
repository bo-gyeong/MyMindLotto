package com.example.lottoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout, lNum;
    TextView tv;
    Button btn;
    MyGraphicView graphicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("행운의 로또번호");

        layout = (LinearLayout) findViewById(R.id.layout);
        tv = (TextView) findViewById(R.id.Tv);
        lNum = (LinearLayout) findViewById(R.id.LNum);
        btn = (Button) findViewById(R.id.Btn);

        Toast.makeText(getApplicationContext(),"화면 아무곳이나 터치하세요", Toast.LENGTH_LONG).show();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               layout.setVisibility(View.GONE);
            }
        });

        graphicView = (MyGraphicView) new MyGraphicView(this);
        lNum.addView(graphicView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphicView.invalidate();
            }
        });
    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();

            int lottoNum[] = new int[6];
            for(int i=0; i<lottoNum.length; i++){
                lottoNum[i] = (int) (Math.random()*45 + 1);
                for(int j=0; j<i; j++){
                    if(lottoNum[j] == lottoNum[i]) i--;
                }
            }
            Arrays.sort(lottoNum);

            for(int i=0; i<lottoNum.length; i++){
                if(lottoNum[i]<=10) {
                    paint.setColor(Color.parseColor("#FFBB00"));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(150*(i+1), 200, 60, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(60);
                    canvas.drawText(" "+lottoNum[i]+" ", 141*(i+1), 220, paint);
                }
                else if(lottoNum[i]<=20) {
                    paint.setColor(Color.parseColor("#0000C9"));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(150*(i+1), 200, 60, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(60);
                    canvas.drawText(lottoNum[i]+"", 141*(i+1), 220, paint);
                }
                else if(lottoNum[i]<=30) {
                    paint.setColor(Color.parseColor("#ED0000"));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(150*(i+1), 200, 60, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(60);
                    canvas.drawText(lottoNum[i]+"", 141*(i+1), 220, paint);
                }
                else if(lottoNum[i]<=40) {
                    paint.setColor(Color.parseColor("#757575"));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(150*(i+1), 200, 60, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(60);
                    canvas.drawText(lottoNum[i]+"", 141*(i+1), 220, paint);
                }
                else {
                    paint.setColor(Color.parseColor("#0BC904"));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(150*(i+1), 200, 60, paint);
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(60);
                    canvas.drawText(lottoNum[i]+"", 141*(i+1), 220, paint);
                }

            }

        }
    }

}