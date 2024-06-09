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

    public UserDataRemoteDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE); //TODO Da settare costante
        databaseReference = firebaseDatabase.getReference().getRef();
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
        public LiveData<UserStat> getUserStats(String tokenId) { // prendo le statistiche su firebase di un utente passando tokenId
            MutableLiveData<UserStat> liveData = new MutableLiveData<>();
            if (tokenId == null || tokenId.isEmpty()) {
                Log.e(TAG, "Token ID is null or empty");
                return liveData; // Returns an empty LiveData object.
            }
            databaseReference.child(FIREBASE_USERS_COLLECTION).child(tokenId).child(FIREBASE_STATS_COLLECTION)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // Legge i dati dal database però lo faccio manuale perchè si spacca la deserilizzazione
                            UserStat stats = null; // Crea un oggetto vuoto di UserStat
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

                            if (stats != null) {
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
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).child("userStats").setValue(userStat)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User stats updated successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to update user stats: " + e.getLocalizedMessage()));
    }


}
