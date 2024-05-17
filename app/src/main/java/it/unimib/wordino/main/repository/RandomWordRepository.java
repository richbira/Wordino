package it.unimib.wordino.main.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.wordino.main.ui.DailyFragment;
import it.unimib.wordino.main.util.ResponseCallBackApi;

import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomWordRepository implements IRandomWordRepository {

    private static final String TAG = DailyFragment.class.getSimpleName();
    private final ResponseCallBackApi responseCallbackApi;
    private final Application application;
    private final RandomWordApiService randomWordApiService;

    private String randomWord;


    public RandomWordRepository(Application application, ResponseCallBackApi responseCallBackApi){
        this.application = application;
        this.responseCallbackApi = responseCallBackApi;
        this.randomWordApiService = ServiceLocator.getInstance().getRandomWordApiService();
    }

    public String getRandomWord(){
        return this.randomWord;
    }

    /*@Override
    public void fetchRandomWord(int length, String lang){
        Log.d(TAG, lang + "Random word fetch start");
        Call<List<String>> wordResponseCall = randomWordApiService.getRandomWord(length, lang);
        try
        {
            Response<List<String>> response = wordResponseCall.execute();
            List<String> newWord = response.body();
            randomWord = newWord.get(0);
            Log.d(TAG, "Successful fetch of random word " + newWord.get(0)); //TODO FINIRE QUI
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }*/

    @Override
    public void fetchRandomWord(int length, String lang){
        Log.d(TAG, lang + "Random word fetch start");
        Call<List<String>> wordResponseCall = randomWordApiService.getRandomWord(length, lang);

        wordResponseCall.enqueue(new Callback<List<String>>() {

            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<String> newWord = response.body();
                    Log.d(TAG, "Successful fetch of random word " + newWord.get(0));
                    responseCallbackApi.onSuccessRandom(newWord.get(0));
                } else {
                    responseCallbackApi.onFailureRandom("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                responseCallbackApi.onFailureRandom("Errore nella chiamata API 2" + t);
            }
        });
    }

}
