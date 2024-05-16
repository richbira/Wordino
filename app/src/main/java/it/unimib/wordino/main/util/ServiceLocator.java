package it.unimib.wordino.main.util;

import android.app.Application;

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


    public RandomWordApiService getWordApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RANDOM_WORD_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RandomWordApiService.class);
    }
    /**
     * Creates an instance of IUserRepository.
     * @return An instance of IUserRepository.
     */
    public IUserRepository getUserRepository(Application application) {
        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationRemoteDataSource();

        BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserDataRemoteDataSource();

        return (IUserRepository) new UserRepository(userRemoteAuthenticationDataSource,
                userDataRemoteDataSource);
    }
}
