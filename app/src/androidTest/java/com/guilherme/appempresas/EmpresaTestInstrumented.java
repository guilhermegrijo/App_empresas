package com.guilherme.appempresas;

import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.StringRes;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class EmpresaTestInstrumented {


    private String email = "testeapple@ioasys.com.br";
    private String senha = "12341234";


    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void registerIdlingResource() {
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
    public void Should_SucessufullyOpenEnterpriseDetail_QueryReturnedEnterpriseList() {
        onView(withId(R.id.email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(typeText(senha), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());

        onView(withId(R.id.action_search)).perform(click());

        onView(withId(Resources.getSystem().getIdentifier("search_src_text",
                "id", "android"))).perform(clearText(), typeText("a"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        onView(withId(R.id.description)).check(matches(isDisplayed()));

    }

    @Test
    public void Should_ShowEmptyState_DidntFindQuery() {
        onView(withId(R.id.email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password_edit)).perform(typeText(senha), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(click());

        onView(withId(R.id.action_search)).perform(click());

        onView(withId(Resources.getSystem().getIdentifier("search_src_text",
                "id", "android"))).perform(clearText(), typeText("aXXXXXAAAAAXXXXX"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER));


        onView(withId(R.id.empty_list)).check(matches(isDisplayed()));

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
