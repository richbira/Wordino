package it.unimib.wordino.main.model;

import android.os.Parcel;
import android.os.Parcelable;


public class WordApiResponse implements Parcelable {
    private String word;
    private String language;

    public WordApiResponse() {};

    public WordApiResponse(String word, String language) {
        this.word = word;
        this.language = language;
    }


    public String getWord() {
        return word;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.word);
        dest.writeString(this.language);
    }

    public void readFromParcel(Parcel source) {
        this.word = source.readString();
        this.language = source.readString();
    }

    protected WordApiResponse(Parcel in) {
        this.word = in.readString();
        this.language = in.readString();
    }

    public static final Parcelable.Creator<WordApiResponse> CREATOR = new Parcelable.Creator<WordApiResponse>() {
        @Override
        public WordApiResponse createFromParcel(Parcel source) {
            return new WordApiResponse(source);
        }

        @Override
        public WordApiResponse[] newArray(int size) {
            return new WordApiResponse[size];
        }
    };


}
