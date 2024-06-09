package it.unimib.wordino.main.model;

/**
 * Class that represents the result of an action that requires
 * the use of a Web Service or a local database.
 */
public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return (this instanceof UserResponseSuccess) || (this instanceof Success);
    }
    public Object getData(){
        return "";
    }
    public static final class Success extends Result {
        private final String word;
        public Success(){
            this.word = "";
        }
        public Success(String word) {
            this.word = word;
        }
        @Override
        public String getData() {
            return word;
        }
    }

    /**
     * Class that represents an error occurred during the interaction
     * with a Web Service or a local database.
     */
    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }


    }

    public static final class UserResponseSuccess extends Result { //TODO Da checkare, da merge con Result
        private final User user;
        public UserResponseSuccess(User user) {
            super();
            this.user = user;
        }
        public User getData() { //Capire con Yoshiki
            return user;
        }
    }
}
