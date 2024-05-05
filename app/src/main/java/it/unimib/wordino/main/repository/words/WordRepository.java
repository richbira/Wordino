package it.unimib.wordino.main.repository.words;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.wordino.main.ui.DailyFragment;
import it.unimib.wordino.main.util.ResponseCallBack;

import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordRepository implements IWordRepository {

    private static final String TAG = DailyFragment.class.getSimpleName();

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
        Log.d(TAG, "Fetch start");
        Call<List<String>> wordResponseCall = randomWordApiService.getWord(length, lang); //HERE

        wordResponseCall.enqueue(new Callback<List<String>>() {

            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<String> newWord = response.body();
                    responseCallback.onSuccess(newWord.get(0)); //TODO fare meglio!
                } else {
                    responseCallback.onFailure("Errore nella chiamata API 1 " + (response.body() != null) + response.isSuccessful());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                responseCallback.onFailure("Errore nella chiamata API 2" + t);
            }
        });
        Log.d(TAG, "wordResponseCall canceled? " + wordResponseCall.isCanceled());
        Log.d(TAG, "wordResponseCall executed? " + wordResponseCall.isExecuted());

    }

}
