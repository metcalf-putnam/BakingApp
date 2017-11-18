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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        // Construct the RemoteViews object
        RemoteViews views = getRemoteView(context, recipe);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager,
                                     Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    private static RemoteViews getRemoteView(Context context, Recipe recipe){
        Intent intent = new Intent(context, RecipeStepListActivity.class);
        intent.putExtra("recipe", recipe);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_helper_widget);

        views.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);
        List<Ingredient> ingredients = recipe.getIngredients();

        int ingredientsSize = ingredients.size();
        String ingredientText;
        views.setTextViewText(R.id.tv_widget_recipe_name, recipe.getName());

        if(ingredientsSize > 0){
            ingredientText = makePrettyString(ingredients.get(0));
            views.setTextViewText(R.id.tv_widget_ingredient1, ingredientText);
        }
        if(ingredientsSize > 1){
            ingredientText = makePrettyString(ingredients.get(1));
            views.setTextViewText(R.id.tv_widget_ingredient2, ingredientText);
        }
        if(ingredientsSize > 2){
            ingredientText = makePrettyString(ingredients.get(2));
            views.setTextViewText(R.id.tv_widget_ingredient3, ingredientText);
        }
        if(ingredientsSize > 3){
            ingredientText = makePrettyString(ingredients.get(3));
            views.setTextViewText(R.id.tv_widget_ingredient4, ingredientText);
        }
        if(ingredientsSize > 4){
            ingredientText = makePrettyString(ingredients.get(4));
            views.setTextViewText(R.id.tv_widget_ingredient5, ingredientText);
        }
        if(ingredientsSize > 5){
            ingredientText = makePrettyString(ingredients.get(5));
            views.setTextViewText(R.id.tv_widget_ingredient6, ingredientText);
        }
        if(ingredientsSize > 6){
            ingredientText = makePrettyString(ingredients.get(6));
            views.setTextViewText(R.id.tv_widget_ingredient7, ingredientText);
        }
        if(ingredientsSize > 7){
            ingredientText = makePrettyString(ingredients.get(7));
            views.setTextViewText(R.id.tv_widget_ingredient8, ingredientText);
        }
        if(ingredientsSize > 8){
            ingredientText = makePrettyString(ingredients.get(8));
            views.setTextViewText(R.id.tv_widget_ingredient9, ingredientText);
        }
        if(ingredientsSize > 9){
            ingredientText = makePrettyString(ingredients.get(9));
            views.setTextViewText(R.id.tv_widget_ingredient10, ingredientText);
        }
        if(ingredientsSize > 10){
            ingredientText = makePrettyString(ingredients.get(10));
            views.setTextViewText(R.id.tv_widget_ingredient11, ingredientText);
        }
        if(ingredientsSize > 11){
            ingredientText = makePrettyString(ingredients.get(11));
            views.setTextViewText(R.id.tv_widget_ingredient12, ingredientText);
        }
        if(ingredientsSize > 12){
            ingredientText = makePrettyString(ingredients.get(12));
            views.setTextViewText(R.id.tv_widget_ingredient13, ingredientText);
        }
        return views;
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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent != null && intent.hasExtra("recipe") && intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)){
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] ids = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            Recipe recipe = intent.getParcelableExtra("recipe");
            updateWidgets(context, manager, recipe, ids);
        }


    }
}

