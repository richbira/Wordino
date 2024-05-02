package it.unimib.wordino.main.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Definition implements Parcelable
{

    @SerializedName("definition")
    private String definition;
    @SerializedName("synonyms")
    private List<String> synonyms;
    @SerializedName("antonyms")
    private List<String> antonyms;
    @SerializedName("example")
    private String example;

    public final static Creator<Definition> CREATOR = new Creator<Definition>() {
        public Definition createFromParcel(Parcel in) {
            return new Definition(in);
        }

        public Definition[] newArray(int size) {
            return (new Definition[size]);
        }

    };

    @SuppressWarnings({
            "unchecked"
    })
    protected Definition(Parcel in) {
        this.definition = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.synonyms, String.class.getClassLoader()); //not sure
        in.readList(this.antonyms, String.class.getClassLoader()); //not sure
        this.example = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Definition() {}

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
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

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(definition);
        dest.writeList(synonyms);
        dest.writeList(antonyms);
        dest.writeValue(example);
    }

    public void readFromParcel(Parcel source){
        this.definition = source.readString();
        this.synonyms = source.createStringArrayList();
        this.antonyms = source.createStringArrayList();
        this.example = source.readString();
    }


    public int describeContents() {
        return 0;
    }

}