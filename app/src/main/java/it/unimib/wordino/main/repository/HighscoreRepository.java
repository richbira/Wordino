package it.unimib.wordino.main.repository;

import android.app.Application;
import android.util.Log;

import java.util.List;

import it.unimib.wordino.main.database.WordinoDao;
import it.unimib.wordino.main.database.WordinoRoomDatabase;
import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.ui.DailyFragment;
import it.unimib.wordino.main.util.ResponseCallBack;
import it.unimib.wordino.main.util.ServiceLocator;

public class HighscoreRepository implements IHighscoreRepository{
    private static final String TAG = DailyFragment.class.getSimpleName();
    private final Application application;
    private final WordinoDao wordinoDao;

    public HighscoreRepository(Application application){
        this.application = application;
        WordinoRoomDatabase wordinoRoomDatabase = ServiceLocator.getInstance().getWordinoDao(application);
        this.wordinoDao = wordinoRoomDatabase.wordinoDao();
    }
    @Override
    public void updateHighscores(Highscore score){ //todo creare date to string, creare pagina con statistiche.
        Log.d(TAG, "Highscores update start: " + score);


        int newScore = score.getScore();
        WordinoRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Highscore> oldList = wordinoDao.getHighscores();

            if (oldList.size() == 0){
                oldList.add(score);
                Log.d(TAG, "Aggiunto " + score);
            }
            else {

                int insertPosition = 0;
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


}
