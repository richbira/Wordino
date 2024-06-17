package it.unimib.wordino.main.ui;


import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.unimib.wordino.R;
import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.repository.IWordRepositoryLD;
import it.unimib.wordino.main.util.ServiceLocator;


public class SocialLocalTabFragment extends Fragment {

    private ScoresViewModel highscoresModel;
    private Observer<List<Highscore>> highscoresObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IWordRepositoryLD wordRepositoryLD =
                ServiceLocator.getInstance().getWordRepositoryLD(requireActivity().getApplication());

        highscoresModel = new ViewModelProvider(
                requireActivity(),
                new HighscoresViewModelFactory(wordRepositoryLD)).get(ScoresViewModel.class);


        highscoresObserver = new Observer<List<Highscore>>() {
            @Override
            public void onChanged(@Nullable List<Highscore> highscores) {
                if (highscores != null) {
                    loadHighScores(highscores);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Error");
                    builder.setMessage("There are no recorded highscores");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_localtab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        highscoresModel.getHighscores().observe(getViewLifecycleOwner(), highscoresObserver);



    }

    private void loadHighScores(List<Highscore> highscoreList) {
        for (int i = 0; i < highscoreList.size(); i++ ){
            String scoreTextId = "scoreText" + (i + 1);
            String dateTextId = "dateText" + (i + 1);

            int scoreValue = highscoreList.get(i).getScore();
            String dateValue = highscoreList.get(i).getDate();
            dateValue = dateValue.substring(0,10);
            ((TextView) getView().findViewById(getResources().getIdentifier(scoreTextId, "id", PACKAGE_NAME))).setText(""+scoreValue);
            ((TextView) getView().findViewById(getResources().getIdentifier(dateTextId, "id", PACKAGE_NAME))).setText(""+dateValue);
        }
    }

}