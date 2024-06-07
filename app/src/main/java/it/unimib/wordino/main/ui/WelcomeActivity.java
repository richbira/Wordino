package it.unimib.wordino.main.ui;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static it.unimib.wordino.main.util.Constants.EMAIL_ADDRESS;
import static it.unimib.wordino.main.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.wordino.main.util.Constants.ID_TOKEN;
import static it.unimib.wordino.main.util.Constants.PASSWORD;
import static it.unimib.wordino.main.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.wordino.R;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.LoginActivity;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.DataEncryptionUtil;
import it.unimib.wordino.main.util.ServiceLocator;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private UserViewModel userViewModel;
    private DataEncryptionUtil dataEncryptionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Log.d(TAG, "login clicked");
            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        // Play as guest button
        Button playButton = findViewById(R.id.PlayButton);
        playButton.setOnClickListener(view -> {
            //ensureGuestCredentials();
            //Se utente è già presente in locale, allora non serve fare nulla, altirmenti creo utenza
            startGame();
        });

        dataEncryptionUtil = new DataEncryptionUtil(this.getApplication());

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(this.getApplication());
        userViewModel = new ViewModelProvider(
        this,
        new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        checkLoginStatus();
    }

    public void startGame() {
        Intent playIntent = new Intent(WelcomeActivity.this, GameActivity.class);
        startActivity(playIntent);
    }


    private void checkLoginStatus() {
        // Insert the logic you had planned for onViewCreated here
        if (userViewModel.getLoggedUser() != null) {
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

    // Metodo per salvare dati nelle SharedPreferences
    private void saveGuestCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GuestEmail", "guest@example.com"); // Salva un'email fittizia per il guest
        editor.apply();
    }
    private void ensureGuestCredentials() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        if (!prefs.contains("GuestEmail")) {  // Controlla se le credenziali ospite sono già state salvate
            saveGuestCredentials();
        }

    }
}
