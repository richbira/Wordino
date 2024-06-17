package it.unimib.wordino.main.ui.welcome;

import static androidx.fragment.app.FragmentManager.TAG;
import static it.unimib.wordino.main.util.Constants.MINIMUM_PASSWORD_LENGTH;
import static it.unimib.wordino.main.util.Constants.USER_COLLISION_ERROR;
import static it.unimib.wordino.main.util.Constants.WEAK_PASSWORD_ERROR;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

import it.unimib.wordino.R;
import it.unimib.wordino.databinding.FragmentRegistrationBinding;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.ui.SocialFragment;

public class RegistrationFragment extends Fragment {

    private static final String TAG = RegistrationFragment.class.getSimpleName();
    private FragmentRegistrationBinding binding;
    private UserViewModel userViewModel;
    public View progressBar;

    public RegistrationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated"+ userViewModel.isAuthenticationError());
        progressBar = binding.progressBar;
        binding.registerButton.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString().trim();
            binding.registerButton.setEnabled(false);
            if (isEmailOk(email) && isPasswordOk(password)) {
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "Email: " + email + " Password: " + password);
                userViewModel.getUserMutableLiveData(email, password,false).observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        userViewModel.setAuthenticationError(false);
                        progressBar.setVisibility(View.GONE);
                        Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_howToPlayFragment);
                    } else {
                        Log.d(TAG, "Error: " + ((Result.Error) result).getMessage());
                        userViewModel.setAuthenticationError(true);
                        progressBar.setVisibility(View.GONE);
                        binding.registerButton.setEnabled(true);
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                getErrorMessage(((Result.Error) result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
            } else {
                userViewModel.setAuthenticationError(true);
                binding.registerButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    private String getErrorMessage(String message) {
        switch(message) {
            case WEAK_PASSWORD_ERROR:
                return requireActivity().getString(R.string.error_password);
            case USER_COLLISION_ERROR:
                return requireActivity().getString(R.string.error_user_collision_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }

    private boolean isEmailOk(String email) {
        // Check if the email is valid through the use of this library:
        // https://commons.apache.org/proper/commons-validator/
        if (!EmailValidator.getInstance().isValid((email))) {
            binding.emailTextInputLayout.setError(getString(R.string.error_email));
            return false;
        } else {
            binding.emailTextInputLayout.setError(null);
            return true;
        }
    }

    /**
     * Checks if the password is not empty.
     * @param password The password to be checked
     * @return True if the password has at least 6 characters, false otherwise
     */
    private boolean isPasswordOk(String password) {
        // Check if the password length is correct
        if (password.isEmpty() || password.length() < MINIMUM_PASSWORD_LENGTH) {
            binding.emailTextInputLayout.setError(getString(R.string.error_password));
            return false;
        } else {
            binding.emailTextInputLayout.setError(null);
            return true;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}