package it.unimib.wordino.main.ui;

import static it.unimib.wordino.main.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.wordino.main.util.Constants.ID_TOKEN;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.wordino.R;
import it.unimib.wordino.databinding.FragmentSettingsBinding;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.ui.welcome.UserViewModel;
import it.unimib.wordino.main.ui.welcome.UserViewModelFactory;
import it.unimib.wordino.main.util.DataEncryptionUtil;
import it.unimib.wordino.main.util.ServiceLocator;
import it.unimib.wordino.main.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private FragmentSettingsBinding binding;
    public SharedPreferencesUtil sharedPref;
    private UserViewModel userViewModel;
    //private DataEncryptionUtil dataEncryptionUtil;
    //private String idToken;
    private String loggedUser;

    public SettingsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getActivity().getApplication());

        sharedPref = new SharedPreferencesUtil(this.getActivity().getApplication());

        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);




        /*try {
            idToken = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View htpView = view.findViewById(R.id.howToPlayConstraint);
        View settingsView = view.findViewById(R.id.settingsLayout);
        TextView loggedUserView = view.findViewById(R.id.accountText);

        boolean isDarkMode = sharedPref.readBooleanData("dark_mode", "dark_mode");

        SwitchMaterial darkModeSwitch = view.findViewById(R.id.dark_mode_switch);
        darkModeSwitch.setChecked(isDarkMode);



        //se utente non è loggato, nascondi il bottone di logout
        if (userViewModel.getLoggedUser() == null) {
            binding.logoutButton.setVisibility(View.GONE);
            loggedUserView.setText("Logged in as guest");
        } else {
            loggedUser = userViewModel.getLoggedUser().getEmail();
            loggedUserView.setText("Logged in as:\n" + loggedUser);
        }

        // Logout button listener
        binding.logoutButton.setOnClickListener(v -> {
            userViewModel.logout();
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_welcomeActivity);
            requireActivity().finish();
        });

        // HowToPlay button listener
        binding.howToPlayButton.setOnClickListener(v -> {
            Log.d(TAG, "cliccato how to play");
            htpView.setVisibility(View.VISIBLE);
            settingsView.setVisibility(View.GONE);
        });

        binding.closeButton.setOnClickListener(v -> {
            Log.d(TAG, "cliccato bottone per chiudere");
            settingsView.setVisibility(View.VISIBLE);
            htpView.setVisibility(View.GONE);
        });




        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPref.writeBooleanData("dark_mode", "dark_mode", true);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPref.writeBooleanData("dark_mode","dark_mode", false);
                }

            }
        });
    }
}