package com.example.patrice.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patrice.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by Tegan on 11/12/2017.
 */
public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>{
    private List<Ingredient> mIngredientList;

    class IngredientViewHolder extends RecyclerView.ViewHolder{
        TextView tv_quantity;
        TextView tv_name;
        TextView tv_unit;

        public IngredientViewHolder(View view){
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_ingredient_name);
            tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            tv_unit = (TextView) view.findViewById(R.id.tv_unit);
        }
        void bind(Ingredient ingredient){
            tv_name.setText(ingredient.getDescription());
            tv_unit.setText(ingredient.getMeasure());
            tv_quantity.setText(ingredient.getQuantity().toString());
        }
    }
    public IngredientListAdapter(){}

    @Override
    public IngredientListAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int recipeLayoutId = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(recipeLayoutId, parent, shouldAttachToParentImmediately);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientListAdapter.IngredientViewHolder holder, int position) {
        holder.bind(mIngredientList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mIngredientList != null){
            return mIngredientList.size();
        }
        return 0;
    }

    public void setIngredientList(List<Ingredient> ingredients){
        clearRecipes();
        mIngredientList = ingredients;
        notifyItemRangeInserted(0, ingredients.size());

    }
    public void clearRecipes(){
        if(mIngredientList != null){
            int currentSize =  mIngredientList.size();
            mIngredientList.clear();
            notifyItemRangeRemoved(0, currentSize);
        }
    }
}

