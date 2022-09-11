package com.telstra.androidexercise.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RowsData implements Parcelable {
   @SerializedName("title")
   String title;
    @SerializedName("description")  String description;
    @SerializedName("imageHref") String imageHref;

    protected RowsData(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageHref = in.readString();
    }

    public static final Creator<RowsData> CREATOR = new Creator<RowsData>() {
        @Override
        public RowsData createFromParcel(Parcel in) {
            return new RowsData(in);
        }

        @Override
        public RowsData[] newArray(int size) {
            return new RowsData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeString(this.imageHref);
    }
}
