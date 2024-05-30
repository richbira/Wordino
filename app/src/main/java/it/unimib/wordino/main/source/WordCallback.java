
package it.unimib.wordino.main.source;

import it.unimib.wordino.main.model.wordmodel.Word;

public interface WordCallback {
    void onSuccessFromRemoteSpecific(Word word);
    void onFailureFromRemoteSpecific(String exception);
    void onSuccessFromRemoteSpecificCheck(Word word);
    void onFailureFromRemoteSpecificCheck(String exception);
    void onSuccessFromRemoteRandom(String word);
    void onFailureFromRemoteRandom(String exception);
    void onSuccessFromLocal(Word word);
    void onFailureFromLocal(String exception);
}