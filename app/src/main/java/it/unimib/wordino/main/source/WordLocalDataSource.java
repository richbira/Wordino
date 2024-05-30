package it.unimib.wordino.main.source;

import it.unimib.wordino.main.database.WordinoDao;
import it.unimib.wordino.main.database.WordinoRoomDatabase;
import it.unimib.wordino.main.model.wordmodel.Word;

public class WordLocalDataSource extends BaseWordLocalDataSource {
    private final WordinoDao wordinoDao;

    public WordLocalDataSource(WordinoRoomDatabase wordinoRoomDatabase) {
        this.wordinoDao = wordinoRoomDatabase.wordinoDao();

    }
    @Override
    public void getWord(Word word){

    }
    @Override
    public void insertWord(Word word){
        wordinoDao.insertWord(word); //todo cambiare da wordlist a word
    }
}
