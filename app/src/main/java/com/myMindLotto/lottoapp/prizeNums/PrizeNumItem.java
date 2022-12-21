package com.myMindLotto.lottoapp.prizeNums;

// 회차별 당첨번호 리사이클러뷰 커스텀 위해 회차 정보 가져오는 코드
public class PrizeNumItem {
    private String drwNoTextView;

    public PrizeNumItem(String drwNoTextView) {
        this.drwNoTextView = drwNoTextView;
    }

    public String getDrwNoTextView() {
        return drwNoTextView;
    }

    public void setDrwNoTextView(String drwNoTextView) {
        this.drwNoTextView = drwNoTextView;
    }
}
