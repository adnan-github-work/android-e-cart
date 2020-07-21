package com.mobiquity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.R;
import com.mobiquity.adapter.CategoryAdapter;
import com.mobiquity.models.Category;
import com.mobiquity.models.Product;
import com.mobiquity.utils.AppUtils;
import com.mobiquity.utils.Constants;
import com.mobiquity.utils.NetworkCalls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        ExpandableListView.OnChildClickListener,
        Runnable, Response.Listener<JSONArray>, Response.ErrorListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ExpandableListView expandableListView;
    private ArrayList<Category> categories;
    private ArrayList<Long> categoryIds;
    private CategoryAdapter categoryAdapter;
    private Context context;
    private ImageView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initializeUI();
    }

    // initializing ui components
    private void initializeUI() {
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(this);
        expandableListView = findViewById(R.id.ex_list_view_product);
        error = findViewById(R.id.error);
        expandableListView.setOnChildClickListener(this);
        swipeRefreshLayout.post(this);

    }

    private void getProductListFromServer() {
        swipeRefreshLayout.setRefreshing(true);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Constants.baseUrl,
                null,
                this,
                this
        );
        NetworkCalls.requestServer(context, jsonArrayRequest);
    }


    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        if (AppUtils.checkInternetConnection(context)) {
            error.setVisibility(View.GONE);
            getProductListFromServer();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            error.setVisibility(View.VISIBLE);
            error.setImageResource(R.drawable.no_internet);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
        Product product = AppUtils.getCategoryAtPosition(categories, categoryIds, groupPosition).getProducts().get(childPosition);
        Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class)
                .putExtra(Constants.productExtra, product);
        startActivity(intent);
        return false;
    }

    @Override
    public void run() {
        refresh();
    }

    @Override
    public void onResponse(JSONArray response) {

        ObjectMapper mapper = new ObjectMapper();
        swipeRefreshLayout.setRefreshing(false);
        try {
            ArrayList<Category> categories = (ArrayList<Category>) mapper.readValue(response.toString(), new TypeReference<List<Category>>() {
            });
            ProductListActivity.this.categories = categories;
            ArrayList<Long> categoryIds = (ArrayList<Long>) categories.stream().map(product -> product.getId()).collect(Collectors.toList());
            ProductListActivity.this.categoryIds = categoryIds;
            categoryAdapter = new CategoryAdapter(context, categoryIds, categories);
            expandableListView.setAdapter(categoryAdapter);
            error.setVisibility(View.GONE);
        } catch (JsonProcessingException e) {
            swipeRefreshLayout.setRefreshing(false);
            error.setVisibility(View.VISIBLE);
            error.setImageResource(R.drawable.something_went_wrong);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        swipeRefreshLayout.setRefreshing(false);
        ProductListActivity.this.error.setVisibility(View.VISIBLE);
        ProductListActivity.this.error.setImageResource(R.drawable.something_went_wrong);
    }
}