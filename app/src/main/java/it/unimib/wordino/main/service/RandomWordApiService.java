package it.unimib.wordino.main.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomWordApiService { //TODO esportare i parametri
    @GET("word")
    Call <List<String>> getWord( //HERE
            @Query("length") int length,
            @Query("lang") String language
    );
}
