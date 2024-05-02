package it.unimib.wordino.main.model;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License implements Parcelable
{

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    public final static Creator<License> CREATOR = new Creator<License>() {
        public License createFromParcel(Parcel in) {
            return new License(in);
        }

        public License[] newArray(int size) {
            return (new License[size]);
        }

    }
            ;

    @SuppressWarnings({
            "unchecked"
    })
    protected License(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public License() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(License.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(url);
    }

    public void readFromParcel(Parcel source){
        this.name = source.readString();
        this.url = source.readString();
    }

    public int describeContents() {
        return 0;
    }

}