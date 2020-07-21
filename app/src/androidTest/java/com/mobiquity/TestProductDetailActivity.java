package com.mobiquity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.mobiquity.activity.ProductDetailActivity;
import com.mobiquity.models.Product;
import com.mobiquity.models.SalesPrice;
import com.mobiquity.utils.AppUtils;
import com.mobiquity.utils.Constants;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class TestProductDetailActivity {

    @Rule
    public ActivityTestRule<ProductDetailActivity> mActivityRule =
            new ActivityTestRule<ProductDetailActivity>(ProductDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Product product = new Product(101, 1, "Milk", "/Milk.jpg", "", new SalesPrice(123, "EUR"));
                    Intent result = new Intent(targetContext, ProductDetailActivity.class);
                    result.putExtra(Constants.productExtra, product);
                    return result;
                }
            };

    public Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void isActivityOnView() {
        onView(withId(R.id.product_detail_activity))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUIComponentVisibility() {
        onView(withId(R.id.im_product))
                .check(matches(isDisplayed()));
        onView(withId(R.id.tv_product_name))
                .check(matches(isDisplayed()));
        onView(withId(R.id.tv_product_price))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testUIComponentContent() {
        onView(withId(R.id.tv_product_name))
                .check(matches(withText("Milk")));
        onView(withId(R.id.tv_product_price))
                .check(matches(withText("Price: 123.0 EUR")));
        if (!AppUtils.hasInternetConnection(context)) {
            onView(withId(R.id.im_product))
                    .check(matches(withDrawable(R.drawable.no_content)));
        }

    }

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }

}
