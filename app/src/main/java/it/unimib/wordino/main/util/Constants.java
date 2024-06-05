package it.unimib.wordino.main.util;

public class Constants {

    //Costanti per le lingue
    public static final String ENGLISH = "en";
    public static final String PACKAGE_NAME = "it.unimib.wordino";
    public static final String RANDOM_WORD_API_BASE_URL = "https://random-word-api.herokuapp.com/";

    public static final String DICTIONARY_API_BASE_URL = "https://api.dictionaryapi.dev/";

    public static final String WORDINO_DATABASE_NAME = "wordino_db";

    // Constants for Room database
    public static final String NEWS_DATABASE_NAME = "news_db";
    public static final int DATABASE_VERSION = 1;

    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";
    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String INVALID_USER_ERROR = "invalidUserError";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";


    // Constants for SharedPreferences
    public static final String SHARED_PREFERENCES_FILE_NAME = "";
    public static final String SHARED_PREFERENCES_COUNTRY_OF_INTEREST = "country_of_interest";
    public static final String SHARED_PREFERENCES_TOPICS_OF_INTEREST = "topics_of_interest";
    public static final String SHARED_PREFERENCES_FIRST_LOADING = "first_loading";

    // Constants for EncryptedSharedPreferences
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "it.unimib.wordino.shared_prefs"; //TODO change this
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String PASSWORD = "password";
    public static final String ID_TOKEN = "google_token";

    // Constants for encrypted files
    public static final String ENCRYPTED_DATA_FILE_NAME = "it.unimib.wordino.encrypted_file.txt";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;

    // Constants for Firebase Realtime Database
    public static final String FIREBASE_REALTIME_DATABASE = "https://wordino-6452d-default-rtdb.europe-west1.firebasedatabase.app/";
    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_STATS_COLLECTION = "userStat";
}
