package it.unimib.wordino.main.database;

import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.wordmodel.Word;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordinoDao {
    //@Query("SELECT * FROM word")

    @Insert
    Long insertWord(Word word);

    @Delete
    void delete(Word word);

    @Query("SELECT * FROM highscore ORDER BY score DESC")
    List<Highscore> getHighscores();

    @Insert
    void insertScores(List<Highscore> scores);

    @Query("DELETE FROM highscore")
    void deleteAll();
}

