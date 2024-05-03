package it.unimib.wordino.main.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.wordino.main.model.Word;
import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.ui.DailyFragment;
import it.unimib.wordino.main.util.ResponseCallBack;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificWordRepository implements ISpecificWordRepository {

    private static final String TAG = DailyFragment.class.getSimpleName();
    private final ResponseCallBack responseCallback;
    private final Application application;
    private final DictionaryWordApiService specificWordApiService;

    public SpecificWordRepository(Application application, ResponseCallBack responseCallBack){
        this.application = application;
        this.responseCallback = responseCallBack;
        this.specificWordApiService = ServiceLocator.getInstance().getSpecificWordApiService();
    }


    @Override
    public void fetchSpecificWord(String word) {
        Log.d(TAG, "Specific word fetch start: " + word);

        Call<List<Word>> wordResponseCall = specificWordApiService.getSpecificWord(word);

        wordResponseCall.enqueue(new Callback<List<Word>>() {

            @Override
            public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) { //TODO SETUP ROOM PER SALVARE L'OGGETTO
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<Word> specificWord = response.body();
                    Log.d(TAG, "Successful fetch of word: " + specificWord.get(0).getWord());
                    responseCallback.onSuccessSpecific(specificWord.get(0).getWord());
                } else {
                    responseCallback.onFailureSpecific("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                responseCallback.onFailureSpecific("Error wordNotFound");
            }
        });
    }
}
