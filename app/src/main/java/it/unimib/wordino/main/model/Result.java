package it.unimib.wordino.main.model;

/**
 * Class that represents the result of an action that requires
 * the use of a Web Service or a local database.
 */
public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return this instanceof Success;
    }
    public String getData(){
        return "";
    }

    /**
     * Class that represents a successful action during the interaction
     * with a Web Service or a local database.
     */
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
}
