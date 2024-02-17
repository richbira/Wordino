package it.unimib.wordino.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.unimib.wordino.R;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    //Set il primo active box
    public View activeBox;
    private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Game");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Aggiungo flag "Unlimited" che gli passo dalla schermata home
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean unlimitedPlayIsChecked = getIntent().getBooleanExtra("switchUnlimited", false);
            String mode = getIntent().getStringExtra("mode");
            Log.d(TAG, "unlimitedPlay: " + unlimitedPlayIsChecked);
            Log.d(TAG, "mode: " + mode); // Se questo flag è true mostro Fragment con le regole
        }


        //activeBox = findViewById(R.id.word_01);

        //Bottone per tornare alla schermata principale
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        //Button qButton = (Button) findViewById(R.id.key_q);
        //Button.setOnClickListener(this);

    }
    /*
    Gestione bottoni cliccati
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.backButton) { // RR -> Forse conviene usare switch?
            Log.d(TAG, "Bottone Back cliccato");
            Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.key_q) {
            Log.d(TAG, "Bottone Q cliccato");
            TextView wordBox = (TextView) findViewById(activeBox.getId());
            updateActiveBox("Q");
        }
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