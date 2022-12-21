package com.myMindLotto.lottoapp.prizeNums;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrizeNumsApi {
    @GET("common.do?method=getLottoNumber")
    Call<PrizeNumsDTO> getPrizeInfo(
      @Query("drwNo") String drwNo
    );
}
