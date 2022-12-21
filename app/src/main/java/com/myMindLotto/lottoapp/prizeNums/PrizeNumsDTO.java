package com.myMindLotto.lottoapp.prizeNums;

import com.google.gson.annotations.SerializedName;

public class PrizeNumsDTO {
    @SerializedName("drwNoDate")
    private String date;

    @SerializedName("drwtNo1")
    private String drwtNo1;

    @SerializedName("drwtNo2")
    private String drwtNo2;

    @SerializedName("drwtNo3")
    private String drwtNo3;

    @SerializedName("drwtNo4")
    private String drwtNo4;

    @SerializedName("drwtNo5")
    private String drwtNo5;

    @SerializedName("drwtNo6")
    private String drwtNo6;

    @SerializedName("bnusNo")
    private String bnusNo;

    public String getDate() {
        return date;
    }

    public String getDrwtNo1() {
        return drwtNo1;
    }

    public String getDrwtNo2() {
        return drwtNo2;
    }

    public String getDrwtNo3() {
        return drwtNo3;
    }

    public String getDrwtNo4() {
        return drwtNo4;
    }

    public String getDrwtNo5() {
        return drwtNo5;
    }

    public String getDrwtNo6() {
        return drwtNo6;
    }

    public String getBnusNo() {
        return bnusNo;
    }

    @Override
    public String toString() {
        return drwtNo1 + ","
                + drwtNo2 + ","
                + drwtNo3 + ","
                + drwtNo4 + ","
                + drwtNo5 + ","
                + drwtNo6 + ",";
    }
}
