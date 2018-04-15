package com.android.metg2.androidcontroller.fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.widget.LinearLayout;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.activities.AccelerometerActivity;
import com.android.metg2.androidcontroller.activities.LogsActivity;
import com.android.metg2.androidcontroller.activities.MazeActivity;
import com.android.metg2.androidcontroller.activities.RemoteControlActivity;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.MazeViewModel;

import java.util.Random;

public class MazeFragment extends Fragment {

    private Button exploreButton;
    private Button playButton;
    private MazeViewModel viewModel;
    private static LinearLayout layout;
    private static View actualCell;

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
        layout = v.findViewById(R.id.maze_layout);
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

        paintRandomCells();

        return v;
    }

    private void paintRandomCells() {

        Random r = new Random();

        /*View column = getColumn(0);
        if (column == null){

            DebugUtils.debug("MAZE COLOR", "Column " + 0 + " is null");
        }else {

            View row = getCellFromColumn(column, 4);
            if (row==null) {
                DebugUtils.debug("MAZE COLOR", "Row " + 4 + " is null");
            }
        }*/

        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {

                DebugUtils.debug("MAZE COLOR", "i: " + i + " , j: " + j);
                getCellTop(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getCellBottom(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getCellRight(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getCellLeft(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getInnerCell(i,j).setBackgroundColor(getColor(r.nextInt(7)));

            }
        }

    }

    private int getColor(int color){
        switch (color){

            case Constants.CELL_NONE: return Color.WHITE;
            case Constants.CELL_UNEXPLORED: return Color.DKGRAY;
            case Constants.CELL_EXPLORING: return Color.YELLOW;
            case Constants.CELL_CURRENT: return Color.BLUE;
            case Constants.CELL_EXPLORED: return Color.LTGRAY;
            case Constants.CELL_SOLUTION: return Color.GREEN;
            case Constants.CELL_DISCARDED: return Color.RED;
            case Constants.WALL_TRUE: return Color.BLACK;
            case Constants.WALL_FALSE: return Color.WHITE;
            default: return Color.WHITE;
        }
    }


    /**
     * Returns the central square within a cell, without the walls.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getInnerCell(int x, int y) {
        DebugUtils.debug("MAZE COLOR", "getting inner cell");
        return getCell(x, y).findViewById(R.id.cell_rect);
    }

    /**
     * Returns a cell, with the 4 walls and the inner rectangle.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getCell(int x, int y) {

        return getCellFromColumn(getColumn(y), x);
    }

    /**
     * Returns the top wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getCellTop(int x, int y) {
        DebugUtils.debug("MAZE COLOR", "getting top cell");
        return getCell(x,y).findViewById(R.id.cell_top);
    }

    /**
     * Returns the bottom wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getCellBottom(int x, int y) {
        DebugUtils.debug("MAZE COLOR", "getting bottom cell");
        return getCell(x,y).findViewById(R.id.cell_bottom);
    }

    /**
     * Returns the left wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getCellLeft(int x, int y) {
        DebugUtils.debug("MAZE COLOR", "getting left cell");
        return getCell(x,y).findViewById(R.id.cell_left);
    }

    /**
     * Returns the right wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View
     */
    public static View getCellRight(int x, int y) {
        DebugUtils.debug("MAZE COLOR", "getting right cell");
        return getCell(x,y).findViewById(R.id.cell_right);
    }


    /**
     * This method encapsulates the logic for getting a column 1x5 og the labyrinth.
     *
     * @param num int -> referring to the column.
     * @return View -> The row.
     */
    private static View getColumn(int num) {

        switch (num){
            case 0:
                DebugUtils.debug("MAZE COLOR", "getting column 0");
                return layout.findViewById(R.id.column0);
            case 1:
                DebugUtils.debug("MAZE COLOR", "getting column 1");
                return layout.findViewById(R.id.column1);
            case 2:
                DebugUtils.debug("MAZE COLOR", "getting column 2");
                return layout.findViewById(R.id.column2);
            case 3:
                DebugUtils.debug("MAZE COLOR", "getting column 3");
                return  layout.findViewById(R.id.column3);
            case 4:
                DebugUtils.debug("MAZE COLOR", "getting column 4");
                return layout.findViewById(R.id.column4);
            default:
                return null;
        }
    }

    /**
     * This method encapsulates the logic for getting a cell within a row.
     *
     * @param column View -> The column in which we'll search for the cell.
     * @param num int -> The row we select.
     * @return View -> The cell we return.
     */
    private static View getCellFromColumn(View column, int num) {

        if (column != null) {
            switch (num) {
                case 0:
                    DebugUtils.debug("MAZE COLOR", "getting row 0");
                    return column.findViewById(R.id.row0);
                case 1:
                    DebugUtils.debug("MAZE COLOR", "getting row 1");
                    return column.findViewById(R.id.row1);
                case 2:
                    DebugUtils.debug("MAZE COLOR", "getting row 2");
                    return column.findViewById(R.id.row2);
                case 3:
                    DebugUtils.debug("MAZE COLOR", "getting row 3");
                    return column.findViewById(R.id.row3);
                case 4:
                    DebugUtils.debug("MAZE COLOR", "getting row 4");
                    return column.findViewById(R.id.row4);
                default:
                    return null;
            }
        } else {
            return null;
        }
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
