package com.myMindLotto.lottoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lottoapp.R;

import java.util.ArrayList;

public class SeeLottoNumListView extends AppCompatActivity {

    ListView seeLottoNumList;

    SQLiteDatabase sqlDB;
    LottoNumDB lottoNumDB;

    int numFirst, numSecond, numThird, numFourth, numFifth, numSixth;
    MakeLottoNumList makeLottoNumList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_lottonum_listview);
        setTitle(getString(R.string.seeRandomNumBtn));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seeLottoNumList = findViewById(R.id.seeLottoNumListView);
        lottoNumDB = new LottoNumDB(this);

        sqlDB = lottoNumDB.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM lottoNum;", null);

        ArrayList<String> allLottoNums = new ArrayList<>();
        ArrayList<Nums> numsContent = new ArrayList<>();

        while (cursor.moveToNext()){
            Nums nums = new Nums();

            allLottoNums.add(cursor.getString(0));
            numFirst = cursor.getInt(1);
            numSecond = cursor.getInt(2);
            numThird = cursor.getInt(3);
            numFourth = cursor.getInt(4);
            numFifth = cursor.getInt(5);
            numSixth = cursor.getInt(6);

            nums.num1TextView = numFirst;
            nums.num2TextView = numSecond;
            nums.num3TextView = numThird;
            nums.num4TextView = numFourth;
            nums.num5TextView = numFifth;
            nums.num6TextView = numSixth;

            numsContent.add(nums);
        }

        makeLottoNumList = new MakeLottoNumList(numsContent);
        seeLottoNumList.setAdapter(makeLottoNumList);
        seeLottoNumList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(SeeLottoNumListView.this);
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setNegativeButton("취소", null);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlDB.execSQL("DELETE FROM lottoNum WHERE allLottoNum = '" + allLottoNums.get(position) + "';" );
                        allLottoNums.remove(position);

                        Intent intent = getIntent();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();

                return false;
            }
        });  //'저장내역 보기' 리스트 아이템 길게 클릭 후 삭제에 관한 대화상자 띄우기
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
