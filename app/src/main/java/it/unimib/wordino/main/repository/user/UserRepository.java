package it.unimib.wordino.main.repository.user;
import androidx.lifecycle.MutableLiveData;

import java.util.Set;

import it.unimib.wordino.Model.User;
import it.unimib.wordino.main.data.Result;
import it.unimib.wordino.main.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.wordino.main.source.user.BaseUserDataRemoteDataSource;

/**
 * Repository class to get the user information.
 */
public class UserRepository implements IUserRepository, UserResponseCallback {

    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    private final MutableLiveData<Result> userMutableLiveData;
    private final MutableLiveData<Result> userPreferencesMutableLiveData;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource,
                          BaseUserDataRemoteDataSource userDataRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.userPreferencesMutableLiveData = new MutableLiveData<>();
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
    }

    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered) {
        if (isUserRegistered) {
            signIn(email, password);
        } else {
            signUp(email, password);
        }
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getGoogleUser(String idToken) {
        signInWithGoogle(idToken);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getUserPreferences(String idToken) {
        userDataRemoteDataSource.getUserPreferences(idToken);
        return userPreferencesMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public void signUp(String email, String password) {
        userRemoteDataSource.signUp(email, password);
    }

    @Override
    public void signIn(String email, String password) {
        userRemoteDataSource.signIn(email, password);
    }

    @Override
    public void signInWithGoogle(String token) {
        userRemoteDataSource.signInWithGoogle(token);
    }

    @Override
    public void saveUserPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken) {
        userDataRemoteDataSource.saveUserPreferences(favoriteCountry, favoriteTopics, idToken);
    }

    @Override
    public void onSuccessFromAuthentication(User user) {
        if (user != null) {
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromGettingUserPreferences() {
        userPreferencesMutableLiveData.postValue(new Result.UserResponseSuccess(null));
    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {

    }

    public void onSuccessDeletion() {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(null);
        userMutableLiveData.postValue(result);
    }

    public void onFailureFromRemote(Exception exception) {

    }

    public void onFailureFromLocal(Exception exception) {

    }

    public void onFailureFromCloud(Exception exception) {

    }
}
