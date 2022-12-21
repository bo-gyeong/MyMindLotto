package com.myMindLotto.lottoapp.prizeNums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    /***** "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" *****/
    private static final String BASE_URL = "https://www.dhlottery.co.kr/";

    public static PrizeNumsApi getApiService(){
        return getInstance().create(PrizeNumsApi.class);
    }

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
