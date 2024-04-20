package it.unimib.wordino.main.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import it.unimib.wordino.main.model.WordApiResponse;
import it.unimib.wordino.main.util.ResponseCallBack;

import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordRepository implements IWordRepository {

    private final ResponseCallBack responseCallback;
    private final Application application;

    private final RandomWordApiService randomWordApiService;


    public WordRepository(Application application, ResponseCallBack responseCallBack){
        this.application = application;
        this.responseCallback = responseCallBack;
        this.randomWordApiService = ServiceLocator.getInstance().getWordApiService();
    }

    @Override
    public void fetchWord(int length, String lang){

        Call<WordApiResponse> wordResponseCall = randomWordApiService.getWord(length, lang);

        wordResponseCall.enqueue(new Callback<WordApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<WordApiResponse> call, @NonNull Response<WordApiResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    String newWord = response.body().getWord();
                    responseCallback.onSuccess(newWord);
                } else {
                    responseCallback.onFailure("Errore nella chiamata API 1 " + (response.body() != null) + response.isSuccessful());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WordApiResponse> call, @NonNull Throwable t) {
                responseCallback.onFailure("Errore nella chiamata API 2" + t);
            }
        });
    }

}
