package com.example.patrice.bakingapp.model;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/*
Recipe model for use in baking app
Has array lists for ingredients and steps
*/
public class Recipe implements Parcelable {
    private String name;
    private int id;
    private int servings;
    private String imageUrl;

    private List<Ingredient> ingredientList = new ArrayList<>();
    private List<Step> stepList = new ArrayList<>();

    public Recipe(){
        //ParseRecipeJsonUtil builds a new Recipe object
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public List<Ingredient> getIngredients() {
        return ingredientList;
    }

    public List<Step> getSteps() {
        return stepList;
    }
    public void addIngredient(Ingredient ingredient){
        this.ingredientList.add(ingredient);
    }
    public void addStep(Step step){
        stepList.add(step);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(stepList);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(imageUrl);
    }

    private Recipe(Parcel in) {
        in.readTypedList(ingredientList, Ingredient.CREATOR);
        in.readTypedList(stepList, Step.CREATOR);
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        imageUrl = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {

            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
