package it.unimib.wordino.main.model;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meaning implements Parcelable
{

    @SerializedName("partOfSpeech")
    @Expose
    private String partOfSpeech;
    @SerializedName("definitions")
    @Expose
    private List<Definition> definitions;
    @SerializedName("synonyms")
    @Expose
    private List<Object> synonyms;
    @SerializedName("antonyms")
    @Expose
    private List<Object> antonyms;
    public final static Creator<Meaning> CREATOR = new Creator<Meaning>() {


        public Meaning createFromParcel(android.os.Parcel in) {
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
    protected Meaning(android.os.Parcel in) {
        this.partOfSpeech = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.definitions, (it.unimib.wordino.main.model.Definition.class.getClassLoader()));
        in.readList(this.synonyms, (java.lang.Object.class.getClassLoader()));
        in.readList(this.antonyms, (java.lang.Object.class.getClassLoader()));
    }

    public Meaning() {
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public Meaning withPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
        return this;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public Meaning withDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
        return this;
    }

    public List<Object> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
    }

    public Meaning withSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
        return this;
    }

    public List<Object> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<Object> antonyms) {
        this.antonyms = antonyms;
    }

    public Meaning withAntonyms(List<Object> antonyms) {
        this.antonyms = antonyms;
        return this;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(partOfSpeech);
        dest.writeList(definitions);
        dest.writeList(synonyms);
        dest.writeList(antonyms);
    }

    public int describeContents() {
        return 0;
    }

}