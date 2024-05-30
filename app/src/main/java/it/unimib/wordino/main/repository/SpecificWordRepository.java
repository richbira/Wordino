package it.unimib.wordino.main.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.wordino.main.database.WordinoDao;
import it.unimib.wordino.main.database.WordinoRoomDatabase;
import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.ui.DailyFragment;
import it.unimib.wordino.main.util.ResponseCallBackApi;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificWordRepository implements ISpecificWordRepository {

    private static final String TAG = DailyFragment.class.getSimpleName();
    private final ResponseCallBackApi responseCallbackApi;
    private final Application application;
    private final DictionaryWordApiService specificWordApiService;
    private final WordinoDao wordinoDao;

    public SpecificWordRepository(Application application, ResponseCallBackApi responseCallBackApi){
        this.application = application;
        this.responseCallbackApi = responseCallBackApi;
        this.specificWordApiService = ServiceLocator.getInstance().getSpecificWordApiService();
        WordinoRoomDatabase wordinoRoomDatabase = ServiceLocator.getInstance().getWordinoDao(application);
        this.wordinoDao = wordinoRoomDatabase.wordinoDao();
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
                    responseCallbackApi.onSuccessSpecific(specificWord);
                } else {
                    responseCallbackApi.onFailureSpecific("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                responseCallbackApi.onFailureSpecific("Error wordNotFound");
            }
        });
    }
    public void saveWordInDatabase(List<Word> wordList) {
        WordinoRoomDatabase.databaseWriteExecutor.execute(() -> {

            // Writes the words in the database and gets the associated primary keys
            //List<Long> insertedWordsIds = wordinoDao.insertAll(wordList); todo attenzione qui ho tolto
            for (int i = 0; i < wordList.size(); i++) {
                // Adds the primary key to the corresponding object Word just downloaded
                //wordList.get(i).setWordId(insertedWordsIds.get(i));
            }
        });
    }

}
