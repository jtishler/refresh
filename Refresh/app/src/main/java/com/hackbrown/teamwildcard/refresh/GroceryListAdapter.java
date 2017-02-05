package com.hackbrown.teamwildcard.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jenna on 2/4/17.
 */

public class GroceryListAdapter extends ArrayAdapter<GroceryListItem> {

    private Context context;
    private List<GroceryListItem> groceryListItems;

    public GroceryListAdapter(List<GroceryListItem> groceryListItems, Context context) {
        super(context, R.layout.grocery_list_item, groceryListItems);
        this.groceryListItems = groceryListItems;
        this.context = context;

    }

    private static class GroceryListItemHolder {
        public TextView itemName;
        public CheckBox checkBox;
        public TextView quantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        GroceryListItemHolder holder = new GroceryListItemHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grocery_list_item, null);

            holder.itemName = (TextView) v.findViewById(R.id.itemName);
            holder.checkBox = (CheckBox) v.findViewById(R.id.check_box);
            holder.quantity = (TextView) v.findViewById(R.id.quantity);

            holder.checkBox.setOnCheckedChangeListener((MainActivity) getContext());
            v.setTag(holder);
        } else {
            holder = (GroceryListItemHolder) v.getTag();
        }

        if (groceryListItems.size() > 0) {
            GroceryListItem item = groceryListItems.get(position);
            holder.itemName.setText(item.getName());
            holder.checkBox.setChecked(item.isSelected());
            holder.checkBox.setTag(item);
            holder.quantity.setText("" + item.getQuantity());
        }

        return v;
    }
}