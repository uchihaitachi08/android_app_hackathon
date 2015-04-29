package com.example.vicky.testapp;

import android.content.Context;

/**
 * Created by Vicky on 17-01-2015.
 */
public class StringArray {
    public static String[] theArray;
    public static String[] getArray(Context context) {
        if (theArray == null) {
            theArray = context.getResources().getStringArray(R.array.station_names);
        }
        return theArray;
    }
}
