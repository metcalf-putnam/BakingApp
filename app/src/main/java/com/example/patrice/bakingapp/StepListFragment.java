package com.example.patrice.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.patrice.bakingapp.model.Step;

import java.util.List;

/**
 * Created by Tegan on 11/10/2017.
 */


public class StepListFragment extends Fragment
    implements StepListAdapter.StepClickListener{

    OnStepClickListener mCallback;

    public interface StepProvider {
        List<Step> getSteps();
    }
    public interface OnStepClickListener {
        void OnStepSelected(Step step);
    }


    private List<Step> steps;

    public StepListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);
        RecyclerView rv_step_list = rootView.findViewById(R.id.rv_recipestep_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_step_list.setLayoutManager(layoutManager);
        StepListAdapter adapter = new StepListAdapter(this);
        rv_step_list.setAdapter(adapter);
        rv_step_list.setHasFixedSize(true);
        adapter.setSteps(steps);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
            steps = ((StepProvider) context).getSteps();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStepClick(Step step) {
        mCallback.OnStepSelected(step);
    }
}
