package it.unimib.wordino.main.source.user;

import static it.unimib.wordino.main.util.Constants.FIREBASE_REALTIME_DATABASE;
import static it.unimib.wordino.main.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.wordino.main.util.Constants.INVALID_USER_ERROR;
import static it.unimib.wordino.main.util.Constants.UNEXPECTED_ERROR;
import static it.unimib.wordino.main.util.Constants.USER_COLLISION_ERROR;
import static it.unimib.wordino.main.util.Constants.WEAK_PASSWORD_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;
import it.unimib.wordino.main.model.PlayerStats;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.unimib.wordino.main.Model.User;


/**
 * Class that manages the user authentication using Firebase Authentication.
 */
public class UserAuthenticationRemoteDataSource extends BaseUserAuthenticationRemoteDataSource {
    //Classe che gestisce l'autenticazione dell'utente
    private static final String TAG = UserAuthenticationRemoteDataSource.class.getSimpleName();

    private final FirebaseAuth firebaseAuth;

    public UserAuthenticationRemoteDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public User getLoggedUser() { // Prendo l'utente loggato
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            return null;
        } else {
            return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
        }
    }
    @Override
    public void signUp(String email, String password) { // Registrazione
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    userResponseCallback.onSuccessFromAuthentication(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())

                    );

                } else {
                    userResponseCallback.onFailureFromAuthentication(getErrorMessage(task.getException()));
                }
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                userResponseCallback.onFailureFromAuthentication(getErrorMessage(task.getException()));
            }
        });
    }

    /*private void saveUserStats(User user) { //TODO Da cancellare
        // Initialize default statistics
        PlayerStats defaultStats = new PlayerStats();

        // Save the user statistics in Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE);
        DatabaseReference databaseReference = firebaseDatabase.getReference().getRef();
        DatabaseReference userRef = databaseReference.child("users").child(user.getIdToken()).child("stats");
        userRef.setValue(defaultStats).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "User stats saved successfully");
            userResponseCallback.onSuccessFromAuthentication(user);
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Failed to save user stats", e);
            userResponseCallback.onFailureFromAuthentication("Failed to save user stats: " + e.getMessage());
        });
    }*/


    @Override
    public void logout() { // Logout
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.removeAuthStateListener(this);
                    Log.d(TAG, "User logged out");
                    userResponseCallback.onSuccessLogout();
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
        firebaseAuth.signOut();
    }


    @Override
    public void signIn(String email, String password) { // Login
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser(); //Prendo User
                if (firebaseUser != null) { //Se esiste
                    userResponseCallback.onSuccessFromAuthentication(
                            new User(firebaseUser.getDisplayName(), email, firebaseUser.getUid())
                    );
                } else {
                    userResponseCallback.onFailureFromAuthentication(getErrorMessage(task.getException()));
                }
            } else {
                userResponseCallback.onFailureFromAuthentication(getErrorMessage(task.getException()));
            }
        });
    }

    @Override
    public void signInWithGoogle(String idToken) { // Login con Google
        if (idToken !=  null) {
            // Got an ID token from Google. Use it to authenticate with Firebase.
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuth.signInWithCredential(firebaseCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        userResponseCallback.onSuccessFromAuthentication(
                                new User(firebaseUser.getDisplayName(),
                                        firebaseUser.getEmail(),
                                        firebaseUser.getUid()
                                )
                        );
                    } else {
                        userResponseCallback.onFailureFromAuthentication(
                                getErrorMessage(task.getException()));
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    userResponseCallback.onFailureFromAuthentication(getErrorMessage(task.getException()));
                }
            });
        }
    }

    @Override
    public void resetPassword(String email){
        firebaseAuth.sendPasswordResetEmail(email);
    } // Reset password

    private String getErrorMessage(Exception exception) { // Gestione errori
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            return WEAK_PASSWORD_ERROR;
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return INVALID_CREDENTIALS_ERROR;
        } else if (exception instanceof FirebaseAuthInvalidUserException) {
            return INVALID_USER_ERROR;
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            return USER_COLLISION_ERROR;
        }
        return UNEXPECTED_ERROR;
    }
}
