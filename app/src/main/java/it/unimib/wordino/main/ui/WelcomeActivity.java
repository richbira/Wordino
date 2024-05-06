package it.unimib.wordino.main.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;

import it.unimib.wordino.R;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button howToPlayButton = findViewById(R.id.howToPlayButton);
        howToPlayButton.setOnClickListener(view -> {
            Log.d(TAG, "how to play clicked");
            startGame();

        });

        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(view -> {
            Log.d(TAG, "login clicked");
            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        Button playButton = findViewById(R.id.PlayButton);
        playButton.setOnClickListener(view -> {
            startGame();
        });
    }

    public void startGame() {
        Intent playIntent = new Intent(WelcomeActivity.this, GameActivity.class);
        startActivity(playIntent);
    }
    //TODO: Implement functionality for orientation fix
}
