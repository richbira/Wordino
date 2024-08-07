package it.unimib.wordino.main.ui.welcome;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import it.unimib.wordino.main.model.User;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.user.IUserRepository;

public class UserViewModel extends ViewModel { // ViewModel per la gestione dell'utente
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private LiveData<Boolean> isTodayLiveData;
    private boolean authenticationError;


    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
        authenticationError = false;
        isTodayLiveData = userRepository.getIsTodayLiveData();
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

    public LiveData<UserStat> getUserStats(String tokenId) { // Da mettere nella schermata delle statistiche
        return userRepository.getUserStats(tokenId);
    }
    public void updateGameResult(String tokenId, boolean won,Integer guessCount) {
        userRepository.updateGameResult(tokenId, won, guessCount);
    }
    public void fetchUserStats(String tokenId) {
        userRepository.getUserStats(tokenId).observeForever(new Observer<UserStat>() {
            @Override
            public void onChanged(UserStat userStat) {
                //userStatsLiveData.setValue(userStat);
            }
        });
    }
    public LiveData<Boolean> getIsTodayLiveData() {
        return userRepository.getIsTodayLiveData();
    }

    public void checkIfDailyChallengeDateIsToday(String idToken) {
        userRepository.setIsTodayLiveData(idToken);
    }
}



