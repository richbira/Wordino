package it.unimib.wordino.main.model.wordmodel;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
@Entity
public class Meaning implements Parcelable
{

    @SerializedName("partOfSpeech")
    private String partOfSpeech;
    @Embedded(prefix = "definitions_")
    @SerializedName("definitions")
    private List<Definition> definitions;
    @Embedded(prefix = "synonyms_")
    @SerializedName("synonyms")
    private List<String> synonyms;
    @Embedded(prefix = "antonyms_")
    @SerializedName("antonyms")
    private List<String> antonyms;
    public final static Creator<Meaning> CREATOR = new Creator<Meaning>() {
        public Meaning createFromParcel(Parcel in) {
            return new Meaning(in);
        }

        public Meaning[] newArray(int size) {
            return (new Meaning[size]);
        }

    }
            ;

    @SuppressWarnings({
            "unchecked"
    })
    protected Meaning(Parcel in) {
        this.partOfSpeech = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.definitions, (Definition.class.getClassLoader()));
        in.readList(this.synonyms, String.class.getClassLoader()); //NOT SURE
        in.readList(this.antonyms, String.class.getClassLoader()); //NOT SURE
    }
    public Meaning() {
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Meaning.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("partOfSpeech");
        sb.append('=');
        sb.append(((this.partOfSpeech == null)?"<null>":this.partOfSpeech));
        sb.append(',');
        sb.append("definitions");
        sb.append('=');
        sb.append(((this.definitions == null)?"<null>":this.definitions));
        sb.append(',');
        sb.append("synonyms");
        sb.append('=');
        sb.append(((this.synonyms == null)?"<null>":this.synonyms));
        sb.append(',');
        sb.append("antonyms");
        sb.append('=');
        sb.append(((this.antonyms == null)?"<null>":this.antonyms));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(partOfSpeech);
        dest.writeList(definitions);
        dest.writeList(synonyms);
        dest.writeList(antonyms);
    }

    public void readFromParcel(Parcel source){
        this.partOfSpeech = source.readString();
        this.definitions = source.createTypedArrayList(Definition.CREATOR);
        this.synonyms = source.createStringArrayList();
        this.antonyms = source.createStringArrayList();
    }

    public int describeContents() {
        return 0;
    }

}