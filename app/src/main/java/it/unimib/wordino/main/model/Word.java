package it.unimib.wordino.main.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class Word implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private long wordId;
    @ColumnInfo(name = "word")
    @SerializedName("word")
    private String word;
    //@SerializedName("phonetic")
    @Ignore
    private String phonetic;
    //@Embedded(prefix = "phonetics_")
    //@SerializedName("phonetics")
    @Ignore
    private List<Phonetic> phonetics;

    //@Embedded(prefix = "meanings_")
    //@SerializedName("meanings")
    @Ignore
    private List<Meaning> meanings;
    //@Embedded(prefix = "license_")
    //@SerializedName("license")
    @Ignore
    private License license;
    //@Embedded(prefix = "source_urls_")
    //@SerializedName("sourceUrls")
    @Ignore
    private List<String> sourceUrls;

    public final static Creator<Word> CREATOR = new Creator<Word>() {
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return (new Word[size]);
        }

    };
    @Ignore
    public Word() {}

    @SuppressWarnings({
            "unchecked"
    })
    protected Word(Parcel in) {
        this.word = ((String) in.readValue((String.class.getClassLoader())));
        this.phonetic = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.phonetics, (it.unimib.wordino.main.model.Phonetic.class.getClassLoader()));
        in.readList(this.meanings, (it.unimib.wordino.main.model.Meaning.class.getClassLoader()));
        this.license = ((License) in.readValue((License.class.getClassLoader())));
        in.readList(this.sourceUrls, (java.lang.String.class.getClassLoader()));
    }

    public Word(String word, String phonetic, List<Phonetic> phonetics, List<Meaning> meanings, License license, List<String> sourceUrls ) {
        this.word = word;
        this.phonetic = phonetic;
        this.phonetics = phonetics;
        this.meanings = meanings;
        this.license = license;
        this.sourceUrls = sourceUrls;
    }

    public Word(long wordId, String word){
        this.wordId = wordId;
        this.word = word;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long id) {
        this.wordId = id;
    }


    public String getPhonetic() {
        return phonetic;
    }
    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }
    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }
    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public License getLicense() {
        return license;
    }
    public void setLicense(License license) {
        this.license = license;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }
    public void setSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
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
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.word);
        dest.writeValue(this.phonetic);
        dest.writeList(this.phonetics);
        dest.writeList(this.meanings);
        dest.writeValue(this.license);
        dest.writeList(this.sourceUrls);
    }

    public void readFromParcel(Parcel source){
        this.word = source.readString();
        this.phonetic = source.readString();
        this.phonetics = source.createTypedArrayList(Phonetic.CREATOR);
        this.meanings = source.createTypedArrayList(Meaning.CREATOR);
        this.license = source.readParcelable(License.class.getClassLoader());
        this.sourceUrls = source.createStringArrayList();
    }



    public int describeContents() {
        return 0;
    }

}