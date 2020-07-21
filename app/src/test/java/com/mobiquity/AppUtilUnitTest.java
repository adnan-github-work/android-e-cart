package com.mobiquity;


import com.mobiquity.models.Category;
import com.mobiquity.models.Product;
import com.mobiquity.utils.AppUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class AppUtilUnitTest {

    ArrayList<Category> categories;
    ArrayList<Long> categoryIds;

    @Before
    public void mockData() {
        populateCategoryData();
    }

    @Test
    public void testCategoryFromListAtPosition() {
        Category category = AppUtils.getCategoryAtPosition(categories, categoryIds, 0);

        assertEquals(category.getName(), "Food");
        assertEquals(category.getId(), 1);
        assertEquals(category.getProducts().get(0).getName(), "Cake");
    }


    private void populateCategoryData() {
        categories = new ArrayList<Category>();
        Product product = new Product();
        product.setCategoryId(1);
        product.setDescription("Baked cake");
        product.setId(101);
        product.setName("Cake");
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(product);
        Category category = new Category();
        category.setId(1);
        category.setDescription("");
        category.setName("Food");
        category.setProducts(products);
        categories.add(category);

        categoryIds = new ArrayList<Long>();
        categoryIds.add(1L);
    }
}