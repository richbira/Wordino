package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;

import android.app.AlertDialog;
import android.os.Bundle;

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

public class SocialFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = SocialFragment.class.getSimpleName();
    private ScoresViewModel highscoresModel;
    private Observer<List<Highscore>> highscoresObserver;


    public SocialFragment() {
        // Required empty public constructor
    }

    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
        return fragment;
    }

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
                Log.d(TAG, "gameboard Onchanged");
                if (highscores != null) {
                    loadHighScores(highscores);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Error");
                    builder.setMessage("There are no recorded highscores");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social, container, false);
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
            dateValue = dateValue.substring(0,9);
            ((TextView) getView().findViewById(getResources().getIdentifier(scoreTextId, "id", PACKAGE_NAME))).setText(""+scoreValue);
            ((TextView) getView().findViewById(getResources().getIdentifier(dateTextId, "id", PACKAGE_NAME))).setText(""+dateValue);
        }
    }

}