package it.unimib.wordino.main.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RandomWordApiService { //TODO esportare i parametri + Mettere su file delle costanti
    @GET("word")
    Call <List<String>> getRandomWord(
            @Query("length") int length,
            @Query("lang") String language
    );
}
