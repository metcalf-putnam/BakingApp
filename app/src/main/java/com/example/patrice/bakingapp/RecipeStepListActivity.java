package com.example.patrice.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;
import com.example.patrice.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
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
    implements StepListFragment.StepProvider, StepListFragment.OnStepClickListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<Step> mStepList = null;
    private List<Ingredient> mIngredients = null;
    private StepListAdapter mAdapter;

    @Override
    public List<Step> getSteps() {
        return mStepList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity != null){
            if(intentThatStartedThisActivity.hasExtra("recipe")){
                Recipe recipe = intentThatStartedThisActivity.getParcelableExtra("recipe");
                mStepList = recipe.getSteps();
            }

        }
        setContentView(R.layout.activity_recipestep_list);
        if (findViewById(R.id.recipestep_detail_container) != null) {
            //recipestep_detail_container is only present in tablet devices
            mTwoPane = true;
            if(savedInstanceState == null){
                UpdateStep(mStepList.get(0), true);
            }
        }
        else{
            mTwoPane = false;
        }
    }

    private void UpdateStep(Step step, boolean firstTime){
        RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();
        detailFragment.SetStepDetails(step);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(firstTime){
            fragmentManager.beginTransaction()
                    .add(R.id.recipestep_detail_container, detailFragment)
                    .commit();
        }
        else{
            fragmentManager.beginTransaction()
                    .replace(R.id.recipestep_detail_container, detailFragment)
                    .commit();
        }

    }

    @Override
    public void OnStepSelected(Step step) {
        UpdateStep(step, false);
    }
}
