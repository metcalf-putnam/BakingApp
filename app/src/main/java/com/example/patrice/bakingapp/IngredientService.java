package com.example.patrice.bakingapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Tegan on 11/12/2017.
 */

public class IngredientService extends IntentService{
    public IngredientService(){
        super("IngredientService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
