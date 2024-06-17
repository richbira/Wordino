package it.unimib.wordino.main.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.wordino.main.repository.IWordRepositoryLD;

public class GameBoardViewModelTrainingFactory implements ViewModelProvider.Factory{

    private final IWordRepositoryLD iWordRepositoryLD;

    public GameBoardViewModelTrainingFactory(IWordRepositoryLD iWordRepositoryLD) {
        this.iWordRepositoryLD = iWordRepositoryLD;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GameBoardViewModelTraining(iWordRepositoryLD);
    }
}