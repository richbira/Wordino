package it.unimib.wordino.main.model;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phonetic implements Parcelable
{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("license")
    @Expose
    private License license;
    public final static Creator<Phonetic> CREATOR = new Creator<Phonetic>() {


        public Phonetic createFromParcel(android.os.Parcel in) {
            return new Phonetic(in);
        }

        public Phonetic[] newArray(int size) {
            return (new Phonetic[size]);
        }

    }
            ;

    @SuppressWarnings({
            "unchecked"
    })
    protected Phonetic(android.os.Parcel in) {
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

    public Phonetic withText(String text) {
        this.text = text;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Phonetic withAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Phonetic withSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Phonetic withLicense(License license) {
        this.license = license;
        return this;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(text);
        dest.writeValue(audio);
        dest.writeValue(sourceUrl);
        dest.writeValue(license);
    }

    public int describeContents() {
        return 0;
    }

}