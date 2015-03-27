package com.niupiao.niupiao.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.niupiao.niupiao.Constants;

/**
 * Created by kevinchen on 2/17/15.
 */
public class User implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(name);
        dest.writeString(username);
    }

    public User(Parcel in) {
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        name = in.readString();
        username = in.readString();
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

    @SerializedName(Constants.JsonApi.Facebook.User.EMAIL)
    private String email;

    @SerializedName(Constants.JsonApi.Facebook.User.FIRST_NAME)
    private String firstName;

    @SerializedName(Constants.JsonApi.Facebook.User.LAST_NAME)
    private String lastName;

    @SerializedName(Constants.JsonApi.Facebook.User.NAME)
    private String name;

    @SerializedName(Constants.JsonApi.Facebook.User.USERNAME)
    private String username;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
