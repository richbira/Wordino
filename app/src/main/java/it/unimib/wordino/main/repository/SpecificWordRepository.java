package it.unimib.wordino.main.repository;

import android.app.Application;

import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.ui.DailyFragment;
import it.unimib.wordino.main.util.ResponseCallBack;
import it.unimib.wordino.main.util.ServiceLocator;

public class SpecificWordRepository implements ISpecificWordRepository {

    private static final String TAG = DailyFragment.class.getSimpleName();
    private final ResponseCallBack responseCallback;
    private final Application application;
    private final DictionaryWordApiService specificWordApiService;

    public SpecificWordRepository(Application application, ResponseCallBack responseCallBack){
        this.application = application;
        this.responseCallback = responseCallBack;
        this.specificWordApiService = ServiceLocator.getInstance().getSpecificWordApiService();
    }

    @Override
    public void fetchSpecificWord(String word) { //TODO class

    }
}
