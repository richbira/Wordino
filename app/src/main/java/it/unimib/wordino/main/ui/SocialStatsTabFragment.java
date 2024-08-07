package it.unimib.wordino.main.ui;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unimib.wordino.R;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.IWordRepositoryLD;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.ServiceLocator;

public class SocialStatsTabFragment extends Fragment {

    private static final String TAG = SocialStatsTabFragment.class.getSimpleName();

    private UserViewModel userViewModel;
    private String tokenId;
    private TextView gamePlayedText;
    private TextView currentStreakText;
    private TextView maxStreakText;
    private TextView winrateText;
    private HorizontalBarChart horizontalBarChart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getActivity().getApplication());

        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        tokenId = userViewModel.getLoggedUser().getIdToken();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_social_statstab, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onviewcreated socialtab");

/*
        userViewModel.fetchUserStats(tokenId); // Fetch new data
        userViewModel.getUserStats(tokenId).observe(getViewLifecycleOwner(), new Observer<UserStat>() {
            @Override
            public void onChanged(UserStat userStat) {
                Log.d(TAG, "Carica stats");
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
*/




        gamePlayedText = view.findViewById(R.id.gamePlayedText);
        currentStreakText = view.findViewById(R.id.currentStreakText);
        maxStreakText = view.findViewById(R.id.maxStreakText);
        winrateText = view.findViewById(R.id.winrateText);


        horizontalBarChart = view.findViewById(R.id.horizontalBarChart);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onresume socialtab");

        loadScores();
    }

    public void loadScores() {
        Log.d(TAG, "check1");
        if(userViewModel != null) {
            Log.d(TAG, "check2");
            userViewModel.fetchUserStats(tokenId); // Fetch new data

            userViewModel.getUserStats(tokenId).observe(getViewLifecycleOwner(), new Observer<UserStat>() {
                @Override
                public void onChanged(UserStat userStat) {
                    if (userStat != null) {
                        Log.d(TAG, "Carica stats");
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
        dataSet.setValueTextSize(20f);
        dataSet.setValueTextColor(Color.GRAY); //todo here

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
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(20f);


        YAxis leftAxis = horizontalBarChart.getAxisLeft();
        leftAxis.setDrawLabels(false);  // Assicurati che le label siano disabilitate
        leftAxis.setDrawAxisLine(false);  // Assicurati che la linea dell'asse sia disabilitata
        leftAxis.setDrawGridLines(false);  // Assicurati che le griglie siano disabilitate

        YAxis rightAxis = horizontalBarChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        Legend legend = horizontalBarChart.getLegend();
        legend.setTextSize(15f);
        legend.setTextColor(Color.GRAY);
        legend.setEnabled(true);


        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.setClickable(false);
        horizontalBarChart.setLongClickable(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.setExtraOffsets(-10f, 0f, 0f, 0f);

        horizontalBarChart.invalidate();
    }


}
