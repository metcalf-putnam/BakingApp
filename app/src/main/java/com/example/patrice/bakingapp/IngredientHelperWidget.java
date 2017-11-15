package com.example.patrice.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientHelperWidget extends AppWidgetProvider {
    private static Recipe mRecipe;
    private static List<Ingredient> mIngredients;
    private static RemoteViews mViews;

    public static void setRecipe(Recipe recipe){
        mRecipe = recipe;
        mIngredients = recipe.getIngredients();
       // updateText(mViews);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        mViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_helper_widget);

        if(mRecipe != null){
            updateText(mViews);
        }

        Intent intent = new Intent(context, RecipeStepListActivity.class);
        intent.putExtra("recipe", mRecipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        mViews.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, mViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateText(RemoteViews views){
        int ingredientsSize = mIngredients.size();
        String ingredientText;
        if(ingredientsSize > 0){
            ingredientText = makePrettyString(mIngredients.get(0));
            views.setTextViewText(R.id.tv_widget_ingredient1, ingredientText);
        }
        if(ingredientsSize > 1){
            ingredientText = makePrettyString(mIngredients.get(1));
            views.setTextViewText(R.id.tv_widget_ingredient2, ingredientText);
        }
        if(ingredientsSize > 2){
            ingredientText = makePrettyString(mIngredients.get(2));
            views.setTextViewText(R.id.tv_widget_ingredient3, ingredientText);
        }
    }

    private static String makePrettyString(Ingredient ingredient){
        String output = ingredient.getQuantity().toString() + " "
                + ingredient.getMeasure() + " "
                + ingredient.getDescription();
        return output;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

