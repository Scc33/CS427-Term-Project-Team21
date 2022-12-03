package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;

import android.content.Context;
import android.widget.TextView;

import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LocationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> ActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void City1FeatureTest() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.createCity)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.cityName)).perform(replaceText("Chicago"), closeSoftKeyboard());
        onView(withId(R.id.latitude)).perform(replaceText("41.739685"), closeSoftKeyboard());
        onView(withId(R.id.longitude)).perform(replaceText("-87.554420"), closeSoftKeyboard());
        onView(withId(R.id.createCity)).perform(click());
        Thread.sleep(2000);
        onView(withId(0)).perform(click());
        onView(withId(R.id.mapButton)).perform(click());
        Thread.sleep(2000);
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Chicago")));
        onView(withId(R.id.latLong)).check(matches(withText("Latitude: 41.739685, Longitude: -87.55442")));
        onView(withId(R.id.webView)).check(matches(isDisplayed()));

        onView(withId(R.id.mapBackButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
        onView(withId(R.id.removeCitiesList)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
    }

    @Test
    public void City2FeatureTest() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.createCity)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.cityName)).perform(replaceText("Los Angeles"), closeSoftKeyboard());
        onView(withId(R.id.latitude)).perform(replaceText("34.0522342"), closeSoftKeyboard());
        onView(withId(R.id.longitude)).perform(replaceText("-118.2436849"), closeSoftKeyboard());
        onView(withId(R.id.createCity)).perform(click());
        Thread.sleep(2000);
        onView(withId(0)).perform(click());
        onView(withId(R.id.mapButton)).perform(click());
        Thread.sleep(2000);
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText("Los Angeles")));
        onView(withId(R.id.latLong)).check(matches(withText("Latitude: 34.0522342, Longitude: -118.2436849")));
        onView(withId(R.id.webView)).check(matches(isDisplayed()));

        onView(withId(R.id.mapBackButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
        onView(withId(R.id.removeCitiesList)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
    }
}
