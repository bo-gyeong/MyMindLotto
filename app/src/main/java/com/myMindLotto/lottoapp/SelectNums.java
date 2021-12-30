package com.myMindLotto.lottoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.lottoapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SelectNums extends AppCompatActivity {

    ChangeItems changeItems = new ChangeItems();
    Button allSelectBtn, allOutBtn, allInitialBtn, onlySelectCombBtn, mixCombBtn, saveCombNumBtn, callCombNumBtn;

    ArrayList<Integer> selNumsList;
    ArrayList<Integer> outNumsList;
    boolean isMixed, isModify;
    String getTitle;
    int[] getNums;
    SharedPreferences sharedPreferences;

    SQLiteDatabase sqlDB;
    LottoNumDB lottoNumDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_nums);
        setTitle(getString(R.string.selectNBtn));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        checkFirstRun();

        Integer[] numBtnId = new Integer[]{R.id.num1Btn, R.id.num2Btn, R.id.num3Btn, R.id.num4Btn, R.id.num5Btn
        , R.id.num6Btn, R.id.num7Btn, R.id.num8Btn, R.id.num9Btn, R.id.num10Btn,
                R.id.num11Btn, R.id.num12Btn, R.id.num13Btn, R.id.num14Btn, R.id.num15Btn
                , R.id.num16Btn, R.id.num17Btn, R.id.num18Btn, R.id.num19Btn, R.id.num20Btn,
                R.id.num21Btn, R.id.num22Btn, R.id.num23Btn, R.id.num24Btn, R.id.num25Btn
                , R.id.num26Btn, R.id.num27Btn, R.id.num28Btn, R.id.num29Btn, R.id.num30Btn,
                R.id.num31Btn, R.id.num32Btn, R.id.num33Btn, R.id.num34Btn, R.id.num35Btn
                , R.id.num36Btn, R.id.num37Btn, R.id.num38Btn, R.id.num39Btn, R.id.num40Btn,
                R.id.num41Btn, R.id.num42Btn, R.id.num43Btn, R.id.num44Btn, R.id.num45Btn};

        Button[] numBtn = new Button[numBtnId.length];
        final int[] numBtnClickTimes = new int[45];

        Intent intent= getIntent();
        if (intent.getExtras() != null){
            getTitle = intent.getStringExtra("getTitle");
            getNums = intent.getExtras().getIntArray("getNums");
            isModify = intent.getBooleanExtra("isModify", false);

            for (int i=0; i<numBtnId.length; i++){
                numBtn[i] = (Button) findViewById(numBtnId[i]);
                numBtnClickTimes[i] = getNums[i];

                if (numBtnClickTimes[i]%3 == 1){
                    changeItems.selectItems(numBtn, i, getApplicationContext());
                }
                else if (numBtnClickTimes[i]%3 == 2){
                    changeItems.outItems(numBtn, i, getApplicationContext());
                }
                else{
                    changeItems.initialItems(numBtn, i, getApplicationContext());
                }
            }

        } else{
            for (int i=0; i<numBtnId.length; i++){
                numBtn[i] = (Button) findViewById(numBtnId[i]);
                numBtnClickTimes[i] = 0;
            }
        }  //SeeCombNumListView 클래스에서 intent를 통해 데이터를 불러왔는지에 따른 동작

        for (int i=0; i<numBtnId.length; i++){
            int finalI = i;

            numBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numBtnClickTimes[finalI]%3 == 0){
                        changeItems.selectItems(numBtn, finalI, getApplicationContext());
                        numBtnClickTimes[finalI]++;
                    }
                    else if (numBtnClickTimes[finalI]%3 == 1){
                        changeItems.outItems(numBtn, finalI, getApplicationContext());
                        numBtnClickTimes[finalI]++;
                    }
                    else{
                        changeItems.initialItems(numBtn, finalI, getApplicationContext());
                        numBtnClickTimes[finalI] = 0;
                    }
                }
            });
        }  //공 버튼 클릭 시 색상 변경

        allSelectBtn = (Button) findViewById(R.id.allSelectBtn);
        allSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<numBtnId.length; i++) {
                    int finalI = i;

                    changeItems.selectItems(numBtn, finalI, getApplicationContext());
                    numBtnClickTimes[finalI] = 1;
                }
            }
        });  //모두 선택 버튼

        allOutBtn = (Button) findViewById(R.id.allOutBtn);
        allOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<numBtnId.length; i++) {
                    int finalI = i;

                    changeItems.outItems(numBtn, finalI, getApplicationContext());
                    numBtnClickTimes[finalI] = 2;
                }
            }
        });  //모두 제외 버튼

        allInitialBtn = (Button) findViewById(R.id.allInitialBtn);
        allInitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<numBtnId.length; i++) {
                    int finalI = i;

                    changeItems.initialItems(numBtn, finalI, getApplicationContext());
                    numBtnClickTimes[finalI] = 0;
                }
            }
        });  //초기화 버튼

        onlySelectCombBtn = (Button) findViewById(R.id.onlySelectCombBtn);
        onlySelectCombBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selNumsList = new ArrayList<>();
                outNumsList = new ArrayList<>();
                isMixed = false;

                for (int i=0; i<numBtnId.length; i++) {
                    if(numBtnClickTimes[i] == 1){
                        selNumsList.add(i+1);
                    }  //numBtnClickTimes[i]가 1번 클릭된 상태면 공 숫자를 선택 리스트에 넣기
                    else if (numBtnClickTimes[i] == 2){
                        outNumsList.add(i+1);
                    }  //numBtnClickTimes[i]가 2번 클릭된 상태면 공 숫자를 제외 리스트에 넣기
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putIntegerArrayListExtra("selNums", selNumsList);
                intent.putIntegerArrayListExtra("outNums", outNumsList);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                if (selNumsList.size()==6){
                    Toast.makeText(getApplicationContext(), "6개의 숫자를 선택하여 새로고침이 되지 않습니다", Toast.LENGTH_SHORT).show();
                }
                else if (outNumsList.size()>38){
                    Toast.makeText(getApplicationContext(), "최소 7개의 숫자를 남겨주세요", Toast.LENGTH_SHORT).show();
                }
                else if (selNumsList.size()<6){
                    Toast.makeText(getApplicationContext(), " 선택된 숫자의 개수가 적어\n다른 숫자와 함께 조합됩니다", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "새로고침을 눌러주세요", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });

        mixCombBtn = (Button) findViewById(R.id.mixCombBtn);
        mixCombBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selNumsList = new ArrayList<>();
                outNumsList = new ArrayList<>();
                isMixed = true;

                for (int i=0; i<numBtnId.length; i++) {
                    if(numBtnClickTimes[i] == 1){
                        selNumsList.add(i+1);
                    }  //numBtnClickTimes[i]가 1번 클릭된 상태면 공 숫자를 선택 리스트에 넣기
                    else if (numBtnClickTimes[i] == 2){
                        outNumsList.add(i+1);
                    }  //numBtnClickTimes[i]가 2번 클릭된 상태면 공 숫자를 제외 리스트에 넣기
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putIntegerArrayListExtra("selNums", selNumsList);
                intent.putIntegerArrayListExtra("outNums", outNumsList);
                intent.putExtra("isMixed", isMixed);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                if (outNumsList.size()>38){
                    Toast.makeText(getApplicationContext(), "최소 7개의 숫자를 남겨주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "새로고침을 눌러주세요", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });

        lottoNumDB = new LottoNumDB(this);
        saveCombNumBtn = (Button) findViewById(R.id.saveCombNumBtn);
        saveCombNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(SelectNums.this, R.layout.save_dlg_layout, null);
                final EditText titleEdit = (EditText) view.findViewById(R.id.saveCombEditText);

                AlertDialog.Builder dlg = new AlertDialog.Builder(SelectNums.this);
                dlg.setTitle("제목을 입력해주세요");
                if (isModify){
                    titleEdit.setText(getTitle);
                }
                dlg.setView(view);
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String titleStr = titleEdit.getText().toString();
                        sqlDB = lottoNumDB.getWritableDatabase();
                        //lottoNumDB.onUpgrade(sqlDB, 1, 2);
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(titleEdit.getWindowToken(), 0);

                        Cursor cursor = null;
                        try {
                            cursor = sqlDB.rawQuery("SELECT combTitle FROM combNum WHERE combTitle = '" + titleStr + "';", null);
                        }catch (Exception e){
                            e.printStackTrace();
                        }  //이미 있는 제목인지 확인위한 쿼리

                        String title = null;
                        if (cursor != null){
                            while (cursor.moveToNext()) {
                                title = cursor.getString(0);
                            }
                        }  //cursor에 값이 있으면 title에 넣기

                        if (title != null && !isModify){  //title이 null이 아니고 수정버튼 클릭 여부가 false인 상태일때
                            Toast.makeText(getApplicationContext(), "이미 있는 제목입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (isModify){  //수정 버튼을 클릭했을 때
                                sqlDB.execSQL("DELETE FROM combNum WHERE combTitle = '" + title + "';");
                            }
                            sqlDB.execSQL("INSERT INTO combNum VALUES ('" + titleStr + "', '"
                                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "', "
                                    + numBtnClickTimes[0] + ", " + numBtnClickTimes[1] + ", " + numBtnClickTimes[2]
                                    + ", " + numBtnClickTimes[3] + ", " + numBtnClickTimes[4] + ", " + numBtnClickTimes[5]
                                    + ", " + numBtnClickTimes[6] + ", " + numBtnClickTimes[7] + ", " + numBtnClickTimes[8]
                                    + ", " + numBtnClickTimes[9] + ", " + numBtnClickTimes[10]
                                    + ", " + numBtnClickTimes[11] + ", " + numBtnClickTimes[12]
                                    + ", " + numBtnClickTimes[13] + ", " + numBtnClickTimes[14] + ", " + numBtnClickTimes[15]
                                    + ", " + numBtnClickTimes[16] + ", " + numBtnClickTimes[17] + ", " + numBtnClickTimes[18]
                                    + ", " + numBtnClickTimes[19] + ", " + numBtnClickTimes[20]
                                    + ", " + numBtnClickTimes[21] + ", " + numBtnClickTimes[22]
                                    + ", " + numBtnClickTimes[23] + ", " + numBtnClickTimes[24] + ", " + numBtnClickTimes[25]
                                    + ", " + numBtnClickTimes[26] + ", " + numBtnClickTimes[27] + ", " + numBtnClickTimes[28]
                                    + ", " + numBtnClickTimes[29] + ", " + numBtnClickTimes[30]
                                    + ", " + numBtnClickTimes[31] + ", " + numBtnClickTimes[32]
                                    + ", " + numBtnClickTimes[33] + ", " + numBtnClickTimes[34] + ", " + numBtnClickTimes[35]
                                    + ", " + numBtnClickTimes[36] + ", " + numBtnClickTimes[37] + ", " + numBtnClickTimes[38]
                                    + ", " + numBtnClickTimes[39] + ", " + numBtnClickTimes[40]
                                    + ", " + numBtnClickTimes[41] + ", " + numBtnClickTimes[42]
                                    + ", " + numBtnClickTimes[43] + ", " + numBtnClickTimes[44] + ");");

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }
                        sqlDB.close();
                    }
                });
                dlg.show();
            }
        });

        callCombNumBtn = (Button) findViewById(R.id.callCombNumBtn);
        callCombNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnIntent = new Intent(getApplicationContext(), SeeCombNumListView.class);
                startActivity(btnIntent);
            }
        });  //저장내역 불러오기 버튼 클릭시 리스트 불러옴
    }

    public void checkFirstRun() {
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent intent = new Intent(getApplicationContext(), Guide.class);
            startActivity(intent);
        }
    }  //앱 첫 실행 후 isFirstRun이 true이면 가이드 화면 보여줌

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }  //뒤로가기 버튼 클릭 시
}

class ChangeItems{
    void selectItems(Button[] numBtn, int finalI, Context context){
        if (finalI<10){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ball_shape_yellow));
                numBtn[finalI].setTextColor(Color.WHITE);
            } else {
                numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ball_shape_yellow));
                numBtn[finalI].setTextColor(Color.WHITE);
            }
        }
        else if (9<finalI && finalI<20){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ball_shape_blue));
                numBtn[finalI].setTextColor(Color.WHITE);
            } else {
                numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ball_shape_blue));
                numBtn[finalI].setTextColor(Color.WHITE);
            }
        }
        else if (19<finalI && finalI<30){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ball_shape_red));
                numBtn[finalI].setTextColor(Color.WHITE);
            } else {
                numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ball_shape_red));
                numBtn[finalI].setTextColor(Color.WHITE);
            }
        }
        else if (29<finalI && finalI<40){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ball_shape_gray));
                numBtn[finalI].setTextColor(Color.WHITE);
            } else {
                numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ball_shape_gray));
                numBtn[finalI].setTextColor(Color.WHITE);
            }
        }
        else if (39<finalI && finalI<45){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ball_shape_green));
                numBtn[finalI].setTextColor(Color.WHITE);
            } else {
                numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ball_shape_green));
                numBtn[finalI].setTextColor(Color.WHITE);
            }
        }
    }

    void outItems(Button[] numBtn, int finalI, Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24));
            numBtn[finalI].setTextColor(Color.BLACK);
        } else {
            numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24));
            numBtn[finalI].setTextColor(Color.BLACK);
        }
    }

    void initialItems(Button[] numBtn, int finalI, Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            numBtn[finalI].setBackground(ContextCompat.getDrawable(context, R.drawable.ball_shape));
            numBtn[finalI].setTextColor(Color.WHITE);
        } else {
            numBtn[finalI].setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ball_shape));
            numBtn[finalI].setTextColor(Color.WHITE);
        }
    }
}  //번호에 따른 공 색 변화