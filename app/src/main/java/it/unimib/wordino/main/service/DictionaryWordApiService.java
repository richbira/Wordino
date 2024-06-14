package it.unimib.wordino.main.service;

import java.util.List;

import it.unimib.wordino.main.model.wordmodel.Word;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryWordApiService {
    @GET("{word}")
    Call <List<Word>> getSpecificWord(
            @Path("word") String word
    );
}