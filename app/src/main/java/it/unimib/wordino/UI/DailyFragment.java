package it.unimib.wordino.UI;

import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import it.unimib.wordino.R;

/**
 * A simple {@link Fragment} subclass.
 * ù
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = DailyFragment.class.getSimpleName();
    public View activeBox;
    public int currentLine;
    public String tempWord = "SPARK";

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activeBox = view.findViewById(R.id.word_01);
        currentLine = 0;

        Button qButton = view.findViewById(R.id.key_q); qButton.setOnClickListener(this);
        Button wButton = view.findViewById(R.id.key_w); wButton.setOnClickListener(this);
        Button eButton = view.findViewById(R.id.key_e); eButton.setOnClickListener(this);
        Button rButton = view.findViewById(R.id.key_r); rButton.setOnClickListener(this);
        Button tButton = view.findViewById(R.id.key_t); tButton.setOnClickListener(this);
        Button yButton = view.findViewById(R.id.key_y); yButton.setOnClickListener(this);
        Button uButton = view.findViewById(R.id.key_u); uButton.setOnClickListener(this);
        Button iButton = view.findViewById(R.id.key_i); iButton.setOnClickListener(this);
        Button oButton = view.findViewById(R.id.key_o); oButton.setOnClickListener(this);
        Button pButton = view.findViewById(R.id.key_p); pButton.setOnClickListener(this);
        Button aButton = view.findViewById(R.id.key_a); aButton.setOnClickListener(this);
        Button sButton = view.findViewById(R.id.key_s); sButton.setOnClickListener(this);
        Button dButton = view.findViewById(R.id.key_d); dButton.setOnClickListener(this);
        Button fButton = view.findViewById(R.id.key_f); fButton.setOnClickListener(this);
        Button gButton = view.findViewById(R.id.key_g); gButton.setOnClickListener(this);
        Button hButton = view.findViewById(R.id.key_h); hButton.setOnClickListener(this);
        Button jButton = view.findViewById(R.id.key_j); jButton.setOnClickListener(this);
        Button kButton = view.findViewById(R.id.key_k); kButton.setOnClickListener(this);
        Button lButton = view.findViewById(R.id.key_l); lButton.setOnClickListener(this);
        Button zButton = view.findViewById(R.id.key_z); zButton.setOnClickListener(this);
        Button xButton = view.findViewById(R.id.key_x); xButton.setOnClickListener(this);
        Button cButton = view.findViewById(R.id.key_c); cButton.setOnClickListener(this);
        Button vButton = view.findViewById(R.id.key_v); vButton.setOnClickListener(this);
        Button bButton = view.findViewById(R.id.key_b); bButton.setOnClickListener(this);
        Button nButton = view.findViewById(R.id.key_n); nButton.setOnClickListener(this);
        Button mButton = view.findViewById(R.id.key_m); mButton.setOnClickListener(this);
        Button cancButton = view.findViewById(R.id.key_cancel); cancButton.setOnClickListener(this);
        Button enterButton = view.findViewById(R.id.key_enter); enterButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.key_q) updateActiveBox("Q");
        else if (id == R.id.key_w) updateActiveBox("W");
        else if (id == R.id.key_e) updateActiveBox("E");
        else if (id == R.id.key_r) updateActiveBox("R");
        else if (id == R.id.key_t) updateActiveBox("T");
        else if (id == R.id.key_y) updateActiveBox("Y");
        else if (id == R.id.key_u) updateActiveBox("U");
        else if (id == R.id.key_i) updateActiveBox("I");
        else if (id == R.id.key_o) updateActiveBox("O");
        else if (id == R.id.key_p) updateActiveBox("P");
        else if (id == R.id.key_a) updateActiveBox("A");
        else if (id == R.id.key_s) updateActiveBox("S");
        else if (id == R.id.key_d) updateActiveBox("D");
        else if (id == R.id.key_f) updateActiveBox("F");
        else if (id == R.id.key_g) updateActiveBox("G");
        else if (id == R.id.key_h) updateActiveBox("H");
        else if (id == R.id.key_j) updateActiveBox("J");
        else if (id == R.id.key_k) updateActiveBox("K");
        else if (id == R.id.key_l) updateActiveBox("L");
        else if (id == R.id.key_z) updateActiveBox("Z");
        else if (id == R.id.key_x) updateActiveBox("X");
        else if (id == R.id.key_c) updateActiveBox("C");
        else if (id == R.id.key_v) updateActiveBox("V");
        else if (id == R.id.key_b) updateActiveBox("B");
        else if (id == R.id.key_n) updateActiveBox("N");
        else if (id == R.id.key_m) updateActiveBox("M");
        else if (id == R.id.key_cancel) updateActiveBox("CANC");
        else if (id == R.id.key_enter) updateActiveBox("ENTER");
    }

    private void updateActiveBox(String text) {
        if (activeBox instanceof TextView) {
            if (Objects.equals(text, "CANC")) {
                nextWordBox(-1);
                ((TextView) activeBox).setText("");
            }else if (Objects.equals(text, "ENTER")) {
                enterPressed();
            }

            else {
                ((TextView) activeBox).setText(text);
                nextWordBox(1);
            }
        }
    }

    public void nextWordBox(int i) {

        String fullName = getResources().getResourceName(activeBox.getId());
        String activeBoxName = fullName.substring(fullName.lastIndexOf("/") + 1);
        int nextLetterNum;
        int currentLetterNum = Integer.parseInt(activeBoxName.substring(activeBoxName.length() - 1));
        //Check che serve per far funzionare il cancel sull'ultima box
        if (currentLetterNum == 5
                && i == -1
                && !(((TextView) activeBox).getText().toString().isEmpty())) {

            ((TextView) activeBox).setText("");
            i = 0;
        }

        nextLetterNum = currentLetterNum + i;
        String nextActiveBoxName = activeBoxName.substring(0, activeBoxName.length() - 1) + nextLetterNum;
        int nextActiveBoxId = getResources().getIdentifier(nextActiveBoxName, "id", "it.unimib.wordino"); //TODO defpackage non fisso ma fare funzionare il getpackage o buildconfig
        if (nextActiveBoxId != 0) { // Check if the next box exist
            activeBox = getView().findViewById(nextActiveBoxId);
        } else {
            // Handle the case when there's no next box (maybe reset to the first box or do nothing)
            Log.d(TAG, "No next word box available.");
        }
    }

     private void enterPressed(){
        //TODO METTERE CHECK SE LA PAROLA ESISTE O MENO
         String boxIndex;
         String guessedWord = "";
         for (int i = 1; i < 6; i++){
             boxIndex = "word_" + currentLine + i;
             guessedWord += ((TextView) getView().findViewById(getResources().getIdentifier(boxIndex, "id", "it.unimib.wordino"))).getText(); //TODO defpackage
         }
         Log.d(TAG, "Word: " + guessedWord);
         String code = checkWord(guessedWord);
         Log.d(TAG, "Code: " + code);

         changeLettersColor(code);

         String nextLineBoxName = "word_" + ++currentLine + "1";
         activeBox = getView().findViewById(getResources().getIdentifier(nextLineBoxName, "id", "it.unimib.wordino"));  //TODO defpackage
         Log.d(TAG, "Nuova Riga");
     }
     
     private String checkWord(String guess) {
        String colorCodes = "";
        for (int i = 0; i < 5; i++) {
            Log.d(TAG, "Iterazione " + i + ": " + guess.charAt(i) + " - " + tempWord.charAt(i));
            if (guess.charAt(i) == tempWord.charAt(i)) {
                colorCodes += "g";
            } else if (!(tempWord.indexOf(guess.charAt(i)) < 0)) {
                colorCodes += "y";
            } else {
                colorCodes += "b";
            }
        }
        return colorCodes;
     }

     private void changeLettersColor(String code) {
        String boxId;
        String gyb = "gyb";
        char g = gyb.charAt(0);
        char y = gyb.charAt(1); //TODO BRUTTO TROVARE ALTRO MODO
        char b = gyb.charAt(2);
        for (int i = 1; i < 6; i++){
            boxId = "word_" + currentLine + i;
            if (code.charAt(i-1) == g) {
                ((TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", "it.unimib.wordino"))).setBackgroundResource(R.drawable.border_green);  //TODO defpackage e deprecated getcolor
                Log.d(TAG, "green");
            }else if (code.charAt(i-1) == y) {
                ((TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", "it.unimib.wordino"))).setBackgroundResource(R.drawable.border_yellow);  //TODO defpackage e deprecated getcolor
                Log.d(TAG, "yellow");
            }else if (code.charAt(i-1) == b) {
                ((TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", "it.unimib.wordino"))).setBackgroundResource(R.drawable.border_grey);  //TODO defpackage e deprecated getcolor
                Log.d(TAG, "grey");
            }
        }
     }

}


