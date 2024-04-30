package it.unimib.wordino.main.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.wordino.R;
public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    //Set il primo active box
    public View activeBox;

    public static String lang;
    private static final String TAG = GameActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Game");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Toolbar toolbar = findViewById(R.id.top_appbar);
        //setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.accountFragment, R.id.dailyFragment,
                R.id.settingsFragment, R.id.socialFragment, R.id.trainingFragment).build();

        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);


        //Aggiungo flag "language" che gli passo dalla schermata home
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            lang = getIntent().getStringExtra("language");
            Log.d(TAG, "Language: " + lang);
        }
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