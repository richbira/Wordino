package it.unimib.wordino.main.repository;

import java.util.List;

import it.unimib.wordino.main.model.Word;

public interface ISpecificWordRepository {

    void fetchSpecificWord(String word);
    void saveWordInDatabase(List<Word> word);
}
