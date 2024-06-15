package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.PACKAGE_NAME;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.wordino.R;
import it.unimib.wordino.main.model.GameBoard;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.repository.IWordRepositoryLD;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.ServiceLocator;

public class DailyFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = DailyFragment.class.getSimpleName();
    public View progressBar;
    public int currentLine;
    private GameBoardViewModelDaily gameBoardModel;

    public Animator flipAnimation1;
    public Animator flipAnimation2;
    public Animator flipAnimation3;
    public Animator flipAnimation4;
    public Animator flipAnimation5;
    public Animator shakeAnimation1;
    public Animator shakeAnimation2;
    public Animator shakeAnimation3;
    public Animator shakeAnimation4;
    public Animator shakeAnimation5;

    public Observer<GameBoard> gameBoardObserver;
    public Observer<Result> wordCheckObserver;
    public Observer<Result> dailyWordObserver;
    public Observer<Result> randomWordObserver;
    private UserViewModel userViewModel;
    //private DataEncryptionUtil dataEncryptionUtil;
    private String tokenId;
    private boolean gameOver;
    private boolean fetchingRandom = false;


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

        IWordRepositoryLD wordRepositoryLD =
                ServiceLocator.getInstance().getWordRepositoryLD(requireActivity().getApplication());

        gameBoardModel = new ViewModelProvider(
                requireActivity(),
                new GameBoardViewModelDailyFactory(wordRepositoryLD)).get(GameBoardViewModelDaily.class);

        //Roba per user e statistiche
        //dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        gameBoardObserver = new Observer<GameBoard>() {
            @Override
            public void onChanged(@Nullable GameBoard gameBoard) {
                updateGameBoardUI(gameBoard);
                currentLine = gameBoardModel.getCurrentLine();
                if(gameBoardModel.getWinloss() != "" && !gameOver){ //todo gameover ricevuto da firebase

                    if(gameBoardModel.getWinloss().equals("win")){
                        gameOver = true;
                        userViewModel.updateGameResult(tokenId, true,currentLine);
                    } else if(gameBoardModel.getWinloss().equals("loss")){
                        gameOver = true;
                        userViewModel.updateGameResult(tokenId, false,currentLine);
                    }
                    gameOverAlert(gameBoardModel.getWinloss());

                }
            }
        };
        wordCheckObserver = new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result result) {
                Log.d(TAG, "INIZIO ONCHANGED OBSERVER");
                if (result.isSuccess()){
                    Log.d(TAG, "Result is success, inizia procedura di comparazione");
                    Log.d(TAG, "parola: " + result.getData());
                    gameBoardModel.tryWord((String) result.getData());
                    if (gameBoardModel.getEnterIsPressed()) {
                        gameBoardModel.resetEnterNotPressed();

                        flipAnimation1.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + (currentLine - 1) + "1", "id", PACKAGE_NAME)));
                        flipAnimation2.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + (currentLine - 1) + "2", "id", PACKAGE_NAME)));
                        flipAnimation3.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + (currentLine - 1) + "3", "id", PACKAGE_NAME)));
                        flipAnimation4.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + (currentLine - 1) + "4", "id", PACKAGE_NAME)));
                        flipAnimation5.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + (currentLine - 1) + "5", "id", PACKAGE_NAME)));

                        flipAnimation1.start();
                        flipAnimation2.start();
                        flipAnimation3.start();
                        flipAnimation4.start();
                        flipAnimation5.start();
                    }

                }else if (gameBoardModel.enterIsPressed){
                    Log.d(TAG, "La parola non esiste! ");
                    gameBoardModel.resetEnterNotPressed();

                    //shake animation


                    shakeAnimation1.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "1", "id", PACKAGE_NAME)));
                    shakeAnimation2.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "2", "id", PACKAGE_NAME)));
                    shakeAnimation3.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "3", "id", PACKAGE_NAME)));
                    shakeAnimation4.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "4", "id", PACKAGE_NAME)));
                    shakeAnimation5.setTarget((TextView) getView().findViewById(getResources().getIdentifier("word_" + currentLine + "5", "id", PACKAGE_NAME)));

                    shakeAnimation1.start();
                    shakeAnimation2.start();
                    shakeAnimation3.start();
                    shakeAnimation4.start();
                    shakeAnimation5.start();

                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            "This word does not exist",
                            Snackbar.LENGTH_SHORT).show();
                }

                currentLine = gameBoardModel.getCurrentLine();

            }
        };

        dailyWordObserver = new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result result) {
                Log.d(TAG, "INIZIO dailyword OBSERVER");

                if(result.isSuccess()){
                    Log.d(TAG, "La parola daily è: " + result.getData());
                    progressBar.setVisibility(View.GONE);

                } else {
                    gameBoardModel.setRandomWordToBeFetched();
                }

            }
        };

        randomWordObserver = new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result result) {

                Log.d(TAG, "INIZIO randomword OBSERVER");
                if (result.isSuccess()){
                    gameBoardModel.pushWordOnFirebase();
                    progressBar.setVisibility(View.GONE);
                }

            }
        };
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
        Log.d(TAG, "onViewCreated, DaylyFragment accesso");
        Log.d(TAG, "utenza?"+userViewModel.getLoggedUser());
        //Nascondo tab Setting e Social se l'utente non è loggato
        if (userViewModel.getLoggedUser() != null) {
            tokenId = userViewModel.getLoggedUser().getIdToken();
        }else{
            // Safe access to BottomNavigationView
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
            Log.d(TAG, "BottomNavigationView: " + bottomNavigationView);
            if (bottomNavigationView != null) {
                Menu menu = bottomNavigationView.getMenu();
                for (int i = 0, size = menu.size(); i < size; i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem.getTitle().equals("Social") || menuItem.getTitle().equals("Daily")){
                        menuItem.setVisible(false);
                    }
                }
            }
        }
        userViewModel.getIsTodayLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isToday) {
                if (isToday) {
                    Log.d(TAG, "Hai già completato la daily di oggi");
                    gameBoardModel.setBlockDaily(true);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Daily challenge");
                    builder.setMessage("You completed today's challenge. Come back tomorrow!");
                    //builder.setCancelable(false);
                    /*
                    builder.setPositiveButton("Vai alle statistiche", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NavHostFragment.findNavController(DailyFragment.this)
                                    .navigate(R.id.action_dailyFragment_to_socialFragment);
                        }
                    });
                    */
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Log.d(TAG, "Daily ancora da fare");
                    gameBoardModel.setBlockDaily(false);
                }
            }
        });

        userViewModel.checkIfDailyChallengeDateIsToday(tokenId);

        progressBar = view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        flipAnimation1 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation2 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation3 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation4 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);
        flipAnimation5 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip_animator);

        shakeAnimation1 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.shake_animator);
        shakeAnimation2 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.shake_animator);
        shakeAnimation3 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.shake_animator);
        shakeAnimation4 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.shake_animator);
        shakeAnimation5 = AnimatorInflater.loadAnimator(view.getContext(), R.animator.shake_animator);


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


        //OBSERVERS

        gameBoardModel.getGameBoard().observe(getViewLifecycleOwner(), gameBoardObserver);
        gameBoardModel.getDailyWord().observe(getViewLifecycleOwner(), dailyWordObserver);
        gameBoardModel.getRandomWord().observe(getViewLifecycleOwner(), randomWordObserver);
        gameBoardModel.getGuessedWord().observe(getViewLifecycleOwner(), wordCheckObserver);


    }

    @Override
    public void onClick(View v) {

        //if (!(gameBoardModel.getBlockDaily())) {
            int id = v.getId();
            if (id == R.id.key_q) keyPressed("Q");
            else if (id == R.id.key_w) keyPressed("W");
            else if (id == R.id.key_e) keyPressed("E");
            else if (id == R.id.key_r) keyPressed("R");
            else if (id == R.id.key_t) keyPressed("T");
            else if (id == R.id.key_y) keyPressed("Y");
            else if (id == R.id.key_u) keyPressed("U");
            else if (id == R.id.key_i) keyPressed("I");
            else if (id == R.id.key_o) keyPressed("O");
            else if (id == R.id.key_p) keyPressed("P");
            else if (id == R.id.key_a) keyPressed("A");
            else if (id == R.id.key_s) keyPressed("S");
            else if (id == R.id.key_d) keyPressed("D");
            else if (id == R.id.key_f) keyPressed("F");
            else if (id == R.id.key_g) keyPressed("G");
            else if (id == R.id.key_h) keyPressed("H");
            else if (id == R.id.key_j) keyPressed("J");
            else if (id == R.id.key_k) keyPressed("K");
            else if (id == R.id.key_l) keyPressed("L");
            else if (id == R.id.key_z) keyPressed("Z");
            else if (id == R.id.key_x) keyPressed("X");
            else if (id == R.id.key_c) keyPressed("C");
            else if (id == R.id.key_v) keyPressed("V");
            else if (id == R.id.key_b) keyPressed("B");
            else if (id == R.id.key_n) keyPressed("N");
            else if (id == R.id.key_m) keyPressed("M");
            else if (id == R.id.key_cancel) keyPressed("CANC");
            else if (id == R.id.key_enter) keyPressed("ENTER");
        //}
        //else {
        //    Log.d(TAG, "Gameover, tasti disattivati");

        //}
    }

    /*  ----------------------------------------------------------------------------------------FUNZIONI DI LOGICA  ----------------------------------------------------------------------------------------*/
    /*  ----------------------------------------------------------------------------------------FUNZIONI DI LOGICA  ----------------------------------------------------------------------------------------*/
    /*  ----------------------------------------------------------------------------------------FUNZIONI DI LOGICA  ----------------------------------------------------------------------------------------*/


    public void keyPressed(String key){
        Log.d(TAG, "Premuto il tasto " + key);
        gameBoardModel.updateGameBoard(key);
    }



    private void updateGameBoardUI(GameBoard gameBoard){
        Log.d(TAG, "Inizio updateGameBoardUI");
        String wordCode = "";
        String colorCode = "";
        for (int i = 0; i < (currentLine+1); i++){
            wordCode = "";
            colorCode = "";
            for (int j = 0; j < 5; j++){
                if (!(gameBoard.getValue(i, j) == null)){
                    wordCode += gameBoard.getValue(i, j).charAt(0);
                    colorCode += gameBoard.getValue(i, j).charAt(1);
                    changeBoxText(i, j, gameBoard.getValue(i, j).charAt(0) + "");
                }else changeBoxText(i, j, "");
            }

            changeBoxColor(i, colorCode);
            changeKeyColor(colorCode, wordCode);
        }

    }

    private void changeBoxText(int currentLine, int currentLetter, String value){
        String boxIndex = "word_" + currentLine + (currentLetter + 1);
        ((TextView) getView().findViewById(getResources().getIdentifier(boxIndex, "id", PACKAGE_NAME))).setText(value);
    }

    private void changeBoxColor(int i, String code) {
        String boxId;


        currentLine = gameBoardModel.getCurrentLine();
        if (code != "") {
                for (int j = 1; j < 6; j++) {
                    if (j < code.length() + 1) {

                        boxId = "word_" + i + j;
                        TextView currentBox = (TextView) getView().findViewById(getResources().getIdentifier(boxId, "id", PACKAGE_NAME));
                        if (code.charAt(j - 1) == 'g') {
                            currentBox.setBackgroundResource(R.drawable.border_green);
                        } else if (code.charAt(j - 1) == 'y') {
                            currentBox.setBackgroundResource(R.drawable.border_yellow);
                        } else if (code.charAt(j - 1) == 'b') {
                            currentBox.setBackgroundResource(R.drawable.border_grey);
                        }
                    }
                }

        }

    }

    private void changeKeyColor(String code, String word) {
        String keyId;
        if (word != "") {
            for (int i = 0; i < code.length(); i++) {

                keyId = ("key_" + word.charAt(i)).toLowerCase();
                if (code.charAt(i) == 'g') {
                    ((Button) getView().findViewById(getResources().getIdentifier(keyId, "id", PACKAGE_NAME))).setBackgroundColor(getResources().getColor(R.color.mygreen));  //TODO deprecated getcolor
                } else if (code.charAt(i) == 'y') {
                    ((Button) getView().findViewById(getResources().getIdentifier(keyId, "id", PACKAGE_NAME))).setBackgroundColor(getResources().getColor(R.color.myyellow));  //TODO deprecated getcolor
                } else if (code.charAt(i) == 'b') {
                    ((Button) getView().findViewById(getResources().getIdentifier(keyId, "id", PACKAGE_NAME))).setBackgroundColor(getResources().getColor(R.color.mygrey));  //TODO deprecated getcolor
                }

            }
        }
    }

    private void gameOverAlert(String winloss) {
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


