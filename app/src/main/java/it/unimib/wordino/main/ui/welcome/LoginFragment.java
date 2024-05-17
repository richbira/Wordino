package it.unimib.wordino.main.ui.welcome;

import static it.unimib.wordino.main.util.Constants.EMAIL_ADDRESS;
import static it.unimib.wordino.main.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.wordino.main.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.wordino.main.util.Constants.INVALID_USER_ERROR;
import static it.unimib.wordino.main.util.Constants.PASSWORD;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.validator.easychecker.EasyChecker;
import com.validator.easychecker.exceptions.DeveloperErrorException;
import com.validator.easychecker.exceptions.InputErrorException;
import com.validator.easychecker.util.PasswordPattern;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.wordino.R;
import it.unimib.wordino.main.Model.User;
import it.unimib.wordino.main.data.Result;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.GameActivity;
import it.unimib.wordino.main.util.DataEncryptionUtil;
import it.unimib.wordino.main.util.ServiceLocator;

public class LoginFragment extends Fragment {
    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    private ActivityResultContracts.StartIntentSenderForResult startIntentSenderForResult;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private UserViewModel userViewModel;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private Button resetPassword;
    private Button loginButton;
    private Button registrationButton;
    private Button buttonGoogleLogin;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private DataEncryptionUtil dataEncryptionUtil;
    private static final boolean USE_NAVIGATION_COMPONENT = true;
    private LinearProgressIndicator progressIndicator;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);


        oneTapClient = Identity.getSignInClient(requireActivity());
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();


        startIntentSenderForResult = new ActivityResultContracts.StartIntentSenderForResult();

        activityResultLauncher = registerForActivityResult(startIntentSenderForResult, activityResult -> {
            if (activityResult.getResultCode() == Activity.RESULT_OK) {
                Log.d(TAG, "result.getResultCode() == Activity.RESULT_OK");
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(activityResult.getData());
                    String idToken = credential.getGoogleIdToken();
                    if (idToken != null) {
                        // Got an ID token from Google. Use it to authenticate with Firebase.
                        userViewModel.getGoogleUserMutableLiveData(idToken).observe(getViewLifecycleOwner(), authenticationResult -> {
                            if (authenticationResult.isSuccess()) {
                                User user = ((Result.UserResponseSuccess) authenticationResult).getData();
                                saveLoginData(user.getEmail(), null, user.getIdToken());
                                userViewModel.setAuthenticationError(false);
                                Navigation.findNavController(requireView()).navigate(R.id.login_nav_graph);
                                requireActivity().finish();
                            } else {
                                userViewModel.setAuthenticationError(true);
                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                        getErrorMessage(((Result.Error) authenticationResult).getMessage()),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (ApiException e) {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        textInputLayoutEmail = view.findViewById(R.id.emailTextInputLayout);
        textInputLayoutPassword = view.findViewById(R.id.passwordTextInputLayout);
        registrationButton = view.findViewById(R.id.registerButton);
        loginButton = view.findViewById(R.id.loginButton);
        resetPassword = view.findViewById(R.id.forgotPasswordButton);
        buttonGoogleLogin = view.findViewById(R.id.logInWithGoogleButton);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*if (userViewModel.getLoggedUser() != null) { // Check if the user is already logged in
            Log.d(TAG, "User already logged in");
            String email= null;
            String password= null;
            try {
                email = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME,EMAIL_ADDRESS); // Read the email from the encrypted shared preferences
                password = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD); // Read the password from the encrypted shared preferences

            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (email != null && password!= null) { // If the email and password are not null
                String finalEmail = email;
                String finalPassword = password;
                startActivityBasedOnCondition(GameActivity.class, R.id.bottom_nav_menu_graph); // Start the GameActivity
            }
        }*/

        resetPassword.setOnClickListener(v -> {
            Log.d(TAG, "resetPassword clicked");
            //Navigation.findNavController(requireView()).navigate(R.id.forgotPasswordFragment); // Navigate to the ForgotPasswordFragment
        });
        buttonGoogleLogin.setOnClickListener(v -> {
            Log.d(TAG, "login with google clicked");
        }); // Listener for the login button
        registrationButton.setOnClickListener(v -> {
            Log.d(TAG, "register clicked ");
            Navigation.findNavController(getView()).navigate(R.id.registrationFragment);
        });
        loginButton.setOnClickListener(v -> {
            String email = textInputLayoutEmail.getEditText().getText().toString().trim();
            String password = textInputLayoutPassword.getEditText().getText().toString().trim();

            // Start login if email and password are ok
            if (isEmailOk(email) & isPasswordOk(password)) { // If the email and password are ok
                if (!userViewModel.isAuthenticationError()) { // If the user is not authenticated
                    progressIndicator.setVisibility(View.VISIBLE);
                    userViewModel.getUserMutableLiveData(email, password, true).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    User user = ((Result.UserResponseSuccess) result).getData();
                                    userViewModel.setAuthenticationError(false);
                                    Navigation.findNavController(requireView()).navigate(R.id.bottom_nav_menu_graph); // Navigate to the MainActivity
                                    saveLoginData(email, password, user.getIdToken());
                                    requireActivity().finish();
                                } else {
                                    userViewModel.setAuthenticationError(true);
                                    progressIndicator.setVisibility(View.GONE);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    userViewModel.getUser(email, password, true);
                }
            } else {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });// Listener for the login button
    }
    private void startActivityBasedOnCondition(Class<?> destinationActivity, int destination) {
        if (USE_NAVIGATION_COMPONENT) {
            Navigation.findNavController(requireView()).navigate(destination);
        } else {
            Intent intent = new Intent(requireContext(), destinationActivity);
            startActivity(intent);
        }
        requireActivity().finish();
    }
    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onViewCreated(view, savedInstanceState);
        textInputLayoutEmail = view.findViewById(R.id.emailTextInputLayout);
        textInputLayoutPassword = view.findViewById(R.id.passwordTextInputLayout);

        Button loginButton = view.findViewById(R.id.loginButton);
        Button registrationButton = view.findViewById(R.id.registerButton);
        Button forgotPswButton = view.findViewById(R.id.forgotPasswordButton);

        registrationButton.setOnClickListener(v -> {
            Log.d(TAG, "register clicked ");
            Navigation.findNavController(v).navigate(R.id.registrationFragment);
        });

        forgotPswButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.forgotPasswordFragment);
        });
    }*/

    private boolean validateField(EditText editText, String fieldType) throws
            InputErrorException, DeveloperErrorException {
        Log.d(TAG, "Validating " + fieldType);
        return EasyChecker.Instance.validateInput(getContext(), 8, PasswordPattern.PASSWORD_PATTERN_THREE, editText);
    }

    private void saveLoginData(String email, String password, String idToken) {
        /*
        try {
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN, idToken);
            if (password != null) {
                dataEncryptionUtil.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME,
                        email.concat(":").concat(password));
            }

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }*/
    }
    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case INVALID_CREDENTIALS_ERROR:
                return requireActivity().getString(R.string.error_login_password_message);
            case INVALID_USER_ERROR:
                return requireActivity().getString(R.string.error_login_user_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
    private boolean isPasswordOk(String password) {
        // Check if the password length is correct
        if (password.isEmpty()) {
            textInputLayoutPassword.setError(getString(R.string.error_password));
            return false;
        } else {
            textInputLayoutPassword.setError(null);
            return true;
        }
    }
    private boolean isEmailOk(String email) {
        // Check if the email is valid through the use of this library:
        // https://commons.apache.org/proper/commons-validator/
        if (!EmailValidator.getInstance().isValid((email))) {
            textInputLayoutEmail.setError(getString(R.string.error_email));
            return false;
        } else {
            textInputLayoutEmail.setError(null);
            return true;
        }
    }
}
