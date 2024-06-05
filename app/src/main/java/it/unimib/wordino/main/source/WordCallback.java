
package it.unimib.wordino.main.source;

import java.util.List;

import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.wordmodel.Word;

public interface WordCallback {
    void onSuccessFromRemoteSpecific(Word word);
    void onFailureFromRemoteSpecific(String exception);
    void onSuccessFromRemoteSpecificCheck(Word word);
    void onFailureFromRemoteSpecificCheck(String exception);
    void onSuccessFromRemoteRandom(String word);
    void onFailureFromRemoteRandom(String exception);
    void onSuccessFromLocal(List<Highscore> highscores);
    void onFailureFromLocal(String exception);
}