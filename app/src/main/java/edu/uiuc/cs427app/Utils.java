package edu.uiuc.cs427app;

import android.app.Activity;
import android.content.Intent;

public class Utils {
    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_TEAL = 1;
    public final static int THEME_ORANGE = 2;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

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
}