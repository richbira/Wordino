package it.unimib.wordino.main.database;

import it.unimib.wordino.main.model.Word;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface WordinoDao {
    @Query("SELECT * FROM word")

    @Delete
    void delete(Word word);
}
