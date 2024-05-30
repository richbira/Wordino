package it.unimib.wordino.main.util;

import java.util.List;

import it.unimib.wordino.main.model.wordmodel.Word;

public interface ResponseCallBackApi {
    void onSuccessRandom(String word);
    void onFailureRandom(String errorMessage);

    void onSuccessSpecific(List<Word> word);
    void onFailureSpecific(String errorMessage);


}
