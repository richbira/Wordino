package it.unimib.wordino.main.ui.welcome;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.wordino.main.repository.user.IUserRepository;


/**
 * Custom ViewModelProvider to be able to have a custom constructor
 * for the UserViewModel class.
 */
public class UserViewModelFactory implements ViewModelProvider.Factory {

    private final IUserRepository userRepository;

    public UserViewModelFactory(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) { //Da sistemare come quello del prof
        if (UserViewModel.class.isAssignableFrom(modelClass)) {
            // Here you ensure that the modelClass is indeed assignable to UserViewModel.
            return modelClass.cast(new UserViewModel(userRepository));
        } else {
            // If not, throw an exception to inform that the requested model class is not supported.
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
