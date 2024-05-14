package it.unimib.wordino.main.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class Highscore implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private long scoreId;
    @SerializedName("score")
    private int score;
    @SerializedName("date")
    private String date;
    public final static Creator<License> CREATOR = new Creator<License>() {
        public License createFromParcel(Parcel in) {
            return new License(in);
        }

        public License[] newArray(int size) {
            return (new License[size]);
        }

    };

    public Highscore(int score, String date){
        this.score = score;
        this.date = date;
    }

    @SuppressWarnings({
            "unchecked"
    })
    protected Highscore(Parcel in) {
        this.score = ((int) in.readValue((int.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
    }
    public Highscore() {}

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long id) {
        this.scoreId = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(License.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("score");
        sb.append('=');
        sb.append(((this.score == 0)?"<null>":""+this.score)); // ???? forse this.score == 0 non va bene
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null)?"<null>":this.date));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(score);
        dest.writeValue(date);
    }

    public void readFromParcel(Parcel source){
        this.score = source.readInt();
        this.date = source.readString();
    }

    public int describeContents() {
        return 0;
    }

}