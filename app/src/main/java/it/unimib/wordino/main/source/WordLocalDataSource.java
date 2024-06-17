package it.unimib.wordino.main.source;

import android.util.Log;

import java.util.List;

import it.unimib.wordino.main.database.WordinoDao;
import it.unimib.wordino.main.database.WordinoRoomDatabase;
import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.ui.DailyFragment;

public class WordLocalDataSource extends BaseWordLocalDataSource {
    private static final String TAG = WordLocalDataSource.class.getSimpleName();
    private final WordinoDao wordinoDao;

    public WordLocalDataSource(WordinoRoomDatabase wordinoRoomDatabase) {
        this.wordinoDao = wordinoRoomDatabase.wordinoDao();

    }
    @Override
    public void getWord(Word word){

    }
    @Override
    public void insertWord(Word word){
        wordinoDao.insertWord(word); //todo cambiare da wordlist a word
    }

    public void updateHighscores(Highscore score){ // forse usare date come dataclass.
        Log.d(TAG, "Highscores update start: " + score);


        int newScore = score.getScore();
        WordinoRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Highscore> oldList = wordinoDao.getHighscores();

            if (oldList.size() == 0){
                oldList.add(score);
                Log.d(TAG, "Aggiunto " + score);
            }
            else {

                int insertPosition = oldList.size();
                for (int i = 0; i < oldList.size(); i++) {
                    int j = oldList.size() - i - 1; //contatore inverso
                    int iScore = oldList.get(j).getScore();
                    Log.d(TAG, j + " - " + oldList.get(j).getScore());
                    if (iScore < newScore) {
                        insertPosition = j;
                    }
                }
                oldList.add(insertPosition, score);
                Log.d(TAG, "Aggiunto " + score);
                while (oldList.size() > 5) {
                    oldList.remove(5);
                }
            }

            for (int i = 0; i < oldList.size(); i++){
                oldList.get(i).setScoreId(i + 1);
            }
            wordinoDao.deleteAll();
            wordinoDao.insertScores(oldList);
            Log.d(TAG, "Inserito nel db");
        });
    }

    public void loadHighscoreLadder(){ //todo

        WordinoRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Highscore> highscoresList = wordinoDao.getHighscores();
            if (highscoresList != null) {
                wordCallback.onSuccessFromLocal(highscoresList);
            } else {
                wordCallback.onFailureFromLocal("Oggetto nullo");
            }
        });


    }
}
