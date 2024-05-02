package it.unimib.wordino.main.model;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Word implements Parcelable
{

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("phonetic")
    @Expose
    private String phonetic;
    @SerializedName("phonetics")
    @Expose
    private List<Phonetic> phonetics; //todo secondo me è sbagliato, da fixare con una nuova classe phonetics
    @SerializedName("meanings")
    @Expose
    private List<Meaning> meanings;
    @SerializedName("license")
    @Expose
    private License license;
    @SerializedName("sourceUrls")
    @Expose
    private List<String> sourceUrls;
    public final static Creator<Word> CREATOR = new Creator<Word>() {


        public Word createFromParcel(android.os.Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return (new Word[size]);
        }

    }
            ;

    @SuppressWarnings({
            "unchecked"
    })
    protected Word(android.os.Parcel in) {
        this.word = ((String) in.readValue((String.class.getClassLoader())));
        this.phonetic = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.phonetics, (it.unimib.wordino.main.model.Phonetic.class.getClassLoader()));
        in.readList(this.meanings, (it.unimib.wordino.main.model.Meaning.class.getClassLoader()));
        this.license = ((License) in.readValue((License.class.getClassLoader())));
        in.readList(this.sourceUrls, (java.lang.String.class.getClassLoader()));
    }

    public Word() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Word withWord(String word) {
        this.word = word;
        return this;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public Word withPhonetic(String phonetic) {
        this.phonetic = phonetic;
        return this;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    public Word withPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
        return this;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public Word withMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
        return this;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Word withLicense(License license) {
        this.license = license;
        return this;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public void setSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
    }

    public Word withSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Word.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("word");
        sb.append('=');
        sb.append(((this.word == null)?"<null>":this.word));
        sb.append(',');
        sb.append("phonetic");
        sb.append('=');
        sb.append(((this.phonetic == null)?"<null>":this.phonetic));
        sb.append(',');
        sb.append("phonetics");
        sb.append('=');
        sb.append(((this.phonetics == null)?"<null>":this.phonetics));
        sb.append(',');
        sb.append("meanings");
        sb.append('=');
        sb.append(((this.meanings == null)?"<null>":this.meanings));
        sb.append(',');
        sb.append("license");
        sb.append('=');
        sb.append(((this.license == null)?"<null>":this.license));
        sb.append(',');
        sb.append("sourceUrls");
        sb.append('=');
        sb.append(((this.sourceUrls == null)?"<null>":this.sourceUrls));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(word);
        dest.writeValue(phonetic);
        dest.writeList(phonetics);
        dest.writeList(meanings);
        dest.writeValue(license);
        dest.writeList(sourceUrls);
    }

    public int describeContents() {
        return 0;
    }

}