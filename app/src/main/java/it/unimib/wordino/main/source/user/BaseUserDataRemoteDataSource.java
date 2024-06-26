package it.unimib.wordino.main.source.user;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;

import it.unimib.wordino.main.model.User;
import it.unimib.wordino.main.model.UserStat;
import it.unimib.wordino.main.repository.user.UserResponseCallback;

/**
 * Base class to get the user data from a remote source.
 */
public abstract class BaseUserDataRemoteDataSource { // Chiamata backend
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }
    public abstract void saveUserData(User user);
    public abstract MutableLiveData<UserStat> getUserStats(String tokenId);
    public abstract MutableLiveData<Boolean> getIsTodayLiveData();

    public abstract void updateUserStats(String idToken, UserStat userStat);
    public abstract void updateGameResult(String tokenId, boolean won, Integer guessCount);
    public abstract void setIsTodayLiveData(String idToken);

}

