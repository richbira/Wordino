package it.unimib.wordino.main.source.user;


import java.util.Set;

import it.unimib.wordino.Model.User;
import it.unimib.wordino.main.repository.user.UserResponseCallback;

/**
 * Base class to get the user data from a remote source.
 */
public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);
    public abstract void getUserFavoriteNews(String idToken);
    public abstract void getUserPreferences(String idToken);
    public abstract void saveUserPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken);
}
