package edu.uiuc.cs427app;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Utils {
    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_TEAL = 1;
    public final static int THEME_ORANGE = 2;

    /**
     *
     * @param activity
     * @param theme
     * @param userName
     */
    public static void changeToTheme(Activity activity, int theme, String userName) {
        sTheme = theme;
        activity.finish();
        Intent newThemeIntent = new Intent(activity, activity.getClass());
        newThemeIntent.putExtra("username", userName);
        activity.startActivity(newThemeIntent);
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    /**
     *
     * @param activity
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_MyFirstApp);
                break;
            case THEME_TEAL:
                activity.setTheme(R.style.Theme_Teal);
                break;
            case THEME_ORANGE:
                activity.setTheme(R.style.Theme_Orange);
                break;
        }
    }

    /**
     * Write to file saves the users list of cities for future viewing
     *
     * @param TAG is used to differentiate logs
     * @param fileDirect describes where to save the file to
     * @param fileName describes what the file name should be
     * @param cityList is the data that should be saved to the file
     */
    public static void writeToFile(
            final String TAG,
            final File fileDirect,
            final String fileName,
            final ArrayList<City> cityList
    ) {
        try {
            Log.i(TAG, "Writing to file");
            File file = new File(fileDirect, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cityList);
            oos.close();
            Log.i(TAG, "Wrote to file");
        } catch (Exception e2) {
            Log.i(TAG, "Could not write to file");
            Log.e(TAG, e2.toString());
        }
    }
}