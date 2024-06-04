package it.unimib.wordino.main.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import it.unimib.wordino.main.model.GameBoard;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.repository.IWordRepositoryLD;

public class GameBoardViewModelDaily extends ViewModel {

    private static final String TAG = GameBoardViewModelDaily.class.getSimpleName();

    private final IWordRepositoryLD wordRepositoryLD;

    private MutableLiveData<Result> guessedWord;
    private MutableLiveData<Result> dailyWord;
    private MutableLiveData<GameBoard> boardState;

    public int currentLine = 0;
    public int currentLetter = 0;
    public Boolean fiveLetterWord = false;
    public Boolean enterIsPressed = false;
    private String winloss = "";

    public GameBoardViewModelDaily(IWordRepositoryLD wordRepositoryLD){
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
            guessedWord = wordRepositoryLD.fetchSpecificWordCheck("");
            Log.d(TAG, "inizializzata guessedWord");
        }
        return guessedWord;
    }

    public MutableLiveData<Result> getRandomWord(){
        if (dailyWord == null) {
            dailyWord = wordRepositoryLD.fetchRandomWord();//todo mettere check se la daily è scaduta
            Log.d(TAG, "fetch dailyWord");
        }
        return dailyWord;
    }

    public int getCurrentLine(){ return currentLine;}
    public Boolean getEnterIsPressed() {return enterIsPressed;}

    public String getWinloss() {return winloss;}



    private void checkRealWord(String word){
        guessedWord = wordRepositoryLD.fetchSpecificWordCheck(word);
    }



    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------

    public void updateGameBoard(String text) {

            if (Objects.equals(text, "CANC")) {
                findNextLetter(-1);
                boardState.getValue().changeValue(currentLine, currentLetter, null);
            }else if (Objects.equals(text, "ENTER")) {
                enterPressed();
            }

            else {
                boardState.getValue().changeValue(currentLine, currentLetter, text + "w");
                findNextLetter(1);
            }

            boardState.setValue(this.boardState.getValue());

    }

    public void findNextLetter(int i) {

        int nextLetter;

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
    }

    private void enterPressed(){
        Log.d(TAG, "INIZIO ENTERPRESSED");
        String tempWord = "";
        if (fiveLetterWord) {

            enterIsPressed = true;

            for (int i = 0; i < 5; i++) {
                tempWord += boardState.getValue().getValue(currentLine, i).charAt(0);
            }

            tempWord = tempWord.toLowerCase(); // forse non serve

            checkRealWord(tempWord);
            // idea: mettere observer nel ui su tempword, in modo che quando arriva il cambiamento possa rilanciare una call sulla continuazione della logica
            // se guessedWord è success, allora si continua, se è error, allora ui fa uscire dialog di errore.
        }
        else {
            Log.d(TAG, "La parola non è di cinque lettere!");
        }
    }

    public void tryWord(String guessedWordString) {
        if (enterIsPressed) {
            String dailyWordString = dailyWord.getValue().getData();
            String color_code = "";
            for (int i = 0; i < 5; i++) {
                Log.d(TAG, "Check: " + guessedWordString.charAt(i) + " - " + dailyWordString.charAt(i));
                if (guessedWordString.charAt(i) == dailyWordString.charAt(i)) {
                    boardState.getValue().changeColor(currentLine, i, "g");
                } else if (!(dailyWordString.indexOf(guessedWordString.charAt(i)) < 0)) { //todo volendo cambiare il comportamento di yellow
                    boardState.getValue().changeColor(currentLine, i, "y");
                } else {
                    boardState.getValue().changeColor(currentLine, i, "b");
                }
                color_code += boardState.getValue().getValue(currentLine, i);
            }
            Log.d(TAG, "Code = " + color_code);

            Log.d(TAG, "guess = " + guessedWordString);
            Log.d(TAG, "daily = " + dailyWordString);
            if (guessedWordString.equals(dailyWordString)) { //todo finire di implementare winloss
                winloss = "win";
                Log.d(TAG, "Hai vinto!");
            } else if (currentLine != 5) {
                currentLine++;
                currentLetter = 0;
                fiveLetterWord = false;
            } else {
                winloss = "loss";
                Log.d(TAG, "Hai perso!");
            }

            boardState.setValue(this.boardState.getValue());
            enterIsPressed = false;
        } else Log.d(TAG, "enterisPressed è false");
    }


    private void resetGame() {
        boardState.getValue().reset();
        currentLetter = 0;
        currentLine = 0;
        fiveLetterWord = false;
    }




}
