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
                R.id.dailyFragment,
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


}