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
    private static boolean unlimitedPlay = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "WelcomeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button howToPlayButton = findViewById(R.id.howToPlayButton);
        howToPlayButton.setOnClickListener(item ->
                Log.d(TAG, "how to play cliccato"));
        //TODO: Apre il game activity però ci mettiamo la schermata con le regole - penso si debba usare i fragments

        Button LoginButton = findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(item -> {
            Log.d(TAG, "login cliccato");

            Intent LoginIntent = new Intent(this, LoginActivity.class);
            startActivity(LoginIntent);
        });

        Button PlayButton = findViewById(R.id.PlayButton);
        PlayButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "Play cliccato");

            Switch unlimitedSwitch = findViewById(R.id.unlimitedSwitch);

            Intent PlayIntent = new Intent(view.getContext(), GameActivity.class);
            PlayIntent.putExtra("switchUnlimited", unlimitedSwitch.isChecked());
            view.getContext().startActivity(PlayIntent);}
        });
        //TODO implementare bottoni
        // TODO Se la parola del giorno è già stata indovinata e si prova a giocare senza unlimited -> ci deve essere un blocco

    }
    //TODO fix orientation preview
}