package com.hackbrown.teamwildcard.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jenna on 2/5/17.
 */

public class KitchenListAdapter  extends ArrayAdapter<FoodItem> {

    private Context context;
    private List<FoodItem> kitchenListItems;

    public KitchenListAdapter(List<FoodItem> kitchenListItems, Context context) {
        super(context, R.layout.kitchen_list_item, kitchenListItems);
        this.kitchenListItems = kitchenListItems;
        this.context = context;

    }

    private static class KitchenListItemHolder {
        public TextView itemName;
        public TextView expireDate;
        public TextView quantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        KitchenListAdapter.KitchenListItemHolder holder = new KitchenListAdapter.KitchenListItemHolder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.kitchen_list_item, null);

            holder.itemName = (TextView) v.findViewById(R.id.itemName);
            holder.expireDate = (TextView) v.findViewById(R.id.expireDate);
            holder.quantity = (TextView) v.findViewById(R.id.quantity);

            v.setTag(holder);
        } else {
            holder = (KitchenListAdapter.KitchenListItemHolder) v.getTag();
        }

        if (kitchenListItems.size() > 0) {
            FoodItem item = kitchenListItems.get(position);
            holder.itemName.setText(item.getName());
            holder.expireDate.setText("date");
            holder.quantity.setText("" + item.getQuantity());
        }

        return v;
    }
}