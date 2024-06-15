package it.unimib.wordino.main.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class User implements Parcelable {
    //private String name;
    private String email;
    private String idToken;
    private UserStat userStat;

    private LocalDate dailyChallengeDate;

    public User(String name, String email, String idToken) {
        //this.name = name; //name da togliere
        this.email = email;
        this.idToken = idToken;
        this.userStat = new UserStat();
        setDefaultDailyChallengeDate();
    }
    //Default value calendar
    public void setDefaultDailyChallengeDate() {
        dailyChallengeDate = LocalDate.of(2000, 1, 1);
    }


    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
    public LocalDate getDailyChallengeDate() {
        return dailyChallengeDate;
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
        //dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.idToken);
    }



    protected User(Parcel in) {
        this.email = in.readString();
        this.idToken = in.readString();
        long tmpDate = in.readLong();
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

    public UserStat getUserStats() {
        return userStat;
    }

    public void setUserStats(UserStat userStat) {
        this.userStat = userStat;
    }
}
