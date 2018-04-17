package com.android.metg2.androidcontroller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.utils.Constants;
import com.android.metg2.androidcontroller.utils.DebugUtils;
import com.android.metg2.androidcontroller.viewmodels.MazeViewModel;

import java.util.Random;

/**
 * Maze Fragment. It is the actual view of the Maze Activity.
 *
 * @author Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MazeFragment extends Fragment {

    /**
     * Button that asks the Arduino to explore the maze solution
     */
    private Button exploreButton;

    /**
     * Button that asks the Arduino to play the maze solution
     */
    private Button playButton;

    /**
     * The viewModel of the view
     */
    private MazeViewModel viewModel;

    /**
     * The 5x5 cell maze layout
     */
    private static LinearLayout layout;

    /**
     * Method that returns a new constructed Maze Fragment.
     * @return MazeFragment The Maze Fragment
     */
    public static MazeFragment newInstance(){
        return new MazeFragment();
    }

    /**
     * onCreate method from the fragment.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    /**
     * onActivityCreated method from the fragment.
     *
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onCreateView method from the fragment. It sets the fragment layout and binds the views. It also
     * defines the button listeners and asks to paint the maze cells randomly.
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstance Bundle
     * @return View the fragment view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View v = inflater.inflate(R.layout.fragment_maze,container,false); //Get the corresponding layout

        exploreButton = v.findViewById(R.id.explore_button);
        playButton = v.findViewById(R.id.play_button);
        layout = v.findViewById(R.id.maze_layout);
        viewModel = new MazeViewModel(); //Initialize the viewModel

        //When the explore button is clicked, ask the viewmodel to show it with a toast
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DebugUtils.debug("MAZE", "Pressed explore button");
                viewModel.onExploreButton(v.getContext());
            }
        });

        //When the play button is clicked, ask the viewmodel to show it with a toast
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DebugUtils.debug("MAZE", "Pressed play button");
                viewModel.onPlayButton(v.getContext());
            }
        });

        paintRandomCells(); //paint the maze cells randomly

        return v;
    }

    /**
     * This method is in charge of painting the maze cells randomly. It is just a prove of convept to
     * show how this cells can be painted.
     */
    private void paintRandomCells() {

        Random r = new Random();

        for (int i = 0; i <= 4; i++) { //Rows loop
            for (int j = 0; j <= 4; j++) { //Columns loop

                DebugUtils.debug("MAZE COLOR", "i: " + i + " , j: " + j);
                getCellTop(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getCellBottom(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getCellRight(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getCellLeft(i,j).setBackgroundColor(getColor(r.nextInt(2)+7));
                getInnerCell(i,j).setBackgroundColor(getColor(r.nextInt(7)));

            }
        }

    }

    /**
     * This method returns a Color depending on the integer (identifier) received.
     * @param color The identifier of the wanted color
     * @return Color The Color to be used
     */
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
     * This method returns the central square within a cell, without the walls.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View The central square view
     */
    public static View getInnerCell(int x, int y) {

        DebugUtils.debug("MAZE COLOR", "getting inner cell");
        return getCell(x, y).findViewById(R.id.cell_rect);
    }

    /**
     * Returns a cell, with the 4 walls and the inner cell rectangle.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View The cell view
     */
    public static View getCell(int x, int y) {

        return getCellFromColumn(getColumn(y), x);
    }

    /**
     * Returns the top wall of the cell specified by x and y.
     *
     * @param x int -> referring to the row
     * @param y int -> referring to the columns
     * @return View The top wall view
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
     * @return View The bottom wall view
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
     * @return View The left wall view
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
     * @return View The right wall view
     */
    public static View getCellRight(int x, int y) {

        DebugUtils.debug("MAZE COLOR", "getting right cell");
        return getCell(x,y).findViewById(R.id.cell_right);
    }

    /**
     * This method returns a 1x5 column of the maze.
     *
     * @param num int -> referring to the column
     * @return View The column view
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
     * This method gets a cell within a column, specifying the desired row.
     *
     * @param column View -> The column in which cell search occurs
     * @param num int -> The desired row
     * @return View The cell view
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

    /**
     * onStop method from the fragment.
     */
    @Override
    public void onStop(){

        super.onStop();
    }

    /**
     * onResume method from the fragment.
     */
    @Override
    public void onResume() {

        super.onResume();
    }
}
