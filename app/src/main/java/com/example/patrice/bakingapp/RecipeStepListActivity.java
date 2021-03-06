package com.example.patrice.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.patrice.bakingapp.model.Recipe;
import com.example.patrice.bakingapp.model.Step;

import java.util.List;

import butterknife.ButterKnife;

/**
 * An activity representing a list of RecipeSteps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends AppCompatActivity
        implements StepListFragment.StepProvider, StepListFragment.OnStepClickListener,
        StepListFragment.OnIngredientsClickListener, IngredientListFragment.IngredientProvider {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<Step> mStepList = null;
    private Recipe mRecipe;
    private Step mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable("recipe");
            mStepList = mRecipe.getSteps();
            mStep = savedInstanceState.getParcelable("step");
        }

        Intent intentThatStartedThisActivity = getIntent();
        if (savedInstanceState == null && intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("recipe")) {
                mRecipe = intentThatStartedThisActivity.getParcelableExtra("recipe");
                mStepList = mRecipe.getSteps();
            }
        }
        setContentView(R.layout.activity_recipestep_list);
        if (findViewById(R.id.recipestep_detail_container) != null && mStepList != null) {
            //recipestep_detail_container is only present in tablet devices
            mTwoPane = true;
            if (mStep == null) {
                UpdateStep(mStepList.get(0), true);
            } else {
                UpdateStep(mStep, false);
            }
        } else {
            mTwoPane = false;
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.recipestep_detail_container);
            if(fragment != null){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

    }

    private void UpdateStep(Step step, boolean firstTime) {
        mStep = step;
        RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();
        detailFragment.SetStepDetails(step);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (firstTime) {
            fragmentManager.beginTransaction()
                    .add(R.id.recipestep_detail_container, detailFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipestep_detail_container, detailFragment)
                    .commit();
        }
    }

    @Override
    public void OnStepSelected(Step step) {
        if (mTwoPane) {
            UpdateStep(step, false);
        } else {
            Context context = this;
            Class destinationActivity = RecipeStepDetailActivity.class;
            Intent stepDetailIntent = new Intent(context, destinationActivity);
            stepDetailIntent.putExtra("step", step);
            stepDetailIntent.putExtra("recipe", mRecipe);
            startActivity(stepDetailIntent);
        }

    }


    @Override
    public void OnIngredientsSelected(View view) {
        if (mTwoPane) {
            IngredientListFragment ingredientFragment = new IngredientListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipestep_detail_container, ingredientFragment)
                    .commit();
        } else {
            Context context = this;
            Class destinationActivity = RecipeStepDetailActivity.class;
            Intent ingredientDetailIntent = new Intent(context, destinationActivity);
            ingredientDetailIntent.putExtra("recipe", mRecipe);
            ingredientDetailIntent.putExtra("ingredient", true);
            startActivity(ingredientDetailIntent);
        }
    }


    @Override
    public Recipe getRecipe() {
        return mRecipe;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("recipe", mRecipe);
        outState.putParcelable("step", mStep);
        super.onSaveInstanceState(outState);
    }
}
