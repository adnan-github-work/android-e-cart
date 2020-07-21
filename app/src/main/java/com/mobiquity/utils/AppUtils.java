package com.mobiquity.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.mobiquity.R;
import com.mobiquity.models.Category;

import java.util.ArrayList;

public class AppUtils {

    public static Category getCategoryAtPosition(ArrayList<Category> categories, ArrayList<Long> categoryIds, int listPosition) {
        return categories.stream()
                .filter(cat -> cat.getId() == categoryIds.get(listPosition))
                .findFirst().get();
    }

    /**
     * checking internet connection
     *
     * @return
     */
    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * checking internet connection
     *
     * @return
     */
    public static boolean checkInternetConnection(Context context) {
        boolean hasInternetConnection = hasInternetConnection(context);
        if (!hasInternetConnection) {
            showWarningDialog(context.getResources().getString(R.string.internet_connection_failed), context, context.getResources().getString(R.string.internet_connectivity));
        }

        return hasInternetConnection;
    }

    /**
     * show warning dialog
     *
     * @param message
     */
    public static void showWarningDialog(String message, Context context, String alertTittle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(alertTittle);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }

}
