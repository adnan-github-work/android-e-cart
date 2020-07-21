package com.mobiquity.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.mobiquity.R;
import com.mobiquity.models.Category;
import com.mobiquity.models.Product;
import com.mobiquity.utils.AppUtils;
import com.mobiquity.utils.Constants;
import com.mobiquity.utils.NetworkCalls;

import java.util.ArrayList;

public class CategoryAdapter extends BaseExpandableListAdapter {


    private Context context;
    private ArrayList<Long> categoryIds;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Long> categoryIds, ArrayList<Category> categories) {
        this.context = context;
        this.categoryIds = categoryIds;
        this.categories = categories;
    }

    @Override
    public int getGroupCount() {
        return this.categoryIds.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return AppUtils.getCategoryAtPosition(categories, categoryIds, listPosition).getProducts().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.categoryIds.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return AppUtils.getCategoryAtPosition(categories, categoryIds, listPosition).getProducts().get(expandedListPosition);
    }


    @Override
    public long getGroupId(int listPosition) {
        return this.categoryIds.get(listPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = AppUtils.getCategoryAtPosition(categories, categoryIds, groupPosition).getName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_category, null);

            ExpandableListView eLV = (ExpandableListView) parent;
            eLV.expandGroup(groupPosition);

            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.tv_category_name);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);
            return convertView;
        }
        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final Product product = (Product) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_product_list, null);
        }
        TextView tv_product_name = (TextView) convertView
                .findViewById(R.id.tv_product_name);
        tv_product_name.setText(product.getName());
        ImageView productThumbnail = (ImageView) convertView.findViewById(R.id.product_thumbnail);
        NetworkCalls.loadImageFromServer(context, Constants.baseUrl + product.getUrl(), productThumbnail);
        convertView.setTag(product);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
