package com.myMindLotto.lottoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 'main 페이지' 에서 랜덤생성번호 저장 및 '원하는 숫자로 조합' 클릭시 나오는 번호 저장하는 DB
public class LottoNumDB extends SQLiteOpenHelper{
    public LottoNumDB(Context context){
        super(context, "lottoDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lottoNum (allLottoNum CHAR(20) PRIMARY KEY, lottoNumFirst INTEGER, lottoNumSecond INTEGER, " +
                "lottoNumThird INTEGER, lottoNumFourth INTEGER, lottoNumFifth INTEGER, lottoNumSixth INTEGER);");

        db.execSQL("CREATE TABLE combNum (combTitle CHAR(20) PRIMARY KEY, combDate DATE DEFAULT CURRENT_DATE, "
                + " num1 INTEGER, num2 INTEGER, num3 INTEGER, num4 INTEGER, num5 INTEGER,"
                + " num6 INTEGER, num7 INTEGER, num8 INTEGER, num9 INTEGER, num10 INTEGER,"
                + " num11 INTEGER, num12 INTEGER, num13 INTEGER, num14 INTEGER, num15 INTEGER,"
                + " num16 INTEGER, num17 INTEGER, num18 INTEGER, num19 INTEGER, num20 INTEGER,"
                + " num21 INTEGER, num22 INTEGER, num23 INTEGER, num24 INTEGER, num25 INTEGER,"
                + " num26 INTEGER, num27 INTEGER, num28 INTEGER, num29 INTEGER, num30 INTEGER,"
                + " num31 INTEGER, num32 INTEGER, num33 INTEGER, num34 INTEGER, num35 INTEGER,"
                + " num36 INTEGER, num37 INTEGER, num38 INTEGER, num39 INTEGER, num40 INTEGER,"
                + " num41 INTEGER, num42 INTEGER, num43 INTEGER, num44 INTEGER, num45 INTEGER"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lottoNum;");
        db.execSQL("DROP TABLE IF EXISTS combNum;");

        onCreate(db);
    }
}
