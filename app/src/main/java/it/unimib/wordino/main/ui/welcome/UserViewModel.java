package it.unimib.wordino.main.ui.welcome;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.unimib.wordino.main.model.User;
import it.unimib.wordino.main.data.Result;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.user.IUserRepository;

public class UserViewModel extends ViewModel { // ViewModel per la gestione dell'utente
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private boolean authenticationError;
    private MutableLiveData<UserStat> userStatsLiveData;


    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
        authenticationError = false;
        userStatsLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<Result> getUserMutableLiveData(
            String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null) {
            getUserData(email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Result> getGoogleUserMutableLiveData(String token) {
        if (userMutableLiveData == null) {
            getUserData(token);
        }
        return userMutableLiveData;
    }

    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public MutableLiveData<Result> logout() {
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.logout();
        } else {
            userRepository.logout();
        }

        return userMutableLiveData;
    }

    public void getUser(String email, String password, boolean isUserRegistered) {
        userRepository.getUser(email, password, isUserRegistered);
    }

    public boolean isAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }

    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    private void getUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }

    public void resetPassword(String email) {
        userRepository.resetPassword(email);
    }

    public void saveUserStats(User user) {
        // Assumi che UserStat sia inizializzato al momento della creazione dell'utente o qui
        UserStat stats = new UserStat();  // Dovresti configurare i valori iniziali di UserStat
    }
    public LiveData<UserStat> getUserStats(String tokenId) { // Da mettere nella schermata delle statistiche
        return userRepository.getUserStats(tokenId);
    }
    public void updateUserStats(UserStat userStat) {
        userRepository.updateUserStats(getLoggedUser(), userStat); // Assume currentUser is already defined and valid
    }
    public void updateGameResult(String tokenId, boolean won, LifecycleOwner lifecycleOwner) {
        getUserStats(tokenId).observe(lifecycleOwner, userStats -> {
            Log.d(TAG, "updateGameResult: updating stats");
            if (userStats != null) {
                Log.d(TAG, "stats esistono: " + userStats);
                userStats.updateStats(won);
                userRepository.updateUserStats(getLoggedUser(), userStats);
            } else {
                Log.d(TAG, "Stats: not available");
            }
        });
    }

}



