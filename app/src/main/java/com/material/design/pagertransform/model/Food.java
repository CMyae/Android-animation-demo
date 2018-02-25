package com.material.design.pagertransform.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chan on 9/14/17.
 */

public class Food implements Parcelable{

    private int id;
    private String engTitle;
    private String mmTitle;
    private String imgPath;
    private String type;
    private int rgb;

    public Food() {
    }

    public Food(String engTitle, String mmTitle) {
        this.engTitle = engTitle;
        this.mmTitle = mmTitle;
    }

    protected Food(Parcel in) {
        id = in.readInt();
        engTitle = in.readString();
        mmTitle = in.readString();
        imgPath = in.readString();
        type = in.readString();
        rgb = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public String getMmTitle() {
        return mmTitle;
    }

    public void setMmTitle(String mmTitle) {
        this.mmTitle = mmTitle;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(engTitle);
        dest.writeString(mmTitle);
        dest.writeString(imgPath);
        dest.writeString(type);
        dest.writeInt(rgb);
    }

    public int getRgb() {
        return rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }
}
