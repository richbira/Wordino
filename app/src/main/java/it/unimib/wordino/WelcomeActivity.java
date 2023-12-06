package it.unimib.wordino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.AccessController;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "WelcomeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button howToPlayButton = findViewById(R.id.howToPlayButton);
        howToPlayButton.setOnClickListener(item ->
                Log.d(TAG, "how to play cliccato"));

        Button LoginButton = findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(item -> {
            Log.d(TAG, "login cliccato");

            // TODO implementare isEmailOk(email), isPasswordOK(password)
            Intent intent = new Intent(item.getContext(), LoginActivity.class);
            item.getContext().startActivity(intent);
        });

        Button PlayButton = findViewById(R.id.PlayButton);
        PlayButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "Play cliccato");
            Intent intent = new Intent(view.getContext(), GameActivity.class);
            view.getContext().startActivity(intent);}
        });
        //TODO implementare bottoni

    }
    //TODO fix orientation preview
}