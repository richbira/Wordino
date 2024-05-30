package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;

import android.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import it.unimib.wordino.R;
import it.unimib.wordino.main.model.GameBoard;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.repository.IWordRepositoryLD;

public class GameBoardViewModel extends ViewModel {

    private static final String TAG = GameBoardViewModel.class.getSimpleName();

    private final IWordRepositoryLD wordRepositoryLD;

    private MutableLiveData<Result> guessedWord;
    private MutableLiveData<Result> dailyWord;
    private MutableLiveData<GameBoard> boardState = new MutableLiveData<>(new GameBoard());

    public int currentLine = 0;
    public int currentLetter = 0;
    public Boolean fiveLetterWord = false;
    private String winloss;

    private boolean goodFetchedWordFlag = false;

    public GameBoardViewModel(IWordRepositoryLD wordRepositoryLD){
        this.wordRepositoryLD = wordRepositoryLD;

    }

    public MutableLiveData<GameBoard> getGameBoard() {
        if (boardState == null) {
            boardState = new MutableLiveData<>(new GameBoard());
            Log.d(TAG, "inizializzata gameboard");
        }
        return boardState;
    }
    public MutableLiveData<Result> getGuessedWord(){
        if (guessedWord == null) {
            guessedWord = new MutableLiveData<Result>();
            Log.d(TAG, "inizializzata guessedWord");
        }
        return guessedWord;
    }

    public void fetchRandomWord(){
        //todo mettere check se la daily è scaduta
        dailyWord = wordRepositoryLD.fetchRandomWord();
    };

    private void checkRealWord(String word){  //todo forse mettere altri due onsucces e onfailure appositi per questa chiamata
        guessedWord = wordRepositoryLD.fetchSpecificWordCheck(word);
    }



    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------

    public void updateGameBoard(String text) {

            if (Objects.equals(text, "CANC")) {
                findNextLetter(-1);
                boardState.getValue().changeValue(currentLine, currentLetter, null); //todo get value potrebbe essere sbagliato
            }else if (Objects.equals(text, "ENTER")) {
                enterPressed();
            }

            else {
                boardState.getValue().changeValue(currentLine, currentLetter, text + "w"); //todo get value potrebbe essere sbagliato
                findNextLetter(1);
            }

            boardState.setValue(this.boardState.getValue());

    }

    public void findNextLetter(int i) {

        int nextLetter;
        Log.d(TAG, "CurrentLetter = " + currentLetter);

        //Check che serve per far funzionare il cancel sull'ultima box
        if (currentLetter == 4
                && i == 1){
            fiveLetterWord = true;
            Log.d(TAG, "Fiveletterword!");
        } else if (currentLetter == 4
                && i == -1
                && !(boardState.getValue().getValue(currentLine, currentLetter) == null)) {
            Log.d(TAG, "5 lettere + canc");
            boardState.getValue().changeValue(currentLine, currentLetter, null);
            i = 0;
            fiveLetterWord = false;
        }

        nextLetter = currentLetter + i;

        if (0 <= nextLetter && nextLetter < 5){ //Check se la nuova lettera è compresa fra 0 e 4
            currentLetter = nextLetter;
        }
        Log.d(TAG, "NextLetter = " + currentLetter);
    }

    private void enterPressed(){
        Log.d(TAG, "INIZIO ENTERPRESSED");
        String tempWord = "";

        if (fiveLetterWord) {

            for (int i = 0; i < 5; i++) {
                tempWord += boardState.getValue().getValue(currentLine, i).charAt(0);
            }

            tempWord = tempWord.toLowerCase(); // forse non serve

            checkRealWord(tempWord); //todo prob fare un observer sulla parola fetchata nel repo in modo che quando cambi si possa fare partire il processo per matchare la parola guessata con quella da guessare
            // idea: mettere observer nel ui su tempword, in modo che quando arriva il cambiamento possa rilanciare una call sulla continuazione della logica
            // se guessedWord è success, allora si continua, se è error, allora ui fa uscire dialog di errore.
        }
        else {
            Log.d(TAG, "La parola non è di cinque lettere!");
        }
    }

    public void tryWord(String guessedWordString) {
        String dailyWordString = dailyWord.getValue().getData(); //todo forse non funziona, getData come override potrebbe non essere giusto
        for (int i = 0; i < 5; i++){
            Log.d(TAG, "Check: " + guessedWordString.charAt(i) + " - " + dailyWordString.charAt(i));
            if (guessedWordString.charAt(i) == dailyWordString.charAt(i)) {
                boardState.getValue().addColor(currentLine, i, "g");
            } else if (!(dailyWordString.indexOf(guessedWordString.charAt(i)) < 0)) { //todo volendo cambiare il comportamento di yellow
                boardState.getValue().addColor(currentLine, i, "y");
            } else {
                boardState.getValue().addColor(currentLine, i, "b");
            }
        }

        boardState.setValue(this.boardState.getValue());


        if (guessedWordString == dailyWordString){ //todo finire di implementare winloss
            winloss = "win";
        }else if (currentLine != 5) {
            currentLine++;
            currentLetter = 0;
            fiveLetterWord = false;
        }else {
            winloss = "loss";
        }
    }


    private void resetGame() {
        boardState.getValue().reset();
        currentLetter = 0;
        currentLine = 0;
        fiveLetterWord = false;
    }




}
