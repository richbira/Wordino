package it.unimib.wordino.main.ui.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

import it.unimib.wordino.R;
import it.unimib.wordino.databinding.FragmentForgotPasswordBinding;


public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private UserViewModel userViewModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        binding.buttonResetPassword.setOnClickListener(v->{
            String email = Objects.requireNonNull(binding.emailTextInputLayoutForgotPsw.getText()).toString().trim();
            if(isEmailOk(email)){
                userViewModel.resetPassword(email);
                // https://firebase.google.com/docs/auth/android/manage-users?hl=it#send_a_password_reset_email
                Navigation.findNavController(requireView()).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
            }
        });
    }
    private boolean isEmailOk(String email) {
        if (!EmailValidator.getInstance().isValid((email))) {
            binding.emailTextInputLayoutForgotPsw.setError(getString(R.string.error_email));
            return false;
        } else {
            binding.emailTextInputLayoutForgotPsw.setError(null);
            return true;
        }
    }
}