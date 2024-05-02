package it.unimib.wordino.main.util;

import android.app.Application;

import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.service.RandomWordApiService;
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
/*
    public NewsRoomDatabase getNewsDao(Application application) {
        return NewsRoomDatabase.getDatabase(application);
    }

 */
}
