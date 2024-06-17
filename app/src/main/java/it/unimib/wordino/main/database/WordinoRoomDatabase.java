package it.unimib.wordino.main.database;

import static it.unimib.wordino.main.util.Constants.WORDINO_DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.wordmodel.Word;

@Database(entities = {Word.class, Highscore.class}, version = 4, exportSchema = false)
public abstract class WordinoRoomDatabase extends RoomDatabase {
    public abstract WordinoDao wordinoDao();
    private static volatile WordinoRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static WordinoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordinoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordinoRoomDatabase.class, WORDINO_DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
