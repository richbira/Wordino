package it.unimib.wordino.main.source;

import androidx.lifecycle.MutableLiveData;

import java.util.Date;

public abstract class BaseWordRemoteDataSource {
    protected WordCallback wordCallback;
    public void setWordCallback(WordCallback newsCallback) {
        this.wordCallback = newsCallback;
    }
    public abstract void getSpecificWord(String word);
    public abstract void getSpecificWordCheck(String word);
    public abstract void getRandomWord();
    public abstract MutableLiveData<String> getWord();
    public abstract MutableLiveData<Date> getDate();

    public abstract void setWordOfTheDay(String word);

    public abstract void getWordFromFirebase(String word);

}
