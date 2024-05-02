package it.unimib.wordino.main.model;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Definition implements Parcelable
{

    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("synonyms")
    @Expose
    private List<Object> synonyms;
    @SerializedName("antonyms")
    @Expose
    private List<Object> antonyms;
    @SerializedName("example")
    @Expose
    private String example;
    public final static Creator<Definition> CREATOR = new Creator<Definition>() {


        public Definition createFromParcel(android.os.Parcel in) {
            return new Definition(in);
        }

        public Definition[] newArray(int size) {
            return (new Definition[size]);
        }

    }
            ;

    @SuppressWarnings({
            "unchecked"
    })
    protected Definition(android.os.Parcel in) {
        this.definition = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.synonyms, (java.lang.Object.class.getClassLoader()));
        in.readList(this.antonyms, (java.lang.Object.class.getClassLoader()));
        this.example = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Definition() {
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Definition withDefinition(String definition) {
        this.definition = definition;
        return this;
    }

    public List<Object> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
    }

    public Definition withSynonyms(List<Object> synonyms) {
        this.synonyms = synonyms;
        return this;
    }

    public List<Object> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<Object> antonyms) {
        this.antonyms = antonyms;
    }

    public Definition withAntonyms(List<Object> antonyms) {
        this.antonyms = antonyms;
        return this;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Definition withExample(String example) {
        this.example = example;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Definition.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("definition");
        sb.append('=');
        sb.append(((this.definition == null)?"<null>":this.definition));
        sb.append(',');
        sb.append("synonyms");
        sb.append('=');
        sb.append(((this.synonyms == null)?"<null>":this.synonyms));
        sb.append(',');
        sb.append("antonyms");
        sb.append('=');
        sb.append(((this.antonyms == null)?"<null>":this.antonyms));
        sb.append(',');
        sb.append("example");
        sb.append('=');
        sb.append(((this.example == null)?"<null>":this.example));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(definition);
        dest.writeList(synonyms);
        dest.writeList(antonyms);
        dest.writeValue(example);
    }

    public int describeContents() {
        return 0;
    }

}