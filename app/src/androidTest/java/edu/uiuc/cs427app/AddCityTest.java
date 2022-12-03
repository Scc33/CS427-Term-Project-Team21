package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddCityTest {
    static Intent intent;
    static {
        intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), NewCityActivity.class);
        intent.putExtra("username", "testUser");
        String cityListFile = "testUser" + "-cityList.txt";

        ArrayList<City> cityList;
        try {
            File file = new File("/data/user/0/edu.uiuc.cs427app/files", cityListFile);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityList = (ArrayList<City>) ois.readObject();
            ois.close();
        } catch (Exception e1) {
            cityList = new ArrayList<>();
            File fileDir = InstrumentationRegistry.getInstrumentation().getContext().getFilesDir();
            writeToFile(fileDir, cityListFile, cityList);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("ARRAYLIST", (Serializable) cityList);
        intent.putExtra("cities", bundle);
        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<NewCityActivity> activityScenarioRule = new ActivityScenarioRule<NewCityActivity>(
            intent
    );


    public static void writeToFile(
            final File fileDirect,
            final String fileName,
            final ArrayList<City> cityList
    ) {
        try {
            File file = new File(fileDirect, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cityList);
            oos.close();
        } catch (Exception e2) {
        }
    }

    /**
     * Note this test will show an error screen while it checks what is stored in the file
     * @throws InterruptedException for the thread.sleep
     */
    @Test
    public void testAddingCity() throws InterruptedException {
        String testCity = "New Delhi";
        Double testLatitude = 28.6139;
        Double testLongitude = 77.2090;
        onView(withId(R.id.cityName)).perform(typeText(testCity));
        onView(withId(R.id.latitude)).perform(typeText(Double.toString(testLatitude)));
        onView(withId(R.id.longitude)).perform(typeText(Double.toString(testLongitude)));
        Thread.sleep(2000);
        onView(withId(R.id.createCity)).perform(click());

        ArrayList<City> cityListTest;
        try {
            File file = new File("/data/user/0/edu.uiuc.cs427app/files", "testUser" + "-cityList.txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityListTest = (ArrayList<City>) ois.readObject();
            ois.close();
        } catch (Exception e1) {
            cityListTest = new ArrayList<>();
        }

        String cityNameAfterInsert = "";
        Double latitudeAfterInsert = 0.0, longitudeAfterInsert= 0.0;

        for (City city: cityListTest) {
            if (city.cityName.equals(testCity)) {
                cityNameAfterInsert = city.cityName;
                latitudeAfterInsert = (Double) city.latitude;
                longitudeAfterInsert = (Double) city.longitude;
                break;
            }
        }

        assertEquals(testCity, cityNameAfterInsert);
        assertEquals(testLatitude, latitudeAfterInsert);
        assertEquals(testLongitude, longitudeAfterInsert);
    }
}
