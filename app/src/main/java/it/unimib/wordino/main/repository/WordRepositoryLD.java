package it.unimib.wordino.main.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.source.BaseWordLocalDataSource;
import it.unimib.wordino.main.source.BaseWordRemoteDataSource;
import it.unimib.wordino.main.source.WordCallback;

public class WordRepositoryLD implements IWordRepositoryLD, WordCallback {
    private static final String TAG = WordRepositoryLD.class.getSimpleName();


    private final MutableLiveData<Result> guessedWord;
    private final MutableLiveData<Result> randomWord;
    private final MutableLiveData<List<Highscore>> highscores;
    private final BaseWordRemoteDataSource wordRemoteDataSource;
    private final BaseWordLocalDataSource wordLocalDataSource;

    private boolean goodFetchedWordFlag = false;
    public String tempWord;

    public WordRepositoryLD(BaseWordRemoteDataSource wordRemoteDataSource,
                            BaseWordLocalDataSource wordLocalDataSource) {

        guessedWord = new MutableLiveData<>();
        randomWord = new MutableLiveData<>();
        highscores = new MutableLiveData<>();
        this.wordRemoteDataSource = wordRemoteDataSource;
        this.wordLocalDataSource = wordLocalDataSource;
        this.wordRemoteDataSource.setWordCallback(this);
        this.wordLocalDataSource.setWordCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchRandomWord(){
        wordRemoteDataSource.getRandomWord();
        return randomWord;
    }
    @Override
    public void fetchSpecificWord(String word) {
        wordRemoteDataSource.getSpecificWord(word);
    }
    @Override
    public MutableLiveData<Result> fetchSpecificWordCheck(String word) {
        wordRemoteDataSource.getSpecificWordCheck(word);
        return guessedWord;
    }

    public void saveHighscore(Highscore highscore){
        wordLocalDataSource.updateHighscores(highscore);
    }

    public MutableLiveData<List<Highscore>> getHighscores(){
        wordLocalDataSource.loadHighscoreLadder();
        return highscores;
    }


    public void onSuccessFromRemoteRandom(String word){
        tempWord = word;
        Log.d(TAG, "tempWord settato a: " + tempWord);
        fetchSpecificWord(word);
    }
    public void onFailureFromRemoteRandom(String exception){
        Log.d(TAG, exception);
    }

    @Override
    public void onSuccessFromRemoteSpecific(Word word) {
        String wordString = word.getWord();
        Log.d(TAG, "checkedWord settato a: " + wordString);

        if (!(Objects.equals(wordString, tempWord))) {//Caso in cui la parola fetchata dalla prima api non è valida
            wordRemoteDataSource.getRandomWord();
        } else {
            //saveWordInDatabase(word); TODO RISOLVERE QUESTO
            randomWord.postValue(new Result.Success(wordString));
            Log.d(TAG, "Postata la parola " + wordString + " come randomWord");
        }
    }
    @Override
    public void onFailureFromRemoteSpecific(String exception){
        Log.d(TAG, exception);
        wordRemoteDataSource.getRandomWord(); // fa ripartire il random anche in caso di errore
    }

    @Override
    public void onSuccessFromRemoteSpecificCheck(Word word) {
        String wordString = word.getWord();
        guessedWord.postValue(new Result.Success(wordString));
        Log.d(TAG, "La parola " + wordString + " esiste");
    }
    @Override
    public void onFailureFromRemoteSpecificCheck(String exception){
        Log.d(TAG, exception);
        guessedWord.postValue(new Result.Error("CHECKERROR"));
    }

    @Override
    public void onSuccessFromLocal(List<Highscore> newhighscores){
        highscores.postValue(newhighscores); //todo fare forse il wrapper per highscore per vedere se è un error o meno
    }
    @Override
    public void onFailureFromLocal(String exception){
        Log.d(TAG, "Errore : " + exception);
    }

    public MutableLiveData<String> getWord(){
        return wordRemoteDataSource.getWord();
    }
    public MutableLiveData<Date> getDate(){
        return wordRemoteDataSource.getDate();
    }

    public void setWordOfTheDay(String word){
        wordRemoteDataSource.setWordOfTheDay(word);
    }
    public  void getWordFromFirebase(String word){
        wordRemoteDataSource.getWordFromFirebase(word);
    }

}
