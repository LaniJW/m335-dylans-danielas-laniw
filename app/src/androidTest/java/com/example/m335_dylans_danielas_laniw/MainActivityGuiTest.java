package com.example.m335_dylans_danielas_laniw;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityGuiTest {

    @Rule
    public ActivityTestRule mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.bottom_nav_test", appContext.getPackageName());
    }

    @Test
    public void activityLaunch() {
        onView(withId(R.id.reload_button)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_text_field)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_search_button)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_text_field)).check((matches(withText(""))));

        onView(withId(R.id.navigation_favorites)).perform(click());
        onView(withId(R.id.search_bar_text_field)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_search_button)).check(matches(isDisplayed()));
        onView(withId(R.id.reload_button)).check(matches(not(isDisplayed())));

        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.reload_button)).check(matches(isDisplayed()));
        onView(withId(R.id.search_bar_text_field)).perform(typeText("TestText"));
        onView(withId(R.id.search_bar_text_field)).check((matches(withText("TestText"))));

        onView(withId(R.id.search_bar_text_field)).perform(typeText("~"));
        onView(withId(R.id.invalid_search_text)).check((matches(withText(R.string.invalid_search))));

        onView(withId(R.id.search_bar_text_field)).perform(typeText("3843823"));
        onView(withId(R.id.invalid_search_text)).check((matches(withText(R.string.no_entries_search))));
    }
}
