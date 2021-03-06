package com.example.patrice.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patrice.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Patrice on 9/10/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipeViewHolder> {
    final private RecipeClickListener mOnRecipeClick;
    private ArrayList<Recipe> mRecipeList;
    private Context mContext;

    public MainAdapter(RecipeClickListener clickListener) {
        mOnRecipeClick = clickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int recipeLayoutId = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
        if (mRecipeList != null) {
            return mRecipeList.size();
        }
        return 0;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        clearRecipes();
        mRecipeList = recipes;
        notifyItemRangeInserted(0, recipes.size());

    }

    private void clearRecipes() {
        if (mRecipeList != null) {
            int currentSize = mRecipeList.size();
            mRecipeList.clear();
            notifyItemRangeRemoved(0, currentSize);
        }
    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView recipePhoto;
        TextView recipeItemView;
        TextView ingredient1_View;
        TextView ingredient2_View;
        TextView ingredient3_View;
        TextView ingredient4_View;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipePhoto = itemView.findViewById(R.id.iv_recipe_photo);
            recipeItemView = itemView.findViewById(R.id.tv_recipe_card_title);
            ingredient1_View = itemView.findViewById(R.id.tv_ingredient1);
            ingredient2_View = itemView.findViewById(R.id.tv_ingredient2);
            ingredient3_View = itemView.findViewById(R.id.tv_ingredient3);
            ingredient4_View = itemView.findViewById(R.id.tv_ingredient4);
        }

        void bind(Recipe recipe) {
            String photoUrl = recipe.getImageUrl();
            if (!photoUrl.isEmpty()) {
                Picasso.with(mContext)
                        .load(photoUrl)
                        .placeholder(R.drawable.cupcake_640)
                        .error(R.drawable.ic_error)
                        .into(recipePhoto);
            }

            recipeItemView.setText(recipe.getName());
            ingredient1_View.setText(recipe.getIngredients().get(0).getDescription());
            ingredient2_View.setText(recipe.getIngredients().get(1).getDescription());
            ingredient3_View.setText(recipe.getIngredients().get(2).getDescription());
            ingredient4_View.setText(recipe.getIngredients().get(3).getDescription());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(clickedPosition);
            mOnRecipeClick.onRecipeClick(recipe);
        }
    }
}
