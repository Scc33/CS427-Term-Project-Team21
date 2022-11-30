package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RemoveCityTest extends ActivityTestBase {
    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), RemoveCitiesActivity.class);
        intent.putExtra("username", "testUser");
        Bundle args = new Bundle();
        ArrayList<City> cityList = new ArrayList<>();
        cityList.add(new City("Chicago", 1, 1));
        cityList.add(new City("Champaign", 2, 2));
        args.putSerializable("ARRAYLIST", cityList);
        intent.putExtra("cities", args);
    }

    @Rule
    public ActivityScenarioRule<RemoveCitiesActivity> activityRule =
            new ActivityScenarioRule<>(intent);

    @Test
    public void removeCityTest() throws InterruptedException {
        onView(withId(R.id.removeCitiesList)).check(matches(allOf(
                isDisplayed(),
                hasChildren(is(2))
        )));

        ViewInteraction removeChicago = onView(
                allOf(withText("REMOVE CHICAGO"),
                        withParent(allOf(withId(R.id.removeCitiesList),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        removeChicago.check(matches(withText("REMOVE CHICAGO")));

        ViewInteraction removeChampaign = onView(
                allOf(withText("REMOVE CHAMPAIGN"),
                        withParent(allOf(withId(R.id.removeCitiesList),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        removeChampaign.check(matches(withText("REMOVE CHAMPAIGN")));

        Thread.sleep(200);

        removeChicago.perform(click());

        Thread.sleep(200);

        onView(withId(R.id.removeCitiesList)).check(matches(allOf(
                isDisplayed(),
                hasChildren(is(1))
        )));

        ViewInteraction updateMyCities = onView(
                allOf(withText("UPDATE MY CITIES"), isDisplayed()));
        updateMyCities.check(matches(withText("UPDATE MY CITIES")));
    }
}