package com.android.metg2.androidcontroller.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.MazeViewModel;

public class MazeActivity extends AppCompatActivity {

    private Button exploreButton;
    private Button playButton;
    private MazeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

        exploreButton = findViewById(R.id.explore_button);
        playButton = findViewById(R.id.play_button);
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
