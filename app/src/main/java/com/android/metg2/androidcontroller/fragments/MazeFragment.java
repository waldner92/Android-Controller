package com.android.metg2.androidcontroller.fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.AccelerometerActivity;
import com.android.metg2.androidcontroller.activities.LogsActivity;
import com.android.metg2.androidcontroller.activities.MazeActivity;
import com.android.metg2.androidcontroller.activities.RemoteControlActivity;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.MazeViewModel;

public class MazeFragment extends Fragment {

    private Button exploreButton;
    private Button playButton;
    private MazeViewModel viewModel;

    public static MazeFragment newInstance(){
        return new MazeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_maze,container,false);

        exploreButton = v.findViewById(R.id.explore_button);
        playButton = v.findViewById(R.id.play_button);
        viewModel = new MazeViewModel();

        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugUtils.debug("MAZE", "Pressed explore button");
                //Toast.makeText(v.getContext(), "Explore Button", Toast.LENGTH_LONG).show();
                viewModel.onExploreButton(v.getContext());
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugUtils.debug("MAZE", "Pressed play button");
                //Toast.makeText(v.getContext(), "Play Button", Toast.LENGTH_LONG).show();
                viewModel.onPlayButton(v.getContext());
            }
        });

        return v;
    }

    @Override
    public void onStop(){

        super.onStop();

    }



    @Override
    public void onResume() {
        super.onResume();

    }
}
