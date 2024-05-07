package it.unimib.wordino.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Phonetic implements Parcelable
{

    @SerializedName("text")
    private String text;
    @SerializedName("audio")
    private String audio;
    @SerializedName("sourceUrl")
    private String sourceUrl;
    @Embedded(prefix = "license_")
    @SerializedName("license")
    private License license;
    public final static Parcelable.Creator<Phonetic> CREATOR = new Parcelable.Creator<Phonetic>() {

        @Override
        public Phonetic createFromParcel(Parcel in) {
            return new Phonetic(in);
        }
        @Override
        public Phonetic[] newArray(int size) {
            return (new Phonetic[size]);
        }

    }
            ;

    @SuppressWarnings({
            "unchecked"
    })
    protected Phonetic(Parcel in) {
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.audio = ((String) in.readValue((String.class.getClassLoader())));
        this.sourceUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.license = ((License) in.readValue((License.class.getClassLoader())));
    }
    public Phonetic() {
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }
    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public License getLicense() {
        return license;
    }
    public void setLicense(License license) {
        this.license = license;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Phonetic.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("text");
        sb.append('=');
        sb.append(((this.text == null)?"<null>":this.text));
        sb.append(',');
        sb.append("audio");
        sb.append('=');
        sb.append(((this.audio == null)?"<null>":this.audio));
        sb.append(',');
        sb.append("sourceUrl");
        sb.append('=');
        sb.append(((this.sourceUrl == null)?"<null>":this.sourceUrl));
        sb.append(',');
        sb.append("license");
        sb.append('=');
        sb.append(((this.license == null)?"<null>":this.license));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(text);
        dest.writeValue(audio);
        dest.writeValue(sourceUrl);
        dest.writeValue(license);
    }

    public void readFromParcel(Parcel source){
        this.text = source.readString();
        this.audio = source.readString();
        this.sourceUrl = source.readString();
        this.license = source.readParcelable(License.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

}