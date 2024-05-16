package it.unimib.wordino.main.repository.user;

import androidx.lifecycle.MutableLiveData;

import java.util.Set;

import it.unimib.wordino.main.Model.User;
import it.unimib.wordino.main.data.Result;

public interface IUserRepository {
    MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getGoogleUser(String idToken);
    MutableLiveData<Result> logout();
    User getLoggedUser();
    void signUp(String email, String password);
    void signIn(String email, String password);
    void signInWithGoogle(String token);

    void resetPassword(String email);
}