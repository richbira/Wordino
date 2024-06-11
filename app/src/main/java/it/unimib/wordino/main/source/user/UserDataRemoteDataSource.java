package it.unimib.wordino.main.source.user;

import static it.unimib.wordino.main.util.Constants.FIREBASE_REALTIME_DATABASE;
import static it.unimib.wordino.main.util.Constants.FIREBASE_STATS_COLLECTION;
import static it.unimib.wordino.main.util.Constants.FIREBASE_USERS_COLLECTION;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.unimib.wordino.main.model.User;
import it.unimib.wordino.main.model.UserStat;

/**
 * Class that gets the user information using Firebase Realtime Database.
 */
public class UserDataRemoteDataSource extends BaseUserDataRemoteDataSource {

    private static final String TAG = UserDataRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;
    private final FirebaseDatabase firebaseDatabase;
    private MutableLiveData<Boolean> isTodayLiveData = new MutableLiveData<>();


    public UserDataRemoteDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE); //TODO Da settare costante
        databaseReference = firebaseDatabase.getReference().getRef();
        //isTodayLiveData = getIsTodayLiveData();
    }
        public void saveUserData(User user) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "User already present in Firebase Realtime Database");
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                } else {
                    Log.d(TAG, "User not present in Firebase Realtime Database");
                    databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).setValue(user)
                            .addOnSuccessListener(aVoid -> userResponseCallback.onSuccessFromRemoteDatabase(user))
                            .addOnFailureListener(e -> userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userResponseCallback.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }
        public MutableLiveData<UserStat> getUserStats(String tokenId) { // prendo le statistiche su firebase di un utente passando tokenId
            MutableLiveData<UserStat> liveData = new MutableLiveData<>();
            if (tokenId == null || tokenId.isEmpty()) {
                Log.e(TAG, "Token ID is null or empty");
                return liveData; // Returns an empty LiveData object.
            }
            databaseReference.child(FIREBASE_USERS_COLLECTION).child(tokenId).child(FIREBASE_STATS_COLLECTION)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                UserStat stats = new UserStat();
                                if (dataSnapshot.child("gamesPlayed").exists()) {
                                    stats.setGamesPlayed(dataSnapshot.child("gamesPlayed").getValue(Integer.class));
                                }
                                if (dataSnapshot.child("gamesWon").exists()) {
                                    stats.setGamesWon(dataSnapshot.child("gamesWon").getValue(Integer.class));
                                }
                                if (dataSnapshot.child("gamesLost").exists()) {
                                    stats.setGamesLost(dataSnapshot.child("gamesLost").getValue(Integer.class));
                                }
                                if (dataSnapshot.child("currentStreak").exists()) {
                                    stats.setCurrentStreak(dataSnapshot.child("currentStreak").getValue(Integer.class));
                                }
                                if (dataSnapshot.child("maxStreak").exists()) {
                                    stats.setMaxStreak(dataSnapshot.child("maxStreak").getValue(Integer.class));
                                }
                                if (dataSnapshot.child("highscoreTraining").exists()) {
                                    stats.setHighscoreTraining(dataSnapshot.child("highscoreTraining").getValue(Integer.class));
                                }

                                Map<String, Integer> guessDistribution = new HashMap<>();
                                if (dataSnapshot.child("guessDistribution").exists()) {
                                    for (DataSnapshot entry : dataSnapshot.child("guessDistribution").getChildren()) {
                                        guessDistribution.put(entry.getKey(), entry.getValue(Integer.class));
                                    }
                                }
                                stats.setGuessDistribution(guessDistribution);

                                Log.d(TAG, "Stats trovate " + stats);
                                liveData.setValue(stats);
                            } else {
                                Log.d(TAG, "No stats found for token ID: " + tokenId);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e(TAG, "Failed to read user stats: " + databaseError.getMessage());
                        }
                    });
            return liveData;
        }

    @Override
    public void updateUserStats(String idToken, UserStat userStat) {
        if (idToken == null || idToken.isEmpty()) {
            Log.e(TAG, "Token ID is null or empty");
            return;
        }
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).child(FIREBASE_STATS_COLLECTION).setValue(userStat)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User stats updated successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to update user stats: " + e.getLocalizedMessage()));
        Date today = new Date();
        setCurrentDailyDate(today);
        Log.d(TAG, "updateUserStats: "+today);
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).child("dailyChallengeDate").setValue(today)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Daily challenge date updated successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to update daily challenge date: " + e.getLocalizedMessage()));
    }
    public void updateGameResult(String idToken, boolean won, Integer guessCount) {
        if (idToken == null || idToken.isEmpty()) {
            Log.e(TAG, "Token ID is null or empty");
            return;
        }
        //Mi prendo i dati del idToken
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).child(FIREBASE_STATS_COLLECTION)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // Legge i dati dal database però lo faccio manuale perchè si spacca la deserilizzazione
                        UserStat stats = new UserStat(); // Crea un oggetto vuoto di UserStat
                        stats.setGamesPlayed(dataSnapshot.child("gamesPlayed").getValue(Integer.class));
                        stats.setGamesWon(dataSnapshot.child("gamesWon").getValue(Integer.class));
                        stats.setGamesLost(dataSnapshot.child("gamesLost").getValue(Integer.class));
                        stats.setCurrentStreak(dataSnapshot.child("currentStreak").getValue(Integer.class));
                        stats.setMaxStreak(dataSnapshot.child("maxStreak").getValue(Integer.class));
                        stats.setHighscoreTraining(dataSnapshot.child("highscoreTraining").getValue(Integer.class));

                        Map<String, Integer> guessDistribution = new HashMap<>();
                        for (DataSnapshot entry : dataSnapshot.child("guessDistribution").getChildren()) {
                            guessDistribution.put(entry.getKey(), entry.getValue(Integer.class));
                        }
                        stats.setGuessDistribution(guessDistribution);
                        Log.d(TAG, "Statistiche trovate " + stats.toString());
                        if (won) {
                            stats.updateStats(true, (guessCount+1));

                            Log.d(TAG, "User stats updated successfully on firebase");
                        } else {
                            stats.updateStats(false, (guessCount+1));
                            Log.d(TAG, "User stats updated successfully on firebase");
                        }
                        updateUserStats(idToken, stats);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "Failed to read user stats: " + databaseError.getMessage());
                    }
                });
    }
    public MutableLiveData<Boolean> getIsTodayLiveData() {
        return isTodayLiveData;
    }
    //Creare metodo che fetch data di daily challenge
    public void setIsTodayLiveData(String idToken) {
        if (idToken == null || idToken.isEmpty()) {
            Log.e(TAG, "Token ID is null or empty");
            isTodayLiveData.setValue(false);
            return;
        }
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).child("dailyChallengeDate")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d(TAG, "Daily challenge date found: " + dataSnapshot.getValue());
                            Map<String, Long> map = (Map<String, Long>) dataSnapshot.getValue();
                            Date dailyChallengeDate = new Date(map.get("time"));

                            // Verifica se dailyChallengeDate è uguale a oggi
                            boolean isDateToday = isDateToday(dailyChallengeDate);
                            isTodayLiveData.setValue(isDateToday);
                        } else {
                            Log.d(TAG, "No daily challenge date found for token ID: " + idToken);
                            isTodayLiveData.setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "Failed to read daily challenge date: " + databaseError.getMessage());
                        isTodayLiveData.setValue(false);
                    }
                });
    }

    private boolean isDateToday(Date date) {
        Calendar calendar = Calendar.getInstance();

        // Normalizza la data del challenge
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date normalizedChallengeDate = calendar.getTime();

        // Normalizza la data odierna
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date todayDate = calendar.getTime();

        // Confronta le date
        return normalizedChallengeDate.equals(todayDate);
    }
    public void setCurrentDailyDate(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        data = calendar.getTime();
    }


}
