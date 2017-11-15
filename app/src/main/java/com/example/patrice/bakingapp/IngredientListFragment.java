package com.example.patrice.bakingapp;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patrice.bakingapp.model.Ingredient;
import com.example.patrice.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by Tegan on 11/12/2017.
 */

public class IngredientListFragment extends Fragment {
    public IngredientListFragment(){}

    private List<Ingredient> mIngredients;
    private Recipe mRecipe;

    public interface IngredientProvider {
        Recipe getRecipe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable("recipe");
            mIngredients = mRecipe.getIngredients();
        }
        final View rootView = inflater.inflate(R.layout.frargment_ingredient_list, container, false);
        RecyclerView rv_step_list = rootView.findViewById(R.id.rv_ingredient_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_step_list.setLayoutManager(layoutManager);
        IngredientListAdapter adapter = new IngredientListAdapter();
        rv_step_list.setAdapter(adapter);
        rv_step_list.setHasFixedSize(true);
        adapter.setIngredientList(mIngredients);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mRecipe = ((IngredientProvider) context).getRecipe();
            mIngredients = mRecipe.getIngredients();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("recipe", mRecipe);
    }
}
