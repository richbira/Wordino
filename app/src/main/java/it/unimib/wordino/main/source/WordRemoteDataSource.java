package it.unimib.wordino.main.source;

import static it.unimib.wordino.main.util.Constants.ENGLISH;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.util.ResponseCallBackApi;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordRemoteDataSource extends BaseWordRemoteDataSource{
    private static final String TAG = WordRemoteDataSource.class.getSimpleName();

    private final DictionaryWordApiService specificWordApiService;
    private final RandomWordApiService randomWordApiService;


    public WordRemoteDataSource(){
        this.specificWordApiService = ServiceLocator.getInstance().getSpecificWordApiService();
        this.randomWordApiService = ServiceLocator.getInstance().getRandomWordApiService();

    }
    @Override
    public void getSpecificWord(String word) {
        Log.d(TAG, "INIZIO CALL API con parola " + word);


        Call<List<Word>> specificWordResponseCall = specificWordApiService.getSpecificWord(word);


        specificWordResponseCall.enqueue(new Callback<List<Word>>() {

            @Override
            public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) { //TODO SETUP ROOM PER SALVARE L'OGGETTO
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<Word> specificWord = response.body();
                    Log.d(TAG, "Successful fetch of word: " + specificWord.get(0).getWord());
                    wordCallback.onSuccessFromRemoteSpecific(specificWord.get(0));
                } else {
                    wordCallback.onFailureFromRemoteSpecific("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                wordCallback.onFailureFromRemoteSpecific("Error wordNotFound");
            }
        });
    }

    @Override
    public void getSpecificWordCheck(String word) {
        Log.d(TAG, "INIZIO CALL API check con parola " + word);


        Call<List<Word>> specificWordCheckResponseCall = specificWordApiService.getSpecificWord(word);


        specificWordCheckResponseCall.enqueue(new Callback<List<Word>>() {

            @Override
            public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) { //TODO SETUP ROOM PER SALVARE L'OGGETTO
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<Word> specificWord = response.body();
                    Log.d(TAG, "Successful fetch of word: " + specificWord.get(0).getWord());
                    wordCallback.onSuccessFromRemoteSpecificCheck(specificWord.get(0));
                } else {
                    wordCallback.onFailureFromRemoteSpecificCheck("La parola non esiste");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                wordCallback.onFailureFromRemoteSpecificCheck("Error wordNotFound");
            }
        });
    }


    @Override
    public void getRandomWord(){
        Log.d(TAG,"Random word fetch start");
        Call<List<String>> randomWordResponseCall = randomWordApiService.getRandomWord(5, ENGLISH);

        randomWordResponseCall.enqueue(new Callback<List<String>>() { //todo forse estrarre la logica in una nuova classe (videolezione livedata 26:00)

            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<String> newWord = response.body();
                    Log.d(TAG, "Successful fetch of random word " + newWord.get(0));
                    wordCallback.onSuccessFromRemoteRandom(newWord.get(0));
                } else {
                    wordCallback.onFailureFromRemoteRandom("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                wordCallback.onFailureFromRemoteRandom("Errore nella chiamata API 2" + t);
            }
        });
    }
}

