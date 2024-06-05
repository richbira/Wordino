package it.unimib.wordino.main.Model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import it.unimib.wordino.main.model.UserStat;

public class User implements Parcelable {
    private String name;
    private String email;
    private String idToken;

    UserStat userStat;

    //TODO Dao per salvare le statistiche del player? -> Usi Rooom

    public User(String name, String email, String idToken, UserStat userStat) {
        this.name = name;
        this.email = email;
        this.idToken = idToken;
        this.userStat = new UserStat();
    }
    public User(String name, String email, String idToken) {
        this.name = name;
        this.email = email;
        this.idToken = idToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", idToken='" + idToken + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.idToken);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.email = source.readString();
        this.idToken = source.readString();
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.idToken = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public UserStat getPlayerStats() {
        return userStat;
    }

    public void setPlayerStats(UserStat userStat) {
        this.userStat = userStat;
    }
}
