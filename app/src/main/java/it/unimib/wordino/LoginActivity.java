package it.unimib.wordino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextInputLayout = findViewById(R.id.emailTextInput);
        passwordTextInputLayout = findViewById(R.id.passwordTextInput);

        Button loginButton = findViewById(R.id.LoginButton);

        loginButton.setOnClickListener(item -> {
            // Inizialmente al onCreate questi valori saranno x forza blank, quindi posso farli scattare anche dopo quando viene premuto login
            Log.d(TAG,"Login bottone");
            String email = emailTextInputLayout.getEditText().getText().toString();
            String password = passwordTextInputLayout.getEditText().getText().toString();

            Log.d(TAG,"Email " + email);
            Log.d(TAG,"psw " + password);

            Log.d(TAG,"Email " + checkEmail(email));
            Log.d(TAG,"psw " + checkPassword(password));

            if(checkEmail(email) && checkPassword(password)){

            }else{
                // Snackbar di errore
                Snackbar.make(findViewById(android.R.id.content),
                        getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkEmail(String email){
    //Uso una libreria esterna
        boolean result = EmailValidator.getInstance().isValid(email);

        if (!result){
            emailTextInputLayout.setError("Errore - Da mettere su una stringa");
        } else {
            emailTextInputLayout.setError(null);
        }
        return result;
    }

    private boolean checkPassword(String password){
        return password != null && password.length() >= 8; // Posso aggiungere qua altri valori
    }

}