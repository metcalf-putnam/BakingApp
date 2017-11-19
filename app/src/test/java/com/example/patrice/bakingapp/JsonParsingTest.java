package com.example.patrice.bakingapp;

import android.os.Parcel;

import com.example.patrice.bakingapp.Utils.ParseRecipeJsonUtil;
import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;
import com.example.patrice.bakingapp.model.Step;

import junit.framework.Assert;

import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Patrice on 9/4/2017.
 */
public class JsonParsingTest {

    @Test
    public void fileObjectShouldNotBeNull() throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("recipes.json");
        Scanner s = new Scanner(in).useDelimiter("\\A");
        String json = s.hasNext() ? s.next() : "";
        Assert.assertNotNull(json);
        ArrayList<Recipe> recipes;
        recipes = ParseRecipeJsonUtil.parseRecipes(json);
        Assert.assertEquals(4, recipes.size());

        Recipe tRecipe = recipes.get(3);
        Assert.assertEquals(4, tRecipe.getId());
        Assert.assertEquals("", tRecipe.getImageUrl());
        Assert.assertEquals("Cheesecake", tRecipe.getName());
        Assert.assertEquals(8, tRecipe.getServings());

        List<Ingredient> ingredients;
        ingredients = tRecipe.getIngredients();
        Assert.assertEquals(9, ingredients.size());


        Ingredient ingredient = ingredients.get(6);
        Parcel tParcelIng = Parcel.obtain();
        ingredient.writeToParcel(tParcelIng, ingredient.describeContents());
        tParcelIng.setDataPosition(0);
        Ingredient tIngredient = Ingredient.CREATOR.createFromParcel(tParcelIng);

        Assert.assertEquals("large whole eggs", tIngredient.getDescription());
        Assert.assertEquals("UNIT", tIngredient.getMeasure());
        Assert.assertEquals(3.0, tIngredient.getQuantity());

        List<Step> steps = tRecipe.getSteps();
        Assert.assertEquals(13, steps.size());
        Step step = steps.get(8);

        Parcel tParcel = Parcel.obtain();
        step.writeToParcel(tParcel, 0);
        tParcel.setDataPosition(0);
        Step tStep = Step.CREATOR.createFromParcel(tParcel);

        Assert.assertEquals(8, tStep.getId());
        String step_8_description = "8. Pour the batter into the cooled cookie crust. Bang the pan on a counter or sturdy table a few times to release air bubbles from the batter.";
        Assert.assertEquals(step_8_description, tStep.getDescription());
        Assert.assertEquals("Pour batter in pan.", tStep.getShortDescription());
        String step_8_videoUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb88_6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake/6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake.mp4";
        Assert.assertEquals(step_8_videoUrl, tStep.getVideoUrl());
        Assert.assertEquals("", tStep.getThumbnailUrl());


    }

}
