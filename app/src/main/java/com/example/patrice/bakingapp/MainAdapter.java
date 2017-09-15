package com.example.patrice.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patrice.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tegan on 9/10/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> mRecipeList;

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        TextView recipeItemView;
        TextView ingredient1_View;
        TextView ingredient2_View;
        TextView ingredient3_View;
        TextView ingredient4_View;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeItemView = (TextView) itemView.findViewById(R.id.tv_recipe_card_title);
            ingredient1_View = (TextView) itemView.findViewById(R.id.tv_ingredient1);
            ingredient2_View = (TextView) itemView.findViewById(R.id.tv_ingredient2);
            ingredient3_View = (TextView) itemView.findViewById(R.id.tv_ingredient3);
            ingredient4_View = (TextView) itemView.findViewById(R.id.tv_ingredient4);
        }
        void bind(Recipe recipe){
            recipeItemView.setText(recipe.getName());
            ingredient1_View.setText(recipe.getIngredients().get(0).getDescription());
            ingredient2_View.setText(recipe.getIngredients().get(1).getDescription());
            ingredient3_View.setText(recipe.getIngredients().get(2).getDescription());
            ingredient4_View.setText(recipe.getIngredients().get(3).getDescription());
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
