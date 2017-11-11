package com.example.patrice.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patrice.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tegan on 11/4/2017.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {
    private List<Step> mSteps;
    final private StepClickListener mOnStepClick;
    public interface StepClickListener{
        void onStepClick(Step step);
    }

    public StepListAdapter(StepClickListener listener){
        mOnStepClick = listener;
    }
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int stepLayoutId = R.layout.recipestep_list_content;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(stepLayoutId, parent, shouldAttachToParentImmediately);
        StepViewHolder viewHolder = new StepViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(mSteps.get(position));
    }

    @Override
    public int getItemCount() {
        if(mSteps != null){
            return mSteps.size();
        }
        return 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        TextView tv_step_number;
        TextView tv_short_description;

        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_step_number = (TextView) itemView.findViewById(R.id.tv_step_number);
            tv_short_description = (TextView) itemView.findViewById(R.id.tv_step_text);
        }
        void bind(Step step){
            int id = step.getId();
            int step_number = id + 1;
            tv_step_number.setText(""+step_number);
            tv_short_description.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Step step = mSteps.get(clickedPosition);
            mOnStepClick.onStepClick(step);
        }
    }
    public void setSteps(List<Step> steps){
        clearSteps();
        mSteps = steps;
        notifyItemRangeInserted(0, steps.size());
        Log.d("steps", "Steps " +  steps.size());
    }
    public void clearSteps(){
        if(mSteps != null){
            int currentSize =  mSteps.size();
            mSteps.clear();
            notifyItemRangeRemoved(0, currentSize);
        }
    }
}
