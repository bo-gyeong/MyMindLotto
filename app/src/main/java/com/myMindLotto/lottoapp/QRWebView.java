package com.myMindLotto.lottoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lottoapp.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRWebView extends AppCompatActivity {

    IntentIntegrator integrator;
    WebView showResultWV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_webview);
        setTitle(getString(R.string.QRScanBtn));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showResultWV = (WebView) findViewById(R.id.showResultWV);

        WebSettings webSettings = showResultWV.getSettings();
        webSettings.setJavaScriptEnabled(true);

        showResultWV.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(false);
        integrator.setPrompt("QR코드를 사각형 안에 비춰주세요.");
        integrator.initiateScan();  //QR스캔해서 웹으로 불러오는 코드들
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                finish();
            }
            else{
                showResultWV.loadUrl(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }  //QR코드 읽기 성공여부에 따른 동작 수행

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
