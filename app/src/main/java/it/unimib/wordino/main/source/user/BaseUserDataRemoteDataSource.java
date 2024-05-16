package it.unimib.wordino.main.source.user;


import java.util.Set;

import it.unimib.wordino.main.Model.User;
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
}
