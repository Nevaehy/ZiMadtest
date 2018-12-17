package com.heaven.zimadtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CatDog {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Animal> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Animal> getData() {
        return data;
    }

    public void setData(ArrayList<Animal> data) {
        this.data = data;
    }

    public class Animal implements Parcelable{

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("title")
        @Expose
        private String title;

        protected Animal(Parcel in) {
            url = in.readString();
            title = in.readString();
        }

        public final Creator<Animal> CREATOR = new Creator<Animal>() {
            @Override
            public Animal createFromParcel(Parcel in) {
                return new Animal(in);
            }

            @Override
            public Animal[] newArray(int size) {
                return new Animal[size];
            }
        };

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(url);
            parcel.writeString(title);
        }
    }

}
