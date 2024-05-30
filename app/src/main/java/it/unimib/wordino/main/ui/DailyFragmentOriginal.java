/*
package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.ENGLISH;
import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import it.unimib.wordino.R;
import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.repository.IRandomWordRepository;
import it.unimib.wordino.main.repository.ISpecificWordRepository;
import it.unimib.wordino.main.repository.RandomWordRepository;
import it.unimib.wordino.main.repository.SpecificWordRepository;
import it.unimib.wordino.main.util.ResponseCallBackApi;

/**
 * A simple {@link Fragment} subclass.
 * ù
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//TODO MANTENERE STATO DOPO SWITCH DI FRAGMENT
    /*

public class DailyFragment extends Fragment implements ResponseCallBackApi, View.OnClickListener {

    private static final String TAG = DailyFragment.class.getSimpleName();
    public View activeBox;
    public View progressBar;
    public int currentLine;
    public String tempWord = "spark";
    public Boolean fiveLetterWord = false;
    private IRandomWordRepository iRandomWordRepository;
    private ISpecificWordRepository iSpecificWordRepository;
    final String langConst = ENGLISH;

    private String winloss;

    private boolean goodFetchedWordFlag = false;

    public Animator flipAnimation1;
    public Animator flipAnimation2;
    public Animator flipAnimation3;
    public Animator flipAnimation4;
    public Animator flipAnimation5;



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

        iRandomWordRepository = new RandomWordRepository(requireActivity().getApplication(), this);
        iSpecificWordRepository = new SpecificWordRepository(requireActivity().getApplication(), this);

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
        goodFetchedWordFlag = false;

        progressBar = view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        iRandomWordRepository.fetchRandomWord(5, langConst); //QUI C'E' SOLO ENG

        flipAnimation1 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation2 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation3 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation4 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation5 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);



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

        if (winloss == null) {
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
        else {
            gameoverAlert(winloss);
        }
    }


    @Override
    public void onSuccessRandom(String word) {
        tempWord = word;
        Log.d(TAG, "tempWord settato a: " + tempWord);
        iSpecificWordRepository.fetchSpecificWord(word);
    }

    @Override
    public void onFailureRandom(String errorMessage){
        progressBar.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onSuccessSpecific(List<Word> word) {
        String wordString = word.get(0).getWord();
        Log.d(TAG, "checkedWord settato a: " + wordString);
        if (!goodFetchedWordFlag) {//Ramo per la chiamata api per trovare la parola da guessare
            if (!(Objects.equals(wordString, tempWord))) {//Caso in cui la parola fetchata dalla prima api non è valida
                iRandomWordRepository.fetchRandomWord(5, langConst);
            } else {// Caso in cui la parola viene convalidata dalla seconda api, la flag serve per far si che la seconda chiamata possa essere utilizzata per il check delle parole immesse.
                iSpecificWordRepository.saveWordInDatabase(word);
                progressBar.setVisibility(View.GONE);
                goodFetchedWordFlag = true;
            }
        }
        else { //Ramo per la chiamata api per checkare se la parola immessa esiste o meno
            Log.d(TAG, "La parola " + word + " esiste");
            tryWord(wordString);
        }
    }

    @Override
    public void onFailureSpecific(String errorMessage){
        Log.d(TAG, errorMessage);
        Log.d(TAG, "Parola non trovata nel check");
        if (!goodFetchedWordFlag){ //Ramo per la chiamata api per trovare la parola da guessare
            iRandomWordRepository.fetchRandomWord(5, langConst);
        } else { //Ramo per la chiamata api per checkare se la parola immessa esiste o meno
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Whooops");
            builder.setMessage("La parola immessa non esiste!");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

    /*  ----------------------------------------------------------------------------------------FUNZIONI DI LOGICA  ----------------------------------------------------------------------------------------*/
    /*  ----------------------------------------------------------------------------------------FUNZIONI DI LOGICA  ----------------------------------------------------------------------------------------*/
    /*  ----------------------------------------------------------------------------------------FUNZIONI DI LOGICA  ----------------------------------------------------------------------------------------*/
/*
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
                && i == 1){
            fiveLetterWord = true;
        } else if (currentLetterNum == 5
                && i == -1
                && !(((TextView) activeBox).getText().toString().isEmpty())) {

            ((TextView) activeBox).setText("");
            i = 0;
            fiveLetterWord = false;
        }

        nextLetterNum = currentLetterNum + i;
        String nextActiveBoxName = activeBoxName.substring(0, activeBoxName.length() - 1) + nextLetterNum;
        int nextActiveBoxId = getResources().getIdentifier(nextActiveBoxName, "id", PACKAGE_NAME);
        if (nextActiveBoxId != 0) { // Check if the next box exist
            activeBox = getView().findViewById(nextActiveBoxId);
        } else {
            // Handle the case when there's no next box (maybe reset to the first box or do nothing)
            Log.d(TAG, "No next word box available.");
        }
    }

    private void enterPressed(){

        String boxIndex;
        String guessedWord = "";

        if (fiveLetterWord) {

            for (int i = 1; i < 6; i++) {
                boxIndex = "word_" + currentLine + i;
                guessedWord += ((TextView) getView().findViewById(getResources().getIdentifier(boxIndex, "id", PACKAGE_NAME))).getText();
            }

            guessedWord = guessedWord.toLowerCase();

            iSpecificWordRepository.fetchSpecificWord(guessedWord); //chiamata api se la parola è valida
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Whooops");
            builder.setMessage("La parola non è di cinque lettere!");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    private String stringToCode(String guess) {
        String colorCodes = "";
        for (int i = 0; i < 5; i++) {
            Log.d(TAG, "Check: " + guess.charAt(i) + " - " + tempWord.charAt(i));
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

    private void tryWord(String guessedWord) {
        String code = stringToCode(guessedWord);
        changeBoxColor(code);
        Log.d(TAG, "BACK TO TRYWORD");
        changeKeyColor(code, guessedWord);

        //Check se la parola è corretta
        if (code.equals("ggggg")) {
            winloss = "win";
            gameoverAlert(winloss);

        } else if (currentLine != 5) {
            String nextLineBoxName = "word_" + ++currentLine + "1";
            activeBox = getView().findViewById(getResources().getIdentifier(nextLineBoxName, "id", PACKAGE_NAME));
            fiveLetterWord = false;
        } else {
            winloss = "loss";
            gameoverAlert(winloss);
        }
    }

    private void changeBoxColor(String code) {
        String boxId;

        //ANIMATION
        flipAnimation1.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "1", "id", PACKAGE_NAME)));
        flipAnimation2.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "2", "id", PACKAGE_NAME)));
        flipAnimation3.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "3", "id", PACKAGE_NAME)));
        flipAnimation4.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "4", "id", PACKAGE_NAME)));
        flipAnimation5.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "5", "id", PACKAGE_NAME)));
        flipAnimation1.start();
        flipAnimation2.start();
        flipAnimation3.start();
        flipAnimation4.start();
        flipAnimation5.start();

        for (int i = 1; i < 6; i++){
            boxId = "word_" + currentLine + i;
            TextView currentBox = (TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", PACKAGE_NAME));

            if (code.charAt(i-1) == 'g') {
                currentBox.setBackgroundResource(R.drawable.border_green);
            }else if (code.charAt(i-1) == 'y') {
                currentBox.setBackgroundResource(R.drawable.border_yellow);
            }else if (code.charAt(i-1) == 'b') {
                currentBox.setBackgroundResource(R.drawable.border_grey);
            }

        }

    }

    private void changeKeyColor(String code, String word) {
        String keyId;
        for (int i = 0; i < 5; i++) {
            keyId = "key_" + word.charAt(i);
            if (code.charAt(i) == 'g') {
                ((Button) getView().findViewById(getResources().getIdentifier(keyId, "id", PACKAGE_NAME))).setBackgroundColor(getResources().getColor(R.color.mygreen));  //TODO deprecated getcolor
            } else if (code.charAt(i) == 'y') {
                ((Button) getView().findViewById(getResources().getIdentifier(keyId, "id", PACKAGE_NAME))).setBackgroundColor(getResources().getColor(R.color.myyellow));  //TODO deprecated getcolor
            } else if (code.charAt(i) == 'b') {
                ((Button) getView().findViewById(getResources().getIdentifier(keyId, "id", PACKAGE_NAME))).setBackgroundColor(getResources().getColor(R.color.mygrey));  //TODO deprecated getcolor
            }
        }
    }

    private void resetGame() {
        String boxId;
        for (int i = 0; i < currentLine + 1; i++){
            for (int j = 1; j < 6; j++){
                boxId = "word_" + i + j;
                ((TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", PACKAGE_NAME))).setBackgroundResource(R.drawable.border_white);
                ((TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", PACKAGE_NAME))).setText("");
            }
        }
        activeBox = getView().findViewById(R.id.word_01);
        currentLine = 0;
        fiveLetterWord = false;
    }

    private void gameoverAlert(String winloss){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        switch(winloss){
            case "win":
                builder.setTitle("You Win!");
                builder.setMessage("You're a winner bro!");
                break;
            case "loss":
                builder.setTitle("You Lose!");
                builder.setMessage("You're a loser bro!");
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}


*/