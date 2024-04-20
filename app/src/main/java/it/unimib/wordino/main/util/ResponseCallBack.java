package it.unimib.wordino.main.util;

import it.unimib.wordino.main.model.WordApiResponse;

public interface ResponseCallBack {
    void onSuccess(String word);
    void onFailure(String errorMessage);

}
