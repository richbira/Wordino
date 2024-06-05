package it.unimib.wordino.main.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.repository.IWordRepositoryLD;

public class ScoresViewModel extends ViewModel {
    private static final String TAG = ScoresViewModel.class.getSimpleName();

    private final IWordRepositoryLD wordRepositoryLD;
    private MutableLiveData<List<Highscore>> highscores;

    public ScoresViewModel(IWordRepositoryLD wordRepositoryLD){
        this.wordRepositoryLD = wordRepositoryLD;

    }

    public MutableLiveData<List<Highscore>> getHighscores(){

        return highscores = wordRepositoryLD.getHighscores();
    }


}
