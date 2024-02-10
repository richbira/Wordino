package it.unimib.wordino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    //Set il primo active box
    public View activeBox;
    private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Game");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        activeBox = findViewById(R.id.word_01);

        //Bottone per tornare alla schermata principale
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        Button qButton = (Button) findViewById(R.id.key_q);
        qButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        //NO SO PERCHè SI BUGGA DOPO AVER CLICCATO IL BOTTONE Q, SI BLOCCA E NON PRENDE PIU' BOTTONI
        setContentView(R.layout.activity_game);

        int id = view.getId();
        if (id == R.id.backButton) {
            Log.d(TAG, "Bottone Back cliccato");
            Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
            view.getContext().startActivity(intent);
        } else if (id == R.id.key_q) {
            Log.d(TAG, "Bottone Q cliccato");
            TextView wordBox = (TextView) findViewById(activeBox.getId());
            wordBox.setText("Q");
            nextWordBox();
        }
        Log.d(TAG, "Fine if");
    }

    public void nextWordBox() {
        String fullName = getResources().getResourceName(activeBox.getId());
        String activeBoxName = fullName.substring(fullName.lastIndexOf("/") + 1);
        Log.d(TAG, "activebox " + activeBoxName);
        int nextLetterNum = Integer.parseInt(activeBoxName.substring(activeBoxName.length() - 1)) + 1;
        String nextActiveBoxName = activeBoxName.substring(0, activeBoxName.length() - 1) + Integer.toString(nextLetterNum);
        int nextActiveBoxId = getResources().getIdentifier(nextActiveBoxName, "id", getPackageName());
        activeBox = findViewById(nextActiveBoxId);
        Log.d(TAG, "nextactivebox " + nextActiveBoxName);
    }
}