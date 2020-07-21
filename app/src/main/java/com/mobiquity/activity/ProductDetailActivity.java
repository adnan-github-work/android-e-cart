package com.mobiquity.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiquity.R;
import com.mobiquity.models.Product;
import com.mobiquity.utils.AppUtils;
import com.mobiquity.utils.Constants;
import com.mobiquity.utils.NetworkCalls;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    private TextView tv_product_name, tv_product_price;
    private ImageView im_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initializeUI();
    }

    // initializing ui components
    private void initializeUI() {
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra(Constants.productExtra);
        tv_product_price = findViewById(R.id.tv_product_price);
        tv_product_name = findViewById(R.id.tv_product_name);
        im_product = findViewById(R.id.im_product);

        //checking internet connectivity before getting image from server
        if (AppUtils.hasInternetConnection(this)) {
            NetworkCalls.loadImageFromServer(this, Constants.baseUrl + product.getUrl(), im_product);
        } else {
            im_product.setImageResource(R.drawable.no_content);
            im_product.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Toast.makeText(this, R.string.internet_connection_failed, Toast.LENGTH_LONG).show();
        }
        tv_product_name.setText(product.getName());
        tv_product_price.setText(Constants.price + product.getSalePrice().getAmount() + Constants.space + product.getSalePrice().getCurrency());
    }
}