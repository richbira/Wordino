package it.unimib.wordino.main.source;

public abstract class BaseWordRemoteDataSource {
    protected WordCallback wordCallback;
    public void setWordCallback(WordCallback newsCallback) {
        this.wordCallback = newsCallback;
    }

    public abstract void getSpecificWord(String word);
    public abstract void getSpecificWordCheck(String word);

    public abstract void getRandomWord();

}
