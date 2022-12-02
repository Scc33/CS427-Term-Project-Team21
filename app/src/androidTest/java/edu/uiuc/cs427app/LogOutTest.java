package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LogOutTest {
    @Rule
    public ActivityTestRule<MainActivity> ActivityTestRule =
        new ActivityTestRule<>(MainActivity.class);
    @Test
    public void action_bar_functionality() throws InterruptedException {
        Thread.sleep(1500);
        openActionBarOverflowOrOptionsMenu(ActivityTestRule.getActivity());
        Thread.sleep(1500);
        onView(withText("LOGOUT")).check(matches(isDisplayed()));
        Thread.sleep(1000);
        onView(withText("LOGOUT")).perform(pressBack());
        Thread.sleep(1000);
        onView(withText("LOGOUT")).check(doesNotExist());
        Thread.sleep(2000);
    }
    @Test
    public void log_out_functionality() throws InterruptedException {
        Thread.sleep(1500);
        openActionBarOverflowOrOptionsMenu(ActivityTestRule.getActivity());
        Thread.sleep(1500);
        onView(withText("LOGOUT")).perform(click());
        Thread.sleep(1500);
        onView(withText("Sign In")).check(matches(isDisplayed()));
        onView(withText("LOGIN")).check(matches(isDisplayed()));
        onView(withText("SIGN UP")).check(matches(isDisplayed()));
        onView(withText("Theme Setting")).check(matches(isDisplayed()));
        onView(withText("DEFAULT THEME")).check(matches(isDisplayed()));
        onView(withText("TEAL THEME")).check(matches(isDisplayed()));
        onView(withText("ORANGE THEME")).check(matches(isDisplayed()));
        Thread.sleep(1500);
    }
}