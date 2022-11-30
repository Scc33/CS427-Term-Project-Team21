package edu.uiuc.cs427app;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ActivityTestBase {
    protected static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * Thanks to https://stackoverflow.com/a/34266640/13788914
     *
     * @param numChildrenMatcher how many children there should
     * @return If true if matches
     */
    public static Matcher<View> hasChildren(final Matcher<Integer> numChildrenMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                return view instanceof ViewGroup && numChildrenMatcher.matches(((ViewGroup)view).getChildCount());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" a view with # children is ");
                numChildrenMatcher.describeTo(description);
            }
        };
    }
}
