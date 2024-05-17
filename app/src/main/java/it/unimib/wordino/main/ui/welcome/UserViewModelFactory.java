package it.unimib.wordino.main.ui.welcome;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.wordino.main.repository.user.IUserRepository;
    public class UserViewModelFactory implements ViewModelProvider.Factory { // Factory per la creazione del ViewModel dell'utente

        private final IUserRepository userRepository;

        public UserViewModelFactory(IUserRepository userRepository) {
            this.userRepository = userRepository;
        }

        /*@NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserViewModel(userRepository);
        }*/
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (UserViewModel.class.isAssignableFrom(modelClass)) {
                return modelClass.cast(new UserViewModel(userRepository));
            }
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }

