package it.unimib.wordino.main.util;

public interface ResponseCallBack {
    void onSuccess(String word);
    void onFailure(String errorMessage);

}
