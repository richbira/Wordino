package it.unimib.wordino.main.source.user;


import androidx.lifecycle.LiveData;

import java.util.Set;

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
    //Gestisco qua le statistiche dell'highscore + calcolo statistiche
    //Creo i metodi e li implemento su UserDataRemote
    public abstract LiveData<UserStat> getUserStats(String tokenId);
}
