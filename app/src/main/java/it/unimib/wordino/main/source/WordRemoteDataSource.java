package it.unimib.wordino.main.source;

import static it.unimib.wordino.main.util.Constants.ENGLISH;
import static it.unimib.wordino.main.util.Constants.FIREBASE_REALTIME_DATABASE;
import static it.unimib.wordino.main.util.Constants.FIREBASE_WORDS_COLLECTION;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unimib.wordino.main.model.wordmodel.Word;
import it.unimib.wordino.main.service.DictionaryWordApiService;
import it.unimib.wordino.main.service.RandomWordApiService;
import it.unimib.wordino.main.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordRemoteDataSource extends BaseWordRemoteDataSource{
    private static final String TAG = WordRemoteDataSource.class.getSimpleName();

    private final DictionaryWordApiService specificWordApiService;
    private final RandomWordApiService randomWordApiService;

    private final DatabaseReference databaseReference;
    private final FirebaseDatabase firebaseDatabase;

    private MutableLiveData<String> wordLiveData;
    private MutableLiveData<Date> dateLiveData;


    public WordRemoteDataSource(){
        this.specificWordApiService = ServiceLocator.getInstance().getSpecificWordApiService();
        this.randomWordApiService = ServiceLocator.getInstance().getRandomWordApiService();
        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE);
        databaseReference = firebaseDatabase.getReference().getRef();
    }
    @Override
    public void getSpecificWord(String word) {
        Log.d(TAG, "INIZIO CALL API con parola " + word);


        Call<List<Word>> specificWordResponseCall = specificWordApiService.getSpecificWord(word);


        specificWordResponseCall.enqueue(new Callback<List<Word>>() {

            @Override
            public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) { //TODO SETUP ROOM PER SALVARE L'OGGETTO
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<Word> specificWord = response.body();
                    Log.d(TAG, "Successful fetch of word: " + specificWord.get(0).getWord());
                    wordCallback.onSuccessFromRemoteSpecific(specificWord.get(0));
                } else {
                    wordCallback.onFailureFromRemoteSpecific("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                wordCallback.onFailureFromRemoteSpecific("Error wordNotFound");
            }
        });
    }

    @Override
    public void getSpecificWordCheck(String word) {
        Log.d(TAG, "INIZIO CALL API check con parola " + word);


        Call<List<Word>> specificWordCheckResponseCall = specificWordApiService.getSpecificWord(word);


        specificWordCheckResponseCall.enqueue(new Callback<List<Word>>() {

            @Override
            public void onResponse(@NonNull Call<List<Word>> call, @NonNull Response<List<Word>> response) { //TODO SETUP ROOM PER SALVARE L'OGGETTO
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<Word> specificWord = response.body();
                    Log.d(TAG, "Successful fetch of word: " + specificWord.get(0).getWord());
                    wordCallback.onSuccessFromRemoteSpecificCheck(specificWord.get(0));

                } else {
                    wordCallback.onFailureFromRemoteSpecificCheck("La parola non esiste (specificwordcheck)");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Word>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted() + t);
                wordCallback.onFailureFromRemoteSpecificCheck("Error wordNotFound");
            }
        });
    }


    @Override
    public void getRandomWord(){
        Log.d(TAG,"Random word fetch start");
        Call<List<String>> randomWordResponseCall = randomWordApiService.getRandomWord(5, ENGLISH);

        randomWordResponseCall.enqueue(new Callback<List<String>>() { //todo forse estrarre la logica in una nuova classe (videolezione livedata 26:00)

            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Log.d(TAG, "OnResponse: + " + response.isSuccessful());
                    List<String> newWord = response.body();
                    Log.d(TAG, "Successful fetch of random word " + newWord.get(0));
                    wordCallback.onSuccessFromRemoteRandom(newWord.get(0));
                } else {
                    wordCallback.onFailureFromRemoteRandom("Errore nella chiamata API 1 ");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                Log.d(TAG, "OnFailure: + " + call.isExecuted());
                wordCallback.onFailureFromRemoteRandom("Errore nella chiamata API 2" + t);
            }
        });
    }

    public void setWordOfTheDay(String word) {
        // Crea un oggetto HashMap per conservare i dati.
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("word", word);
        dataMap.put("date", LocalDate.now());  // Aggiunge la data odierna.

        // "push()" genera un ID univoco per ogni nuova parola, per evitare sovrascritture.
        databaseReference.child("WordOfTheDay").push().setValue(dataMap);
    }

    public MutableLiveData<String> getWord(){
        return wordLiveData;
    }

    public MutableLiveData<Date> getDate(){
        return dateLiveData;
    }

    public void getWordFromFirebase() {
        LocalDate today = LocalDate.now();
        // Log initial access to method.
        Log.d(TAG, "getWord: Fetching word and date from Firebase");
        Log.d(TAG, "" + today);
        // Access the Firebase dat abase reference to "WordOfTheDay".
        databaseReference.child("WordOfTheDay").orderByChild("date")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            boolean found = false;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                DataSnapshot dateSnapshot = snapshot.child("date");
                                DataSnapshot wordSnapshot = snapshot.child("word");
                                int day = dateSnapshot.child("dayOfMonth").getValue(Integer.class);
                                int month = dateSnapshot.child("monthValue").getValue(Integer.class);
                                int year = dateSnapshot.child("year").getValue(Integer.class);
                                String wordString = wordSnapshot.getValue(String.class);
                                String dateStr = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                                Log.d(TAG, dateStr + " --- " + today);


                                if (year == today.getYear() && month == today.getMonthValue() && day == today.getDayOfMonth()) {
                                    String word = snapshot.child("word").getValue(String.class);
                                    Log.d(TAG, "Data Retrieved: Word = " + word + ", Date = " + day + "/" + month + "/" + year);
                                    wordCallback.onSuccessFromRemoteFirebaseWord(wordString);
                                    found = true;

                                }
                                if (!found) {
                                    Log.d(TAG, "No data found for the word with today's date.");
                                    wordCallback.onFailureFromRemoteFirebaseWord("Parola di oggi non presente nel firebase");
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Log any errors.
                        System.err.println("Listener was cancelled, error: " + databaseError.getMessage());
                    }
                });
    }


}

