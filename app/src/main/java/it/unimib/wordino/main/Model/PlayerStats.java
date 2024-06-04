package it.unimib.wordino.main.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int currentStreak;
    private int maxStreak;
    private int HighscoreTraining;
    private Map<String, Integer> guessDistribution;

    public PlayerStats() {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.currentStreak = 0;
        this.maxStreak = 0;
        this.HighscoreTraining = 0;
        this.guessDistribution = new HashMap<>();
        // Inizializza la mappa con chiavi da 1 a 5 con valori iniziali a 0
        for (int i = 1; i <= 5; i++) {
            guessDistribution.put(String.valueOf(i), 0);
        }
    }

    public int getHighscoreTraining() {
        return HighscoreTraining;
    }

    public void setHighscoreTraining(int highscoreTraining) {
        HighscoreTraining = highscoreTraining;
    }

    // Metodi getter e setter
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(int maxStreak) {
        this.maxStreak = maxStreak;
    }

    public Map<String, Integer> getGuessDistribution() {
        return guessDistribution;
    }

    public void setGuessDistribution(Map<String, Integer> guessDistribution) {
        this.guessDistribution = guessDistribution;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "gamesPlayed=" + gamesPlayed +
                ", gamesWon=" + gamesWon +
                ", gamesLost=" + gamesLost +
                ", currentStreak=" + currentStreak +
                ", maxStreak=" + maxStreak +
                ", guessDistribution=" + guessDistribution +
                '}';
    }

    // Metodo per aggiornare la distribuzione dei tentativi
    /*public void updateGuessDistribution(int guessCount) {
        if (guessDistribution.containsKey(guessCount)) {
            guessDistribution.put(guessCount, guessDistribution.get(guessCount) + 1);
        } else {
            guessDistribution.put(guessCount, 1);
        }
    }*/
    // Aggiungi altri metodi utili come incrementare le vittorie, aggiornare le sequenze, ecc.

}
