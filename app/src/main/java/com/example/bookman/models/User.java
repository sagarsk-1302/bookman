package com.example.bookman.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String username;
    private String password;
    private String email;
    private List<String> uploadedbooks;

    public User(){}

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        if (in.readByte() == 0x01) {
            uploadedbooks = new ArrayList<String>();
            in.readList(uploadedbooks, String.class.getClassLoader());
        } else {
            uploadedbooks = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        if (uploadedbooks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(uploadedbooks);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getUploadedbooks() {
        return uploadedbooks;
    }

    public void setUploadedbooks(List<String> uploadedbooks) {
        this.uploadedbooks = uploadedbooks;
    }
}