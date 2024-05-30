package it.unimib.wordino.main.source;

import it.unimib.wordino.main.model.wordmodel.Word;

public abstract class BaseWordLocalDataSource {
    protected WordCallback wordCallback;
    public void setWordCallback(WordCallback newsCallback) {
        this.wordCallback = newsCallback;
    }
    public abstract void getWord(Word word);

    public abstract void insertWord(Word word);

}
