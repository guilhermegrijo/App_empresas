package com.guilherme.appempresas;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.StringRes;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class LoginTestInstrumented {


    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void registerIdlingResource() {
        // let espresso know to synchronize with background tasks
        IdlingRegistry.getInstance().register(EspressoTestingIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoTestingIdlingResource.getIdlingResource());
    }

    @Before
    public void init() {
        activityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }

    @Test
    public void Should_FailLogin_EmailIsEmpty() {
        onView(withId(R.id.email)).perform(clearText());
        onView(withId(R.id.password_edit)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(clearText());
        onView(withId(R.id.login_btn)).perform(click());

        onView(withId(R.id.email)).check(matches(withError(getString(R.string.error_invalid_email))));
    }

    @Test
    public void Should_FailLogin_WrongCredentials() {
        onView(withId(R.id.email)).perform(typeText("testeapple@ioasys.com"), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(typeText("12345"), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());

        onView(withId(R.id.error_credentials)).check(matches(isDisplayed()));
    }

    @Test
    public void Should_DoSuccessfullLogin_CorrectCredentials() {
        onView(withId(R.id.email)).perform(typeText("testeapple@ioasys.com.br"), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(typeText("12341234"), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());

        onView(withId(R.id.placeholder)).check(matches(isDisplayed()));
    }

    private String getString(@StringRes int resourceId) {
        return activityRule.getActivity().getString(resourceId);
    }

    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                if (item instanceof EditText) {
                    return ((EditText) item).getError().toString().equals(expected);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message" + expected + ", find it!");
            }
        };
    }
}
