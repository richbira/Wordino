package it.unimib.wordino.main.repository;

import androidx.lifecycle.MutableLiveData;

import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.model.wordmodel.Word;

public interface IWordRepositoryLD {
    void fetchSpecificWord(String word);
    MutableLiveData<Result> fetchSpecificWordCheck(String word);
    MutableLiveData<Result> fetchRandomWord();
    void saveWordInDatabase(Word word);
}
