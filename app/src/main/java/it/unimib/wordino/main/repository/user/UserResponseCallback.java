package it.unimib.wordino.main.repository.user;

import it.unimib.wordino.main.Model.User;

public interface UserResponseCallback { //uso per le chiamate
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}

