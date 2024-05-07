package it.unimib.wordino.main.util;

import java.util.List;

import it.unimib.wordino.main.model.Word;

public interface ResponseCallBack {
    void onSuccessRandom(String word);
    void onFailureRandom(String errorMessage);

    void onSuccessSpecific(List<Word> word);
    void onFailureSpecific(String errorMessage);

}
