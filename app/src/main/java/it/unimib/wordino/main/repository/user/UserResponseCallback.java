package it.unimib.wordino.main.repository.user;

import it.unimib.wordino.main.Model.User;
import it.unimib.wordino.main.model.PlayerStats;

public interface UserResponseCallback { // Interfaccia per la gestione delle risposte dell'utente
    void onSuccessFromAuthentication(User user);
    //void onSuccessFromAuthentication(PlayerStats playerStats);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}

