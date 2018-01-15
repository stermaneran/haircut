package com.example.user.eran;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;


public class EspressoTest {



    //  This rule provides functional testing of a single Activity.
    @Rule
    public ActivityTestRule<logIn> mActivityRule =
            new ActivityTestRule<>(logIn.class);

    //  Before runnimg the tests, we want the app to be on for sure.
    @Before
    public void holdOn() throws InterruptedException {
        sleep(1000);
        try{
            onView(withId(R.id.btnSignOut)).perform(click());
            sleep(1000);
        }
        catch (NoMatchingViewException e){}
        sleep(1000);
    }

    //  Checks that the user we added by the admin, succeeded loging in, and that the branches_text
    @Test
    public void intentHappened() throws InterruptedException {
        sleep(500);
        onView(withId(R.id.email)).perform(typeText("admin@gmail.com"));
        sleep(500);
        onView(withId(R.id.pass)).perform(typeText("admin12"));
        sleep(500);
        pressBack();
        sleep(5000);
        onView(withId(R.id.login)).perform(click());
        System.out.println("HERE");
    }

    //  Checks the sign_in string appears on the first activity.
    @Test
    public void signInButtonAppears() throws InterruptedException {
        onView((withText(R.string.Login))).check(matches(isDisplayed()));
    }

    //  Checks the sign_up string appears on the first activity.
    @Test
    public void signUpButtonAppears() throws InterruptedException {
        sleep(1000);
        try{
            onView(withId(R.id.btnSignOut)).perform(click());
            sleep(1000);
        }
        catch (NoMatchingViewException e){}
        sleep(1000);
        onView((withText(R.string.Register))).check(matches(isDisplayed()));
    }
}