package com.example.patrice.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Patrice on 9/4/2017.
 */

public class Ingredient implements Parcelable {
    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    private double quantity;
    private String measure;
    private String description;

    public Ingredient() {
        //ParseRecipeJsonUtil creates new ingredients from JSON
    }

    private Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        description = in.readString();
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantityIn) {
        this.quantity = quantityIn;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(description);
    }

}
