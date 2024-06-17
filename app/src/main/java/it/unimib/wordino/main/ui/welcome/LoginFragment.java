package it.unimib.wordino.main.ui.welcome;

import static it.unimib.wordino.main.util.Constants.EMAIL_ADDRESS;
import static it.unimib.wordino.main.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.wordino.main.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.wordino.main.util.Constants.ID_TOKEN;
import static it.unimib.wordino.main.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.wordino.main.util.Constants.INVALID_USER_ERROR;
import static it.unimib.wordino.main.util.Constants.PASSWORD;

import android.app.Activity;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

import it.unimib.wordino.R;
import it.unimib.wordino.main.model.Result.UserResponseSuccess;
import it.unimib.wordino.main.model.User;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.repository.user.IUserRepository;
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
    private View progressBar;
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

        Log.d(TAG,"requireActivity() == " + requireActivity());
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
        dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());

        startIntentSenderForResult = new ActivityResultContracts.StartIntentSenderForResult();


        activityResultLauncher = registerForActivityResult(startIntentSenderForResult, activityResult -> {
            Log.d(TAG, "activityResult.getResultCode() == " + activityResult.getResultCode());
            if (true) {

                Log.d(TAG, "result.getResultCode() == Activity.RESULT_OK");
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(activityResult.getData());
                    Log.d(TAG, "onCreate: " + credential);
                    String idToken = credential.getGoogleIdToken();
                    Log.d(TAG, "idToken: " + idToken);
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate with Firebase.
                        userViewModel.getGoogleUserMutableLiveData(idToken).observe(getViewLifecycleOwner(), authenticationResult -> {
                            if (authenticationResult.isSuccess()) {
                                Log.d(TAG, "Sto entrando con Google");
                                User user = ((Result.UserResponseSuccess) authenticationResult).getData();
                                saveLoginData(user.getEmail(), null, user.getIdToken());
                                userViewModel.setAuthenticationError(false);
                                lockButtons(false);
                                progressBar.setVisibility(View.VISIBLE);
                                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_gameActivity);
                                requireActivity().finish();
                            } else {
                                userViewModel.setAuthenticationError(true);
                                lockButtons(true);
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                        getErrorMessage(((Result.Error) authenticationResult).getMessage()),
                                        Snackbar.LENGTH_SHORT).show();
                                Log.d(TAG, "Errore nell'accesso con Google)" + ((Result.Error) authenticationResult).getMessage());
                            }
                        });
                    }
                } catch (ApiException e) {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                    lockButtons(true);
                    progressBar.setVisibility(View.GONE);
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
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        resetPassword.setOnClickListener(v -> {
            Log.d(TAG, "resetPassword clicked");
            lockButtons(false);
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_forgotPasswordFragment); // Navigate to the ForgotPasswordFragment
        });
        buttonGoogleLogin.setOnClickListener(v -> oneTapClient.beginSignIn(signInRequest) // Start the One Tap sign-in flow
                .addOnSuccessListener(requireActivity(), result -> {
                    Log.d(TAG, "onSuccess from oneTapClient.beginSignIn(BeginSignInRequest)");
                    IntentSenderRequest intentSenderRequest =
                            new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                    //lockButtons(false);
                    //progressBar.setVisibility(View.VISIBLE);
                    activityResultLauncher.launch(intentSenderRequest);
                    // Se loggato correttamente vado al giorno
                    //Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_gameActivity); // Navigate to the GameFragment

                })
                .addOnFailureListener(requireActivity(), e -> {
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
                    lockButtons(true);
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            requireActivity().getString(R.string.error_no_google_account_found_message),
                            Snackbar.LENGTH_SHORT).show();
                })); // Listener for the login with Google button
        registrationButton.setOnClickListener(v -> {
            lockButtons(false);
            progressBar.setVisibility(View.VISIBLE);
            Navigation.findNavController(requireView()).navigate(R.id.registrationFragment);
        });
        loginButton.setOnClickListener(v -> {
            String email = Objects.requireNonNull(textInputLayoutEmail.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(textInputLayoutPassword.getEditText()).getText().toString().trim();
            lockButtons(false);
            progressBar.setVisibility(View.VISIBLE);
            // Start login if email and password are ok
            if (isEmailOk(email) & isPasswordOk(password)) { // If the email and password are ok
                if (!userViewModel.isAuthenticationError()) { // If the user is not authenticated
                    userViewModel.getUserMutableLiveData(email, password, true).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    User user = ((UserResponseSuccess) result).getData();
                                    userViewModel.setAuthenticationError(false);
                                    Log.d(TAG, "login successful");
                                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_gameActivity); // Navigate to the DailyFragment
                                    saveLoginData(email, password, user.getIdToken());
                                    requireActivity().finish();

                                } else {
                                    userViewModel.setAuthenticationError(true);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                    lockButtons(true);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                } else {
                    userViewModel.getUser(email, password, true);
                }
            } else {
                lockButtons(true);
                progressBar.setVisibility(View.GONE);
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });// Listener for the login button
    }
    private void saveLoginData(String email, String password, String idToken) {
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

    private void lockButtons(boolean lock) {
        loginButton.setEnabled(lock);
        registrationButton.setEnabled(lock);
        buttonGoogleLogin.setEnabled(lock);
        resetPassword.setEnabled(lock);
    }
    @Override
    public void onResume() {
        super.onResume();
        userViewModel.setAuthenticationError(false);
    }

}
