package com.example.patrice.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.patrice.bakingapp.model.Recipe;
import com.example.patrice.bakingapp.model.Step;

import java.util.List;

/**
 * Created by Patrice on 11/10/2017.
 */


public class StepListFragment extends Fragment
        implements StepListAdapter.StepClickListener {

    private OnStepClickListener mCallback;
    private OnIngredientsClickListener mIngredientClickCallback;
    private Recipe mRecipe;
    private Parcelable mListState;
    private LinearLayoutManager mLayoutManager;
    private List<Step> steps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable("recipe");
            steps = mRecipe.getSteps();
            mListState = savedInstanceState.getParcelable("state");
        }

        final View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);
        RecyclerView rv_step_list = rootView.findViewById(R.id.rv_recipestep_list);

        if (rv_step_list.getLayoutManager() == null) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            rv_step_list.setLayoutManager(mLayoutManager);
        }
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
        StepListAdapter adapter = new StepListAdapter(this);
        rv_step_list.setAdapter(adapter);
        rv_step_list.setHasFixedSize(true);
        adapter.setSteps(steps);
        ImageView ingredients = rootView.findViewById(R.id.iv_spice_photo);
        ingredients.setOnClickListener(new ImageClickListener());


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
            mIngredientClickCallback = (OnIngredientsClickListener) context;
            mRecipe = ((StepProvider) context).getRecipe();
            steps = mRecipe.getSteps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStepClick(Step step) {
        mCallback.OnStepSelected(step);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("recipe", mRecipe);
        //https://stackoverflow.com/questions/28236390/recyclerview-store-restore-state-between-activities/28262885#28262885
        outState.putParcelable("state", mListState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mListState = mLayoutManager.onSaveInstanceState();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            mLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    public interface StepProvider {
        Recipe getRecipe();
    }

    public interface OnStepClickListener {
        void OnStepSelected(Step step);
    }

    public interface OnIngredientsClickListener {
        void OnIngredientsSelected(View view);
    }

    private class ImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mIngredientClickCallback.OnIngredientsSelected(v);
        }
    }
}
