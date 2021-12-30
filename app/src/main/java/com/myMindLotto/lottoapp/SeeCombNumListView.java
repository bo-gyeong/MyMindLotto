package com.myMindLotto.lottoapp;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lottoapp.R;

import java.util.ArrayList;

public class SeeCombNumListView extends AppCompatActivity {

    ListView seeCombNumList;

    SQLiteDatabase sqlDB;
    LottoNumDB lottoNumDB;

    ChangeItems changeItems = new ChangeItems();
    int getNums[];
    String title, date;
    boolean isModify;
    MakeCombList makeCombList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_combnum_listview);
        setTitle("저장내역 불러오기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seeCombNumList = findViewById(R.id.seeCombNumListView);
        lottoNumDB = new LottoNumDB(this);
        getNums = new int[45];

        sqlDB = lottoNumDB.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM combNum;", null);

        ArrayList<ListItem> listContent = new ArrayList<>();
        ArrayList<String> combTitle = new ArrayList<>();

        while (cursor.moveToNext()){
            ListItem listItem = new ListItem();

            title = cursor.getString(0);
            date = cursor.getString(1);

            listItem.titleTextView = title;
            listItem.dateTextView = date;

            listContent.add(listItem);
            combTitle.add(title);
        }

        makeCombList = new MakeCombList(listContent);
        seeCombNumList.setAdapter(makeCombList);
        seeCombNumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View dialogView = getLayoutInflater().inflate(R.layout.call_comb_num_dialog, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle(combTitle.get(position));
                dlg.setView(dialogView);

                Cursor cursor = sqlDB.rawQuery("SELECT * FROM combNum WHERE combTitle = '" + combTitle.get(position) + "';", null);
                cursor.moveToFirst();
                for (int i=0; i<45; i++){
                    getNums[i] = cursor.getInt(i+2);
                }

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
                for (int i=0; i<numBtnId.length; i++){
                    numBtn[i] = (Button) dialogView.findViewById(numBtnId[i]);

                    if (getNums[i]%3 == 1){
                        changeItems.selectItems(numBtn, i, getApplicationContext());
                    }
                    else if (getNums[i]%3 == 2){
                        changeItems.outItems(numBtn, i, getApplicationContext());
                    }
                    else{
                        changeItems.initialItems(numBtn, i, getApplicationContext());
                    }
                }  //저장된 공 클릭 횟수에 따른 색상 변화

                dlg.setNegativeButton("취소", null);
                dlg.setNeutralButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isModify = true;

                        Intent intent = new Intent(getApplicationContext(), SelectNums.class);

                        intent.putExtra("getTitle", combTitle.get(position));
                        intent.putExtra("getNums", getNums);
                        intent.putExtra("isModify", isModify);
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);  //스택 맨 위에 쌓인 액티비티 지우기
                        startActivity(intent);
                        finish();
                    }
                });
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), SelectNums.class);

                        intent.putExtra("getNums", getNums);
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                dlg.show();
            }
        });  //'저장내역 불러오기' 리스트 아이템 클릭 후 대화상자 띄우기

        seeCombNumList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(SeeCombNumListView.this);

                dlg.setTitle(combTitle.get(position));
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlDB.execSQL("DELETE FROM combNum WHERE combTitle = '" + combTitle.get(position) + "';" );
                        combTitle.remove(position);

                        Intent intent = getIntent();
                        finish();
                        overridePendingTransition(0, 0);  //인텐트 전환시 애니메이션 작동 중지
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();

                return true;
            }
        });  //'저장내역 불러오기' 리스트 아이템 길게 클릭 후 삭제에 관한 대화상자 띄우기
    }

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
