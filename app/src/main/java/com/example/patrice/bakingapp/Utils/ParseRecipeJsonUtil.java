package com.example.patrice.bakingapp.Utils;

import android.content.res.Resources;
import android.util.Log;

import com.example.patrice.bakingapp.R;
import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;
import com.example.patrice.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.res.Resources.getSystem;

/**
 * Created by Patrice on 9/4/2017.
 */

public final class ParseRecipeJsonUtil {
    private static final String LOG_TAG = "ParseJson";


    public static ArrayList<Recipe> parseRecipes(String response){
        ArrayList<Recipe> recipesArray = new ArrayList<>();
        try {
            JSONArray recipes = new JSONArray(response);
            for(int i = 0; i < recipes.length(); i++){
                JSONObject recipeObj = recipes.getJSONObject(i);
                recipesArray.add(parseRecipeJsonObject(recipeObj));
            }
        }catch (JSONException e){
            e.printStackTrace();
            String error = Resources.getSystem().getString(R.string.error_getting_initial_JSON);
            Log.d(LOG_TAG, error);
        }
        return recipesArray;
    }

    private static Recipe parseRecipeJsonObject(JSONObject in){
        Recipe out = new Recipe();
        try{
            out.setId(in.getInt("id"));
            out.setName(in.getString("name"));
            out.setImageUrl(in.getString("image"));
            out.setServings(in.getInt("servings"));
            JSONArray ingredientsIn = in.getJSONArray("ingredients");
            out = setIngredients(out, ingredientsIn);
            JSONArray stepsIn = in.getJSONArray("steps");
            out = setSteps(out, stepsIn);

        }catch (JSONException e){
            e.printStackTrace();
            String error = Resources.getSystem().getString(R.string.error_parse_recipe);
            Log.d(LOG_TAG, error);
        }
        return out;
    }

    private static Recipe setIngredients(Recipe recipe, JSONArray ingredientsIn){
        try {
            for (int i = 0; i < ingredientsIn.length(); i++) {
                JSONObject ingredientObj = ingredientsIn.getJSONObject(i);
                Ingredient ingredientOut = parseIngredientJsonObject(ingredientObj);
                recipe.addIngredient(ingredientOut);
            }
        }catch (JSONException e){
            e.printStackTrace();
            String error = Resources.getSystem().getString(R.string.error_adding_ingredients);
            Log.d(LOG_TAG, error);
        }
        return recipe;
    }

    public static Recipe setSteps(Recipe recipe, JSONArray stepsIn){
        try{
            for(int i = 0; i < stepsIn.length(); i++){
                JSONObject stepObj = stepsIn.getJSONObject(i);
                Step stepOut = parseStepJsonObject(stepObj);
                recipe.addStep(stepOut);
            }

        }catch (JSONException e){
            e.printStackTrace();
            String error = Resources.getSystem().getString(R.string.error_adding_steps);
            Log.d(LOG_TAG, error);
        }

        return recipe;
    }

    private static Step parseStepJsonObject(JSONObject in){
        Step out = new Step();
        try{
            out.setDescription(in.getString("description"));
            out.setShortDescription(in.getString("shortDescription"));
            out.setId(in.getInt("id"));
            out.setThumbnailUrl(in.getString("thumbnailURL"));
            out.setVideoUrl(in.getString("videoURL"));
        }catch (JSONException e){
            e.printStackTrace();
            String error = Resources.getSystem().getString(R.string.error_parse_step);
            Log.d(LOG_TAG, error);
        }
        return out;
    }

    private static Ingredient parseIngredientJsonObject(JSONObject in){
        Ingredient out = new Ingredient();
        try {
            out.setDescription(in.getString("ingredient"));
            out.setMeasure(in.getString("measure"));
            out.setQuantity(in.getDouble("quantity"));

        }catch (JSONException e){
            e.printStackTrace();
            String error = Resources.getSystem().getString(R.string.error_parse_ingredient);
            Log.d(LOG_TAG, error);
        }
        return out;
    }
}
