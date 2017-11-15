package com.example.patrice.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrice.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;

/**
 * A fragment representing a single RecipeStep detail screen.
 * This fragment is either contained in a {@link RecipeStepListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeStepDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private Step mStep;
    private String mRecipeName;
    private SimpleExoPlayer mExoPlayer;
    private ImageView mThumbnail;
    private SimpleExoPlayerView mPlayerView;

    /**
     * The model content this fragment is presenting.
     */

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mStep = savedInstanceState.getParcelable("step");
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        mPlayerView = rootView.findViewById(R.id.playerView);
        mThumbnail = rootView.findViewById(R.id.iv_step_thumnail);


        TextView tv_description = rootView.findViewById(R.id.tv_step_description);
        tv_description.setText(mStep.getDescription());
        if(mExoPlayer != null){
            releasePlayer();
        }
        Uri media = GrabVideoUri(mStep);
        if(media != null){
            initializePlayer(media);
            mPlayerView.setVisibility(View.VISIBLE);
        }else{
            mPlayerView.setVisibility(View.GONE);
            String thumbnailUrl = mStep.getThumbnailUrl();
            if(!thumbnailUrl.isEmpty()){
                int index = thumbnailUrl.lastIndexOf(".") + 1;
                String extension = thumbnailUrl.substring(index);
                if(extension.contentEquals("jpg") || extension.contentEquals("png")){
                    Picasso.with(getContext())
                            .load(thumbnailUrl)
                            .placeholder(R.drawable.cupcake_640)
                            .error(R.drawable.ic_error)
                            .into(mThumbnail);
                }
            }
        }

        return rootView;
    }

    private Uri GrabVideoUri(Step step){
        String videoUrl = step.getVideoUrl();
        if(videoUrl.isEmpty()){
            return null;
        }
        Uri videoUri = Uri.parse(videoUrl);
        return videoUri;
    }

    public void SetStepDetails(Step step){
        mStep = step;
    }

    //Borrowing example ExoPlayer setup from the Classical Music Quiz
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            Context activity = this.getActivity();
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(activity, "BakeItToMe");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    activity, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void releasePlayer() {
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("step", mStep);
    }
}
