package it.unimib.wordino.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

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
            startGame("HOW_TO_PLAY");

        });

        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(view -> {
            Log.d(TAG, "login clicked");
            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        Button playButton = findViewById(R.id.PlayButton);
        playButton.setOnClickListener(view -> {
            Log.d(TAG, "Play clicked");
            startGame("PLAY");
        });
    }

    public void startGame(String mode) {
        Intent playIntent = new Intent(WelcomeActivity.this, GameActivity.class);
        playIntent.putExtra("mode", mode);
        startActivity(playIntent);
    }
    //TODO: Implement functionality for unlimited play and orientation fix
}
