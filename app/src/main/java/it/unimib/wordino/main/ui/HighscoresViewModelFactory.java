package it.unimib.wordino.main.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.wordino.main.repository.IWordRepositoryLD;

public class HighscoresViewModelFactory implements ViewModelProvider.Factory{
    private final IWordRepositoryLD iWordRepositoryLD;

    public HighscoresViewModelFactory(IWordRepositoryLD iWordRepositoryLD) {
        this.iWordRepositoryLD = iWordRepositoryLD;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ScoresViewModel(iWordRepositoryLD);
    }
}
