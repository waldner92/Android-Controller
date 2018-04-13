package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.android.metg2.androidcontroller.utils.DebugUtils;

/**
 * Created by Adri on 12/4/18.
 */

public class MazeViewModel extends ViewModel{

    public MazeViewModel(){}

    public void onPlayButton(Context context){

        DebugUtils.debug("MAZE_VM", "PLAY button");
        Toast.makeText(context, "You pressed the Play Button", Toast.LENGTH_SHORT).show();
    }

    public void onExploreButton(Context context){

        DebugUtils.debug("MAZE_VM", "EXPLORE button");
        Toast.makeText(context, "You pressed the Explore Button", Toast.LENGTH_SHORT).show();
    }

}
