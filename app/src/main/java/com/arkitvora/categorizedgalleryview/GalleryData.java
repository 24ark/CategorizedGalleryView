package com.arkitvora.categorizedgalleryview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GalleryData implements Parcelable {
    private String categoryName;
    private ArrayList<String> imagePaths;

    public GalleryData(String categoryName , ArrayList<String> imagePaths) {
        this.categoryName = categoryName;
        this.imagePaths = imagePaths;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public GalleryData setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public GalleryData setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
        return this;
    }

    protected GalleryData(Parcel in) {
        categoryName = in.readString();
        if (in.readByte() == 0x01) {
            imagePaths = new ArrayList<String>();
            in.readList(imagePaths, String.class.getClassLoader());
        } else {
            imagePaths = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryName);
        if (imagePaths == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imagePaths);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<GalleryData> CREATOR = new Creator<GalleryData>() {
        @Override
        public GalleryData createFromParcel(Parcel in) {
            return new GalleryData(in);
        }

        @Override
        public GalleryData[] newArray(int size) {
            return new GalleryData[size];
        }
    };
}