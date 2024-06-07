package it.unimib.wordino.main.repository.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Set;

import it.unimib.wordino.main.model.User;
import it.unimib.wordino.main.model.Result;
import it.unimib.wordino.main.model.UserStat;

public interface IUserRepository { // Interfaccia per la gestione dell'utente
    MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getGoogleUser(String idToken);
    MutableLiveData<Result> logout();
    User getLoggedUser();
    void signUp(String email, String password);
    void signIn(String email, String password);
    void signInWithGoogle(String token);
    void resetPassword(String email);
     LiveData<UserStat> getUserStats(String tokenId);
    void updateUserStats(User user, UserStat userStat);
}