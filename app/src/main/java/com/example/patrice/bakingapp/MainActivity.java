package com.example.patrice.bakingapp;

import android.app.DownloadManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.patrice.bakingapp.Utils.ParseRecipeJsonUtil;
import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements MainAdapter.RecipeClickListener{
    private boolean mTablet;
    private ArrayList<Recipe> mRecipeList;
    private MainAdapter mAdapter;
    private final OkHttpClient client = new OkHttpClient();
    private int WIDGETID;
    @BindView(R.id.rv_main_recipe_list) RecyclerView recipeList;
    @BindView(R.id.progressBar) ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        try {
            fetchRecipes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            WIDGETID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        boolean tablet = getResources().getBoolean(R.bool.isTablet);

        if(tablet){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recipeList.setLayoutManager(gridLayoutManager);
        }else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            recipeList.setLayoutManager(gridLayoutManager);
        }
        recipeList.setHasFixedSize(true);
        mAdapter = new MainAdapter(this);
        recipeList.setAdapter(mAdapter);
    }


    /*
    * "fetchRecipe" method asynchronously fetches data uses okhttp library
    * Modified from https://github.com/square/okhttp/wiki/Recipes (async)
    * */
    public void fetchRecipes() throws Exception {
        progressBar.setVisibility(View.VISIBLE);
        Request request = new Request.Builder()
                .url("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                .build();

        if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @RequiresApi(api = VERSION_CODES.KITKAT)
                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        //System.out.println(responseBody.string());
                        mRecipeList = ParseRecipeJsonUtil.parseRecipes(responseBody.string());
                        mAdapter.setRecipes(mRecipeList);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }

            });
        }
    }


    @Override
    public void onRecipeClick(Recipe recipe) {
        //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

        //Updating widget, borrowing from code here:
        // https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
        Intent intent = new Intent(this, IngredientHelperWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
// since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), IngredientHelperWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra("recipe", recipe);
        sendBroadcast(intent);


        Context context = MainActivity.this;
        Class destinationActivity = RecipeStepListActivity.class;
        Intent startDetailActivity = new Intent(context, destinationActivity);
        startDetailActivity.putExtra("recipe", recipe);
        startActivity(startDetailActivity);
    }
}
