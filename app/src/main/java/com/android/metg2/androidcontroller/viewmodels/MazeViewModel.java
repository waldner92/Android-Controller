package com.android.metg2.androidcontroller.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.android.metg2.androidcontroller.utils.DebugUtils;

/**
 * The viewModel of the Logs Activity. This class performs the actions when the view listeners are
 * triggered. It will communicate with the robot in the future, but right now it just shows toasts
 * on the screen.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MazeViewModel extends ViewModel{

    /**
     * The vieModel constructor.
     */
    public MazeViewModel(){}

    /**
     * This method is called when the play button is pressed (called from the listener). It shows a
     * Toast on the screen indicating that this button has been clicked.
     *
     * @param context Context The application context
     */
    public void onPlayButton(Context context){

        DebugUtils.debug("MAZE_VM", "PLAY button");
        Toast.makeText(context, "You pressed the Play Button", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is called when the explore button is pressed (called from the listener). It shows a
     * Toast on the screen indicating that this button has been clicked.
     *
     * @param context Context The application context
     */
    public void onExploreButton(Context context){

        DebugUtils.debug("MAZE_VM", "EXPLORE button");
        Toast.makeText(context, "You pressed the Explore Button", Toast.LENGTH_SHORT).show();
    }
}
