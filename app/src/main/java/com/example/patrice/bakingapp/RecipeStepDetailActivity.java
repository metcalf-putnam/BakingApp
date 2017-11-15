package com.example.patrice.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;
import com.example.patrice.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a single RecipeStep detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeStepListActivity}.
 */
public class RecipeStepDetailActivity extends AppCompatActivity
    implements IngredientListFragment.IngredientProvider{
    private Step mStep;
    private Recipe mRecipe;
    private boolean mIngredientView;
    private int mStepNumber;
    private int mTotalSteps;
    private List<Step> mStepList;
    @BindView(R.id.button_next) Button button_next;
    @BindView(R.id.button_previous) Button button_previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_detail);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable("recipe");
            mStepNumber = savedInstanceState.getInt("step_num");
            mIngredientView = savedInstanceState.getBoolean("ingredient_view");
            mStepList = mRecipe.getSteps();
            mTotalSteps = mStepList.size();
            mStep = mStepList.get(mStepNumber);

            if(mIngredientView){
                InitializeIngredients();
            }else{
                InitializeStep();
            }
        }else {
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity != null) {
                if (intentThatStartedThisActivity.hasExtra("recipe")) {
                    mRecipe = intentThatStartedThisActivity.getParcelableExtra("recipe");
                    mStepList = mRecipe.getSteps();
                    mTotalSteps = mStepList.size();
                }
                if (intentThatStartedThisActivity.hasExtra("step")) {
                    mStep = intentThatStartedThisActivity.getParcelableExtra("step");
                    mStepNumber = mStep.getId();
                    mIngredientView = false;
                } else if (intentThatStartedThisActivity.getBooleanExtra("ingredient", true)) {
                    button_previous.setText("Back");
                    mIngredientView = true;
                }
            }
        }
        CheckButtonVisibility();

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if(savedInstanceState == null && mIngredientView){
            InitializeIngredients();
        }
        else if (savedInstanceState == null && !mIngredientView) {
            InitializeStep();
        }
    }

    private void UpdateStep(Step step){
        RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();
        detailFragment.SetStepDetails(step);
        FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.recipestep_detail_container, detailFragment)
                    .commit();
    }

    private void InitializeIngredients(){
        IngredientListFragment ingredientFragment = new IngredientListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipestep_detail_container, ingredientFragment)
                .commit();
    }

    private void InitializeStep(){
        RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();
        detailFragment.SetStepDetails(mStep);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipestep_detail_container, detailFragment)
                .commit();
    }


    private void CheckButtonVisibility(){
        if(!mIngredientView){
            if(mStepNumber == (mTotalSteps-1)){
                button_next.setVisibility(View.GONE);
                button_previous.setVisibility(View.VISIBLE);
            }else if(mStepNumber == 0){
                button_previous.setVisibility(View.GONE);
                button_next.setVisibility(View.VISIBLE);
            }else{
                button_next.setVisibility(View.VISIBLE);
                button_previous.setVisibility(View.VISIBLE);
            }
        }else{
            button_next.setVisibility(View.GONE);
            button_previous.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.button_next)
    public void clickNext(View view){
        mStepNumber++;
        mStep = mStepList.get(mStepNumber);
        UpdateStep(mStep);
        CheckButtonVisibility();
    }

    @OnClick(R.id.button_previous)
    public void clickPrevious(View view){
        if(!mIngredientView){
            mStepNumber--;
            mStep = mStepList.get(mStepNumber);
            UpdateStep(mStep);
            CheckButtonVisibility();
        }
        else{
            Context context = this;
            Class destinationActivity = RecipeStepListActivity.class;
            Intent startDetailActivity = new Intent(context, destinationActivity);
            startDetailActivity.putExtra("recipe", mRecipe);
            startActivity(startDetailActivity);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("recipe", mRecipe);
        outState.putInt("step_num", mStepNumber);
        outState.putBoolean("ingredient_view", mIngredientView);
    }

    @Override
    public Recipe getRecipe() {
        return mRecipe;
    }

/**
 * Code below adapted from:
 * https://developer.android.com/training/implementing-navigation/ancestral.html
 */

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public Intent getParentActivityIntent() {
        Intent intent = super.getParentActivityIntent();
        intent.putExtra("recipe", mRecipe);
        return intent;
    }
}
