package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unimib.wordino.R;
import it.unimib.wordino.databinding.FragmentSettingsBinding;
import it.unimib.wordino.databinding.FragmentSocialBinding;
import it.unimib.wordino.main.model.Highscore;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.IWordRepositoryLD;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.ServiceLocator;

public class SocialFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = SocialFragment.class.getSimpleName();
    private FragmentSocialBinding binding;
    private ScoresViewModel highscoresModel;
    private Observer<List<Highscore>> highscoresObserver;
    private UserViewModel userViewModel;
    private String tokenId;
    private TextView gamePlayedText;
    private TextView currentStreakText;
    private TextView maxStreakText;
    private TextView winrateText;
    private HorizontalBarChart horizontalBarChart;

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

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getActivity().getApplication());

        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        tokenId = userViewModel.getLoggedUser().getIdToken();

        highscoresObserver = new Observer<List<Highscore>>() {
            @Override
            public void onChanged(@Nullable List<Highscore> highscores) {
                Log.d(TAG, "gameboard Onchanged");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSocialBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View statsView = binding.userStatistics;
        View leaderboardView = binding.localLeaderboard;

        gamePlayedText = view.findViewById(R.id.gamePlayedText);
        currentStreakText = view.findViewById(R.id.currentStreakText);
        maxStreakText = view.findViewById(R.id.maxStreakText);
        winrateText = view.findViewById(R.id.winrateText);


        horizontalBarChart = view.findViewById(R.id.horizontalBarChart);
        highscoresModel.getHighscores().observe(getViewLifecycleOwner(), highscoresObserver);

        binding.userStatisticsButton.setOnClickListener(v -> {
            statsView.setVisibility(View.VISIBLE);
            binding.userStatisticsButton.setBackgroundColor(getResources().getColor(R.color.mywhite));
            leaderboardView.setVisibility(View.GONE);
            binding.localLeaderboardButton.setBackgroundColor(getResources().getColor(R.color.maincyan));
        });

        binding.localLeaderboardButton.setOnClickListener(v -> {
            statsView.setVisibility(View.GONE);
            binding.userStatisticsButton.setBackgroundColor(getResources().getColor(R.color.maincyan));
            leaderboardView.setVisibility(View.VISIBLE);
            binding.localLeaderboardButton.setBackgroundColor(getResources().getColor(R.color.mywhite));
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        loadScores();
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

    public void loadScores() {
        userViewModel.fetchUserStats(tokenId); // Fetch new data
        userViewModel.getUserStats(tokenId).observe(getViewLifecycleOwner(), new Observer<UserStat>() {
            @Override
            public void onChanged(UserStat userStat) {
                if (userStat != null) {
                    gamePlayedText.setText(String.valueOf(userStat.getGamesPlayed())); //"Games Played:\n"
                    currentStreakText.setText(String.valueOf(userStat.getCurrentStreak())); //"Current Streak:\n" +
                    maxStreakText.setText(String.valueOf(userStat.getMaxStreak())); // "Max Streak:\n" +
                    int gamesPlayed = userStat.getGamesPlayed();
                    int gamesWon = userStat.getGamesWon();
                    int winrate = (gamesPlayed > 0) ? (int) Math.round(((double) gamesWon / gamesPlayed) * 100) : 0;
                    winrateText.setText(String.format(String.valueOf(winrate)) + "%"); //"Win Rate:\n%.2f%%",
                    setupHorizontalBarChart(userStat.getGuessDistribution());
                }
            }
        });
    }
    private void setupHorizontalBarChart(Map<String, Integer> guessDistribution) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        // Aggiungi dati in ordine inverso per far partire da 1 in alto e scendere fino a 6
        for (int i = 6; i >= 1; i--) {
            String key = String.valueOf(i);
            Integer value = guessDistribution.get(key);
            if (value != null) {
                entries.add(new BarEntry(6 - i, value)); // L'indice deve essere invertito
            } else {
                entries.add(new BarEntry(6 - i, 0));
            }
            labels.add(key); // Aggiunto "Guess" per maggiore chiarezza
        }

        BarDataSet dataSet = new BarDataSet(entries, "Guess Distribution");
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0)
                    return "";
                return String.valueOf((int) value);
            }
        });
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(dataSet);
        horizontalBarChart.setData(barData);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(18f);
        xAxis.setXOffset(0f);  // Rimuovi spaziatura orizzontale
        xAxis.setYOffset(0f);  // Rimuovi spaziatura verticale
        xAxis.setSpaceMin(0f);  // Riduce lo spazio minimo al bordo dell'asse
        xAxis.setSpaceMax(0f);  // Riduce lo spazio massimo al bordo dell'asse


        YAxis leftAxis = horizontalBarChart.getAxisLeft();
        leftAxis.setDrawLabels(false);  // Assicurati che le label siano disabilitate
        leftAxis.setDrawAxisLine(false);  // Assicurati che la linea dell'asse sia disabilitata
        leftAxis.setDrawGridLines(false);  // Assicurati che le griglie siano disabilitate

        YAxis rightAxis = horizontalBarChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);



        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.setClickable(false);
        horizontalBarChart.setLongClickable(false);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.setExtraOffsets(-10f, 0f, 0f, 0f);

        horizontalBarChart.invalidate();
    }
}