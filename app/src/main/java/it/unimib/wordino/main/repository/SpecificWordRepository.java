package it.unimib.wordino.main.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import it.unimib.wordino.main.database.WordinoDao;
import it.unimib.wordino.main.database.WordinoRoomDatabase;
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
    private final WordinoDao wordinoDao;

    public SpecificWordRepository(Application application, ResponseCallBack responseCallBack){
        this.application = application;
        this.responseCallback = responseCallBack;
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
                    responseCallback.onSuccessSpecific(specificWord);
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
    public void saveDataInDatabase(List<Word> wordList) {
        WordinoRoomDatabase.databaseWriteExecutor.execute(() -> {

            // Writes the words in the database and gets the associated primary keys
            List<Long> insertedWordsIds = wordinoDao.insertAll(wordList);
            for (int i = 0; i < wordList.size(); i++) {
                // Adds the primary key to the corresponding object Word just downloaded so that
                // if the user marks the news as favorite (and vice-versa), we can use its id
                // to know which news in the database must be marked as favorite/not favorite
                wordList.get(i).setWordId(insertedWordsIds.get(i));
            }
        });
    }

}
