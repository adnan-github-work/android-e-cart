package com.mobiquity;

import android.content.Context;
import android.widget.ExpandableListView;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.mobiquity.activity.ProductListActivity;
import com.mobiquity.utils.AppUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class TestProductListActivity {

    @Rule
    public ActivityScenarioRule activityRule = new ActivityScenarioRule(ProductListActivity.class);
    public Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void testVisibility() {

        if (AppUtils.hasInternetConnection(context)) {
            onView(withId(R.id.error))
                    .check(matches(not(isDisplayed())));
            onView(withId(R.id.ex_list_view_product))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testNavigationActivity() {
        if (AppUtils.hasInternetConnection(context)) {
            //perform click on list item
            onView(withId(R.id.ex_list_view_product)).perform(click());
            //product details activity
            onView(withId(R.id.product_detail_activity))
                    .check(matches(isDisplayed()));
            //perform back press
            onView(isRoot()).perform(pressBack());
            //back to product list activity
            onView(withId(R.id.activity_product_list))
                    .check(matches(isDisplayed()));
        }

    }

}
