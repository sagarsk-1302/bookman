package com.example.bookman.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable {
    public Book(){}

    private String filename;
    private String fileurl;
    private List<String> genre;
    private int publishedyear;
    private String bookname;
    private String authorname;
    private String about;
    private Timestamp uploadedon;
    private String email;
    private String documentId;

    protected Book(Parcel in) {
        filename = in.readString();
        fileurl = in.readString();
        if (in.readByte() == 0x01) {
            genre = new ArrayList<String>();
            in.readList(genre, String.class.getClassLoader());
        } else {
            genre = null;
        }
        publishedyear = in.readInt();
        bookname = in.readString();
        authorname = in.readString();
        about = in.readString();
        uploadedon = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        email = in.readString();
        documentId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filename);
        dest.writeString(fileurl);
        if (genre == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genre);
        }
        dest.writeInt(publishedyear);
        dest.writeString(bookname);
        dest.writeString(authorname);
        dest.writeString(about);
        dest.writeValue(uploadedon);
        dest.writeString(email);
        dest.writeString(documentId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public int getPublishedyear() {
        return publishedyear;
    }

    public void setPublishedyear(int publishedyear) {
        this.publishedyear = publishedyear;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Timestamp getUploadedon() {
        return uploadedon;
    }

    public void setUploadedon(Timestamp uploadedon) {
        this.uploadedon = uploadedon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}