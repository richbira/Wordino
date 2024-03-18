package it.unimib.wordino.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.wordino.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    //Set il primo active box
    public View activeBox;
    private static final String TAG = GameActivity.class.getSimpleName();
    private String mode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Game");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Toolbar toolbar = findViewById(R.id.top_appbar);
        //setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.bottom_nav);
        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.accountFragment, R.id.dailyFragment,
                R.id.settingsFragment, R.id.socialFragment, R.id.trainingFragment).build();

        // For the Toolbar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);


        //Aggiungo flag "Unlimited" che gli passo dalla schermata home
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean unlimitedPlayIsChecked = getIntent().getBooleanExtra("switchUnlimited", false);
            mode = getIntent().getStringExtra("mode");
            Log.d(TAG, "unlimitedPlay: " + unlimitedPlayIsChecked);
            Log.d(TAG, "mode: " + mode); // Se questo flag è true mostro Fragment con le regole
        }

        if (mode.equals("PLAY")) {
            // Load the GameFragment since the mode is Play
            loadGameFragment();
        }else if(mode.equals("HOW_TO_PLAY")){
            loadHowToPlayFragment();
        }


        //activeBox = findViewById(R.id.word_01);



        //Button Button = (Button) findViewById(R.id.key_q);
        //Button.setOnClickListener(this);

    }
    private void loadGameFragment() {
        GameFragment gameFragment = new GameFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, gameFragment)
                .commit();
    }
    private void loadHowToPlayFragment() {
        HowToPlayFragment howToPlayFragment = new HowToPlayFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, howToPlayFragment)
                .commit();
    }
    /*
    Gestione bottoni cliccati
     */
    @Override
    public void onClick(View view) {
    }
    private void updateActiveBox(String text) {
        if (activeBox instanceof TextView) {
            ((TextView) activeBox).setText(text);
            nextWordBox();
        }
    }
    public void nextWordBox() {
        String fullName = getResources().getResourceName(activeBox.getId());
        String activeBoxName = fullName.substring(fullName.lastIndexOf("/") + 1);
        int nextLetterNum = Integer.parseInt(activeBoxName.substring(activeBoxName.length() - 1)) + 1;
        String nextActiveBoxName = activeBoxName.substring(0, activeBoxName.length() - 1) + nextLetterNum;
        int nextActiveBoxId = getResources().getIdentifier(nextActiveBoxName, "id", getPackageName());
        if (nextActiveBoxId != 0) { // Check if the next box exists
            activeBox = findViewById(nextActiveBoxId);
        } else {
            // Handle the case when there's no next box (maybe reset to the first box or do nothing)
            Log.d(TAG, "No next word box available.");
        }
    }

}