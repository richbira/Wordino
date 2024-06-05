package it.unimib.wordino.main.util;

import android.app.Application;

import it.unimib.wordino.main.database.WordinoRoomDatabase;
import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.repository.IWordRepositoryLD;
import it.unimib.wordino.main.repository.WordRepositoryLD;
import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.source.BaseWordLocalDataSource;
import it.unimib.wordino.main.source.BaseWordRemoteDataSource;
import it.unimib.wordino.main.source.WordLocalDataSource;
import it.unimib.wordino.main.source.WordRemoteDataSource;
import it.unimib.wordino.main.repository.user.IUserRepository;
import it.unimib.wordino.main.repository.user.UserRepository;
import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.wordino.main.source.user.BaseUserDataRemoteDataSource;
import it.unimib.wordino.main.source.user.UserAuthenticationRemoteDataSource;
import it.unimib.wordino.main.source.user.UserDataRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *  Registry to provide the dependencies for the classes
 *  used in the application.
 */
public class ServiceLocator {
    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public WordinoRoomDatabase getNewsDao(Application application) {
        return WordinoRoomDatabase.getDatabase(application);
    }

    public IWordRepositoryLD getWordRepositoryLD(Application application){
        BaseWordRemoteDataSource wordRemoteDataSource;
        BaseWordLocalDataSource wordLocalDataSource;

        wordRemoteDataSource = new WordRemoteDataSource();
        wordLocalDataSource = new WordLocalDataSource(getNewsDao(application));


        return new WordRepositoryLD(wordRemoteDataSource, wordLocalDataSource);

    }

    public RandomWordApiService getRandomWordApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RANDOM_WORD_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RandomWordApiService.class);
    }

    public DictionaryWordApiService getSpecificWordApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DICTIONARY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(DictionaryWordApiService.class);
    }

    public WordinoRoomDatabase getWordinoDao(Application application) {
        return WordinoRoomDatabase.getDatabase(application);
    }


    /**
     * Creates an instance of IUserRepository.
     * @return An instance of IUserRepository.
     */
    public IUserRepository getUserRepository(Application application) {
        BaseUserDataRemoteDataSource userDataRemoteDataSource = new UserDataRemoteDataSource();
        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource = new UserAuthenticationRemoteDataSource();
        return (IUserRepository) new UserRepository(userRemoteAuthenticationDataSource,
                userDataRemoteDataSource);
    }
}
