package com.example.arqtarea3_complete.asyntask.extra5;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by William_ST on 20/02/19.
 */

public class SavedData implements Parcelable {

    private List<String> savedResultList;
    private String primecheckbuttonText;

    public SavedData() {
    }

    public SavedData(List<String> savedResultList, String primecheckbuttonText) {
        this.savedResultList = savedResultList;
        this.primecheckbuttonText = primecheckbuttonText;
    }

    protected SavedData(Parcel in) {
        savedResultList = in.createStringArrayList();
        primecheckbuttonText = in.readString();
    }

    public static final Creator<SavedData> CREATOR = new Creator<SavedData>() {
        @Override
        public SavedData createFromParcel(Parcel in) {
            return new SavedData(in);
        }

        @Override
        public SavedData[] newArray(int size) {
            return new SavedData[size];
        }
    };

    public List<String> getSavedResultList() {
        return savedResultList;
    }

    public void setSavedResultList(List<String> savedResultList) {
        this.savedResultList = savedResultList;
    }

    public String getPrimecheckbuttonText() {
        return primecheckbuttonText;
    }

    public void setPrimecheckbuttonText(String primecheckbuttonText) {
        this.primecheckbuttonText = primecheckbuttonText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(savedResultList);
        parcel.writeString(primecheckbuttonText);
    }
}
