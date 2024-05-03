package it.unimib.wordino.main.util;

public interface ResponseCallBack {
    void onSuccessRandom(String word);
    void onFailureRandom(String errorMessage);

    void onSuccessSpecific(String word);
    void onFailureSpecific(String errorMessage);

}
