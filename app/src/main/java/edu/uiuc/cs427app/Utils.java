package edu.uiuc.cs427app;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Utils {
    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_TEAL = 1;
    public final static int THEME_ORANGE = 2;

    public static void saveTheme(final String TAG, final File file, int theme, String username) {
        sTheme = theme;
        final String filename = username + "-theme.txt";
        writeToFile(TAG, file, filename, theme);
    }

    /**
     *
     * @param activity describes the action the user wants to take
     * @param theme is the theme the user chose
     * @param username is the user's username in the app
     */
    public static void changeToTheme(
            final String TAG,
            final File file,
            Activity activity,
            int theme,
            String username
    ) {
        sTheme = theme;
        activity.finish();
        Intent newThemeIntent = new Intent(activity, activity.getClass());
        newThemeIntent.putExtra("username", username);
        activity.startActivity(newThemeIntent);
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        final String filename = username + "-theme.txt";
        writeToFile(TAG, file, filename, theme);
    }

    /**
     * @param TAG is used to differentiate logs
     * @param fileDirect describes where to save the file to
     * @param activity describes the action the user wants to take
     * @param username is the user's username in the app
     */
    public static void onActivityCreateSetTheme(
            final String TAG,
            final File fileDir,
            Activity activity,
            final String username
    ) {
        final String filename = username + "-theme.txt";
        File file = new File(fileDir, filename);
        try {
            Log.i(TAG, "Reading from " + filename);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            sTheme = ois.readInt();
            Log.i(TAG, "Reading theme number: " + sTheme);
            ois.close();
        } catch (Exception e) {
            Log.i(TAG, String.valueOf(e));
        }

        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                Log.i(TAG, "Setting Default Theme");
                activity.setTheme(R.style.Theme_MyFirstApp);
                break;
            case THEME_TEAL:
                Log.i(TAG, "Setting Teal Theme");
                activity.setTheme(R.style.Theme_Teal);
                break;
            case THEME_ORANGE:
                Log.i(TAG, "Setting Orange Theme");
                activity.setTheme(R.style.Theme_Orange);
                break;
        }
    }

    /**
     * Write to file saves the users list of cities for future viewing.
     * This is an overloaded function because you can write either city lists or themes to file
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

    /**
     * Write to file saves the users list of cities for future viewing.
     * This is an overloaded function because you can write either city lists or themes to file
     *
     * @param TAG is used to differentiate logs
     * @param fileDirect describes where to save the file to
     * @param fileName describes what the file name should be
     * @param theme is the data that should be saved to the file
     */
    public static void writeToFile(
            final String TAG,
            final File fileDirect,
            final String fileName,
            final int theme
    ) {
        try {
            Log.i(TAG, "Writing to file " + fileName);
            File file = new File(fileDirect, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(theme);
            oos.close();
            Log.i(TAG, "Wrote to file");
        } catch (Exception e2) {
            Log.i(TAG, "Could not write to file " + fileName);
            Log.e(TAG, e2.toString());
        }
    }
}