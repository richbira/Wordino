package it.unimib.wordino.main.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.model.wordmodel.Word;

public interface IWordRepositoryLD {
    void fetchSpecificWord(String word);
    MutableLiveData<Result> fetchSpecificWordCheck(String word);
    MutableLiveData<Result> fetchRandomWord();

    void saveHighscore(Highscore highscore);
    MutableLiveData<List<Highscore>> getHighscores();

    MutableLiveData<String> getWord();
    MutableLiveData<Date> getDate();

    void setWordOfTheDay(String word);
     MutableLiveData<Result> getWordFromFirebase();
}
