package com.myMindLotto.lottoapp.prizeNums;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lottoapp.R;
import com.myMindLotto.lottoapp.common.SetColor;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrizeNums extends AppCompatActivity {

    ProgressBar progressBar;
    EditText inputDrwNoEdtTxt;
    Button searchPrizeNumBtn;
    RecyclerView prizeNumRecycler;

    Call<PrizeNumsDTO> call;
    MakePrizeNumsRecycler makePrizeRecycler;

    int latestDrwNo = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prize_nums);
        setTitle(getString(R.string.PrizeNumBtn));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inputDrwNoEdtTxt = (EditText) findViewById(R.id.inputDrwNoEditText);
        searchPrizeNumBtn = (Button) findViewById(R.id.searchPrizeNumBtn);
        prizeNumRecycler = (RecyclerView) findViewById(R.id.prizeNumRecyclerView);

        getLatestDrwNo();

        searchPrizeNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drwNo = inputDrwNoEdtTxt.getText().toString();
                if (!drwNo.equals("")){
                    getPrizeNum(drwNo);
                }
                else{
                    Toast.makeText(getApplicationContext(), "번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });  //검색 버튼 클릭 시 (회차번호 찾기)
    }

    void getPrizeNum(String drwNo){
        View dialogView = getLayoutInflater().inflate(R.layout.call_prize_num_dialog, null);
        TextView dateTxt = (TextView) dialogView.findViewById(R.id.dateTextView);

        ImageView[] numImg = new ImageView[7];
        Integer[] numImgId = new Integer[]{R.id.num1ImgView, R.id.num2ImgView, R.id.num3ImgView
                , R.id.num4ImgView, R.id.num5ImgView, R.id.num6ImgView, R.id.bonusNumImgView};

        TextView[] numTxt = new TextView[7];
        Integer[] numTxtId = new Integer[]{R.id.num1TextView, R.id.num2TextView, R.id.num3TextView
                , R.id.num4TextView, R.id.num5TextView, R.id.num6TextView, R.id.bonusNumTextView};

        for (int i=0; i<numImg.length; i++) {
            numImg[i] = (ImageView) dialogView.findViewById(numImgId[i]);
            numTxt[i] = (TextView) dialogView.findViewById(numTxtId[i]);
        }

        AlertDialog.Builder dlg = new AlertDialog.Builder(dialogView.getContext());
        dlg.setTitle(drwNo + "회");
        dlg.setView(dialogView);
        dlg.setPositiveButton("확인", null);

        SetColor setColor = new SetColor();
        call = RetrofitClient.getApiService().getPrizeInfo(drwNo);
        call.enqueue(new Callback<PrizeNumsDTO>() {
            @Override
            public void onResponse(Call<PrizeNumsDTO> call, Response<PrizeNumsDTO> response) {
                PrizeNumsDTO result = response.body();

                dateTxt.setText(result.getDate());

                for (int i=0; i<6; i++){
                    String[] prizeNumArr = result.toString().split(",");
                    numTxt[i].setText(prizeNumArr[i]);
                    setColor.changeColor(numImg, i, Integer.parseInt(numTxt[i].getText().toString()));
                } //당첨번호 받아서 대화상자의 공 색상 변경

                String bonusNum = result.getBnusNo();
                numTxt[6].setText(bonusNum);
                setColor.changeColor(numImg, 6, Integer.parseInt(numTxt[6].getText().toString()));

                dlg.show();
            }

            @Override
            public void onFailure(Call<PrizeNumsDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 연결상태를 확인해주세요", Toast.LENGTH_LONG).show();
            }
        });
    }  //크롤링

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String latestDrwNoStr = bundle.getString("latestDrwNo");
            latestDrwNo = Integer.parseInt(latestDrwNoStr);

            makeDrwNoList();
        }
    };

    // 최신 ~ 1회차까지의 리스트 만들기 위해 최신 회차 받아오는 메소드
    private void getLatestDrwNo(){
        String url = "https://www.dhlottery.co.kr/gameResult.do?method=byWin";
        final Bundle bundle = new Bundle();
/****** https://wonpaper.tistory.com/111     https://tosuccess.tistory.com/119 *****/
        new Thread(){
            @Override
            public void run() {
                try {
                    org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

                    Elements elements;
                    elements = doc.select(".win_result h4 strong"); // 회차

                    String drwNoStr = elements.text();
                    drwNoStr = drwNoStr.substring(0, drwNoStr.length()-1);

                    bundle.putString("latestDrwNo", drwNoStr);
                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }catch (Exception e){
                    Handler handler = new Handler(Looper.getMainLooper());  //Looper: 무한루프 돌며 큐에 있는 내용을 꺼냄
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "네트워크 연결상태를 확인해주세요", Toast.LENGTH_LONG).show();
                        }
                    }, 0);
                }
            }
        }.start();
    }

    private void makeDrwNoList(){
        ArrayList<PrizeNumItem> prizeNumItemList = new ArrayList<>();

        for (int i=latestDrwNo; i>=1; i--){
            prizeNumItemList.add(new PrizeNumItem(i + "회 당첨번호"));
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        prizeNumRecycler.setLayoutManager(manager);
        prizeNumRecycler.setHasFixedSize(false);
        makePrizeRecycler = new MakePrizeNumsRecycler(prizeNumItemList, getApplicationContext());
        prizeNumRecycler.setAdapter(makePrizeRecycler);
        makePrizeRecycler.setOnRItemClickListener(new MakePrizeNumsRecycler.OnRItemClickListener() {
            @Override
            public void onRItemClickListener(View v, int position) {
                makeDialog(position);
            }
        });
    }

    public void makeDialog(int position){
        String drwNo = String.valueOf(latestDrwNo-position);
        getPrizeNum(drwNo);
    }  //리사이클러뷰 아이템 클릭 시 해당 회차의 대화상자 만들기

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
