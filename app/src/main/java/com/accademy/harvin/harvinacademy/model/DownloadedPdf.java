package com.accademy.harvin.harvinacademy.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ishank on 20/7/17.
 */

public class DownloadedPdf implements Parcelable {
    private int progress;
    private int currentFileSize;
    private int totalFileSize;
    public DownloadedPdf(){

    }

    private DownloadedPdf(Parcel in) {
        progress = in.readInt();
        currentFileSize = in.readInt();
        totalFileSize = in.readInt();
    }

    public static final Creator<DownloadedPdf> CREATOR = new Creator<DownloadedPdf>() {
        @Override
        public DownloadedPdf createFromParcel(Parcel in) {
            return new DownloadedPdf(in);
        }

        @Override
        public DownloadedPdf[] newArray(int size) {
            return new DownloadedPdf[size];
        }
    };

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
        dest.writeInt(progress);
    }
}
