package com.example.patrice.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.patrice.bakingapp.TestUtils.RecyclerViewMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Patrice on 11/18/2017.
 */
//Very much inspired by the Udacity Tea Time example
@RunWith(AndroidJUnit4.class)
public class EspressoTests {
    private static final String INGREDIENTS_LABEL = "Ingredients";
    private static final String STEP_3 = "Press the crust into baking form.";
    private static final String DETAIL_INGREDIENT = "salt";
    private static final String STEP_DESCRIPTION1 = "1. Preheat the oven";
    private static final String STEP_DESCRIPTION2 = "6. Pour the filling";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void idlingTest() {
        dataLoadingTest();
        stepListLoadingTest();
        detailLoadingTest_video();
        detailLoadingTest_noVideo();
        detailStepNavigation();
        loadIngredients();
        navigateUpFromSteps();
    }

    public void dataLoadingTest() {
        onView(ViewMatchers.withId(R.id.rv_main_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
    }

    public void stepListLoadingTest() {
        onView(withId(R.id.tv_ingredients_label)).check(matches(withText(INGREDIENTS_LABEL)));

        onView(withRecyclerView(R.id.rv_recipestep_list).atPosition(3))
                .check(matches(hasDescendant(withText(STEP_3))));
    }

    public void detailLoadingTest_video() {
        onView(ViewMatchers.withId(R.id.rv_recipestep_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4,
                        click()));
        onView(withId(R.id.playerView)).check(matches(isDisplayed()));
        onView(withContentDescription("Navigate up")).perform(click());
    }

    public void detailLoadingTest_noVideo() {
        onView(ViewMatchers.withId(R.id.rv_recipestep_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5,
                        click()));
        onView(withId(R.id.playerView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("Navigate up")).perform(click());
    }

    public void loadIngredients() {
        onView(withId(R.id.iv_spice_photo))
                .perform(click());
        onView(withRecyclerView(R.id.rv_ingredient_list).atPosition(3))
                .check(matches(hasDescendant(withText(DETAIL_INGREDIENT))));
        onView(withContentDescription("Navigate up")).perform(click());
    }

    public void detailStepNavigation() {
        onView(ViewMatchers.withId(R.id.rv_recipestep_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
        onView(withId(R.id.button_previous))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.button_next))
                .perform(click());
        onView(withId(R.id.tv_step_description))
                .check(matches(withText(startsWith(STEP_DESCRIPTION1))));
        onView(withId(R.id.button_previous))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("Navigate up")).perform(click());
        onView(ViewMatchers.withId(R.id.rv_recipestep_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6,
                        click()));
        onView(withId(R.id.tv_step_description))
                .check(matches(withText(startsWith(STEP_DESCRIPTION2))));
        onView(withId(R.id.button_previous))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.button_next))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("Navigate up")).perform(click());
    }

    public void navigateUpFromSteps() {
        onView(withContentDescription("Navigate up")).perform(click());
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
