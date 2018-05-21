package com.android.metg2.androidcontroller.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.metg2.androidcontroller.R;
import com.android.metg2.androidcontroller.fragments.MainMenuFragment;

/**
 * Main Menu Activity. It is the menu that shows the four available options to do next.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class MainMenuActivity extends AppCompatActivity {

    private String MAIN_MENU_FRAGMENT = "MAIN_MENU_FRAGMENT";

    /**
     * OnCreate Method from the activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu); //Get the corresponding layout

        initFragment();
    }

    /**
     * This method initializes the activity's fragment.
     */
    private void initFragment() {

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag(MAIN_MENU_FRAGMENT);

        if (fragment == null){

            fragment = MainMenuFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_main_menu_container,fragment,MAIN_MENU_FRAGMENT);
        transaction.commit();
    }
}
