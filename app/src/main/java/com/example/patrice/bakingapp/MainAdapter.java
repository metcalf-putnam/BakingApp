package com.example.patrice.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patrice.bakingapp.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Tegan on 9/10/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> mRecipeList;

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView recipeItemView;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeItemView = (TextView) itemView.findViewById(R.id.tv_recipe_card_title);
        }
        void bind(Recipe recipe){
            recipeItemView.setText(recipe.getName());
        }
    }

    public MainAdapter(){
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int recipeLayoutId = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(recipeLayoutId, parent, shouldAttachToParentImmediately);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }


    @Override
    public int getItemCount() {
        if(mRecipeList != null){
            return mRecipeList.size();
        }
        return 0;
    }

    public void setRecipes(ArrayList<Recipe> recipes){
        clearRecipies();
        mRecipeList = recipes;
        notifyItemRangeInserted(0, recipes.size());

    }
    public void clearRecipies(){
        if(mRecipeList != null){
            int currentSize =  mRecipeList.size();
            mRecipeList.clear();
            notifyItemRangeRemoved(0, currentSize);
        }
    }
}
