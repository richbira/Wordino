package it.unimib.wordino.main.repository;

import java.util.List;

import it.unimib.wordino.main.model.Highscore;

public interface IHighscoreRepository {
    void updateHighscores(Highscore score);

    void loadHighscoreLadder();
}
