package it.unimib.wordino.main.source.user;

import static it.unimib.wordino.main.util.Constants.FIREBASE_REALTIME_DATABASE;
import static it.unimib.wordino.main.util.Constants.FIREBASE_STATS_COLLECTION;
import static it.unimib.wordino.main.util.Constants.FIREBASE_USERS_COLLECTION;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

import it.unimib.wordino.main.Model.User;
import it.unimib.wordino.main.model.PlayerStats;
import it.unimib.wordino.main.util.SharedPreferencesUtil;

/**
 * Class that gets the user information using Firebase Realtime Database.
 */
public class UserDataRemoteDataSource extends BaseUserDataRemoteDataSource {

    private static final String TAG = UserDataRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;

    public UserDataRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE); //TODO Da settare costante
        databaseReference = firebaseDatabase.getReference().getRef();
    }

    public void saveUserData(User user) {
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "User already present in Firebase Realtime Database");
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                } else {
                    Log.d(TAG, "User not present in Firebase Realtime Database");
                    // Salvataggio dell'utente
                    userRef.setValue(user)
                            .addOnSuccessListener(aVoid -> {
                                userResponseCallback.onSuccessFromRemoteDatabase(user);
                                // Salvataggio delle statistiche solo se l'utente viene salvato con successo
                                PlayerStats playerStats = new PlayerStats(); // Assicurati che PlayerStats sia correttamente inizializzato
                                userRef.child(FIREBASE_STATS_COLLECTION).setValue(playerStats)
                                        .addOnSuccessListener(voidStats -> userResponseCallback.onSuccessFromRemoteDatabase(user)) // Ho cambiato 'aVoid' in 'voidStats'
                                        .addOnFailureListener(e -> userResponseCallback.onFailureFromRemoteDatabase("Failed to save player stats: " + e.getLocalizedMessage()));
                            })
                            .addOnFailureListener(e -> userResponseCallback.onFailureFromRemoteDatabase("Failed to save user: " + e.getLocalizedMessage()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userResponseCallback.onFailureFromRemoteDatabase("Database error: " + databaseError.getMessage());
            }
        });
    }



    //TODO aggiungo i metodi per aggiornare le statistiche del player

}
