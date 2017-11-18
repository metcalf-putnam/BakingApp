package com.example.patrice.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.patrice.bakingapp.model.Recipe;

/**
 * Created by Tegan on 11/12/2017.
 */

public class IngredientService extends IntentService{

    public static final String ACTION_UPDATE_WIDGETS = "com.example.patrice.bakingapp.action.update_widgets";
    public IngredientService(){
        super("IngredientService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                final Recipe recipe = intent.getParcelableExtra("recipe");
                handleActionUpdateBakingWidget(recipe);
            }
        }
    }

    public static void startActionUpdateBakingWidget(Context context) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

   private void handleActionUpdateBakingWidget(Recipe recipe){
       AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
       int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientHelperWidget.class));
       appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ll_widget);
       IngredientHelperWidget.updateWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
