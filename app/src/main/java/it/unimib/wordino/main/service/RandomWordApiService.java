package it.unimib.wordino.main.service;

import it.unimib.wordino.main.model.WordApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomWordApiService { //TODO esportare i parametri
    @GET("word")
    Call <WordApiResponse> getWord(
            @Query("length") int length,
            @Query("lang") String language
    );
}
