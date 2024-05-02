package it.unimib.wordino.main.ui.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.validator.easychecker.EasyChecker;
import com.validator.easychecker.exceptions.DeveloperErrorException;
import com.validator.easychecker.exceptions.InputErrorException;
import com.validator.easychecker.util.PasswordPattern;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.wordino.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText emailEditText;
    private EditText passwordEditText;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        Button loginButton = view.findViewById(R.id.loginButton);
        Button registrationButton = view.findViewById(R.id.registerButton);
        Button forgotPswButton = view.findViewById(R.id.forgotPasswordButton);

        registrationButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.registrationFragment);
        });

        forgotPswButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.forgotPasswordFragment);
        });

        loginButton.setOnClickListener(item -> {
            Log.d(TAG, "Login Button Pressed");
            Log.d(TAG, "Password: " + passwordEditText.getText());
            Log.d(TAG, "Email: " + emailEditText.getText());

            boolean isValidEmail = false;
            boolean isValidPassword = false;

            try {
                Log.d(TAG, "Starting email validation");
                isValidEmail = validateField(emailEditText, "Email");
                Log.d(TAG, "Email validation completed: " + isValidEmail);

                Log.d(TAG, "Starting password validation");
                isValidPassword = EasyChecker.Instance.validateInput(this.getContext(), 8, PasswordPattern.PASSWORD_PATTERN_TWO , passwordEditText); //Non funziona
                Log.d(TAG, "Password validation completed: " + isValidPassword);

                if (isValidEmail && isValidPassword) {
                    Log.d(TAG, "Login successful, transitioning to game");
                    Log.d(TAG, "isValidEmail: " + isValidEmail);
                    Log.d(TAG, "isValidPassword: " + isValidPassword);
                    //Navigation.findNavController(item).navigate(R.id.gameActivity);
                } else {
                    Log.d(TAG, "Validation failed");
                    Log.d(TAG, "isValidEmail: " + isValidEmail);
                    Log.d(TAG, "isValidPassword: " + isValidPassword);
                    if (!isValidEmail) {
                        emailEditText.setError("Invalid email");
                    }
                    if (!isValidPassword) {
                        passwordEditText.setError("Invalid password");
                    }
                }
            } catch (InputErrorException | DeveloperErrorException e) {
                Log.e(TAG, "Validation error: ", e);
                if (e instanceof InputErrorException) {
                    // Specific error handling for input issues
                } else if (e instanceof DeveloperErrorException) {
                    // Specific error handling for developer errors
                }
            }
        });
    }
        private boolean validateField (EditText editText, String fieldType) throws
        InputErrorException, DeveloperErrorException {
            Log.d(TAG, "Validating " + fieldType);
            return EasyChecker.Instance.validateInput(getContext(), 8, PasswordPattern.PASSWORD_PATTERN_THREE, editText);
        }
    }
