package com.android.metg2.androidcontroller.utils;

import android.util.Log;

import com.android.metg2.androidcontroller.BuildConfig;

/**
 * Debug Utilities class. It helps to control when are logs displayed.
 *
 * @author  Adria Acero, Adria Mallorqui, Jordi Miro
 * @version 1.0
 */
public class DebugUtils {

    /**
     * Flag to control if we want to show the debug prints or not.
     */
    private static final boolean DEBUG_ENABLE = true;

    /**
     * This method prints the message only if the app is build as debug (not release) and the private
     * flag is activated.
     *
     * @param TAG The TAG of the log
     * @param message The message to be printed
     */
    public static void debug(String TAG, String message){

        if(BuildConfig.DEBUG && DEBUG_ENABLE){

            Log.d(TAG, message);
        }
    }
}
