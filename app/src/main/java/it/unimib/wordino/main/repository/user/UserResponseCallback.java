package it.unimib.wordino.main.repository.user;

import it.unimib.wordino.main.Model.User;

public interface UserResponseCallback { // Interfaccia per la gestione delle risposte dell'utente
    void onSuccessFromAuthentication(User user);
    //void onSuccessFromAuthentication(UserStat userStat);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}

