package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WeatherTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testChicagoWeather() {
        onView(withId(R.id.createCity)).perform(click());
        onView(withId(R.id.cityName)).perform(typeText("Chicago"));
        onView(withId(R.id.latitude)).perform(typeText("41.8781"));
        onView(withId(R.id.longitude)).perform(typeText("-87.6298"));
        onView(withId(R.id.createCity)).perform(click());

        onView(withId(R.id.cityListLayout)).perform(click());
        onView(withId(R.id.weatherButton)).perform(click());

        onView(withId(R.id.city_name_tv)).check(matches(withText("Chicago")));
        onView(withId(R.id.weather_Dscp_tv)).check(matches(not(withText("Weather Description"))));
        onView(withId(R.id.temperature_tv)).check(matches(not(withText("99"))));
        onView(withId(R.id.humidity_tv)).check(matches(not(withText("99"))));
        onView(withId(R.id.wind_tv)).check(matches(not(withText("99"))));

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
        onView(withId(R.id.removeCitiesList)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
    }

    @Test
    public void testNewYorkCityWeather() {
        onView(withId(R.id.createCity)).perform(click());
        onView(withId(R.id.cityName)).perform(typeText("New York City"));
        onView(withId(R.id.latitude)).perform(typeText("40.7128"));
        onView(withId(R.id.longitude)).perform(typeText("-74.0060"));
        onView(withId(R.id.createCity)).perform(click());

        onView(withId(R.id.cityListLayout)).perform(click());
        onView(withId(R.id.weatherButton)).perform(click());

        onView(withId(R.id.city_name_tv)).check(matches(withText("New York City")));
        onView(withId(R.id.weather_Dscp_tv)).check(matches(not(withText("Weather Description"))));
        onView(withId(R.id.temperature_tv)).check(matches(not(withText("99"))));
        onView(withId(R.id.humidity_tv)).check(matches(not(withText("99"))));
        onView(withId(R.id.wind_tv)).check(matches(not(withText("99"))));

        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
        onView(withId(R.id.removeCitiesList)).perform(click());
        onView(withId(R.id.removeCity)).perform(click());
    }
}