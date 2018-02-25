package com.material.design.pagertransform.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chan on 9/15/17.
 */

public class Position implements Parcelable{

    private String name;
    private int width;
    private int height;
    private int left;
    private int top;
    private int leftDelta;
    private int topDelta;
    private float scaleX;
    private float scaleY;

    public Position(String name, int width, int height, int left, int top) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
    }

    public Position(int width, int height, int left, int top) {
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
    }

    protected Position(Parcel in) {
        name = in.readString();
        width = in.readInt();
        height = in.readInt();
        left = in.readInt();
        top = in.readInt();
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeftDelta() {
        return leftDelta;
    }

    public void setLeftDelta(int leftDelta) {
        this.leftDelta = leftDelta;
    }

    public int getTopDelta() {
        return topDelta;
    }

    public void setTopDelta(int topDelta) {
        this.topDelta = topDelta;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(left);
        dest.writeInt(top);
    }
}
