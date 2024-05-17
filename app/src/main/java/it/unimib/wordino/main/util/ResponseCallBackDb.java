package it.unimib.wordino.main.util;

import java.util.List;

import it.unimib.wordino.main.model.Highscore;

public interface ResponseCallBackDb {

    void onSuccess(List<Highscore> highscores);

    void onFailure(String errorMessage);
}
