package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.EMAIL_ADDRESS;
import static it.unimib.wordino.main.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.wordino.main.util.Constants.PASSWORD;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.wordino.R;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.LoginActivity;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.DataEncryptionUtil;
import it.unimib.wordino.main.util.ServiceLocator;
import it.unimib.wordino.main.util.SharedPreferencesUtil;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private UserViewModel userViewModel;
    private DataEncryptionUtil dataEncryptionUtil;
    public SharedPreferencesUtil sharedPref;
    public Boolean isAutoLoginOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sharedPref = new SharedPreferencesUtil(this.getApplication());

        boolean isDarkMode = sharedPref.readBooleanData("dark_mode", "dark_mode");
        isAutoLoginOn = sharedPref.readBooleanData("auto_login", "auto_login");

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Log.d(TAG, "login clicked");
            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        // Play as guest button
        Button playButton = findViewById(R.id.PlayButton);
        playButton.setOnClickListener(view -> startGame());

        dataEncryptionUtil = new DataEncryptionUtil(this.getApplication());

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(this.getApplication());
        userViewModel = new ViewModelProvider(
        this,
        new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        checkLoginStatus();
    }

    public void startGame() {
        Intent playIntent = new Intent(WelcomeActivity.this, GameActivity.class); //TODO Deve andare nel training
        startActivity(playIntent);
    }


    private void checkLoginStatus() {
        // Insert the logic you had planned for onViewCreated here
        Log.d(TAG, "Checking login status "+ isAutoLoginOn + " " + userViewModel.getLoggedUser());

        if (userViewModel.getLoggedUser() != null && isAutoLoginOn) {
            Log.d(TAG, "User already logged in: " + userViewModel.getLoggedUser().getEmail());
            autoLogin();
        }else{
            Log.d(TAG, "User not logged in: ");
        }
    }

    private void autoLogin() {
        try {
            String email = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS);
            String password = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD);
            if (email != null && password != null) {
                // Proceed with automatic login or directly start game
                startGame();
            }
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Failed to retrieve login credentials", e);
            throw new RuntimeException("Failed to retrieve encrypted data", e);
        }
    }
}
