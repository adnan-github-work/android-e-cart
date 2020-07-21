package com.mobiquity.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mobiquity.R;

public class NetworkCalls {

    /**
     * load image
     */
    public static void loadImageFromServer(Context context, String url, ImageView photo) {
        ImageRequest ir = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        photo.setImageBitmap(response);
                    }
                }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                photo.setImageResource(R.drawable.no_content);
                photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });
        requestServer(context, ir);
    }

    public static void requestServer(Context context, Object request) {
        if (AppUtils.hasInternetConnection(context)) {
            if (request instanceof JsonArrayRequest) {
                JsonArrayRequest jsonArrayRequest = (JsonArrayRequest) request;
                JsonArrayNetworkRequest(jsonArrayRequest);
            } else if (request instanceof ImageRequest) {
                ImageRequest imageRequest = (ImageRequest) request;
                ImageNetworkRequest(imageRequest);
            }
        }

    }

    private static void ImageNetworkRequest(ImageRequest imageRequest) {
        imageRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationClass.getInstance().addToRequestQueue(imageRequest);
    }

    private static void JsonArrayNetworkRequest(JsonArrayRequest jsonArrayRequest) {
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationClass.getInstance().addToRequestQueue(jsonArrayRequest);
    }


}
