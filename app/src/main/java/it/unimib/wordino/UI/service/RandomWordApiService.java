package it.unimib.wordino.UI.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomWordApiService { //TODO esportare i parametri
    @GET("word")
    Call<String> getWord(
            @Query("length") int length,
            @Query("language") String language
    );
}
