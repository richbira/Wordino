package it.unimib.wordino.main.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomWordApiService {
    @GET("word")
    Call <List<String>> getRandomWord(
            @Query("length") int length,
            @Query("lang") String language
    );
}
