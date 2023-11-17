package it.unimib.wordino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = GameActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Game");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ;
    }
}