package com.hackbrown.teamwildcard.refresh;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by ellenjiang on 2/4/17.
 */



public class RecipeActivity extends ListActivity
{
    String[] myStringArray;
    ArrayAdapter<String> adapter;

    ListView listView;

    ArrayList<String> myRecipesURLs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myStringArray = new String[]{"Crème brulée", "Apple pie", "Apple strudel", "Egg tart"};
        ArrayList<String> myRecipes = new ArrayList<String>();

        myRecipesURLs = new ArrayList<String>();

        myRecipes = getIntent().getStringArrayListExtra("RECIPES");
        myRecipesURLs = getIntent().getStringArrayListExtra("URLS");


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myRecipes);
        listView = (ListView) findViewById(R.id.recipe_list);
        //listView.setAdapter(adapter);
        setListAdapter(adapter);


    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
        //Intent explicitIntent = new Intent(this, SearcherActivity.class);

        //startActivity(explicitIntent);

        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myRecipesURLs.get(position)));
        startActivity(myIntent);
    }

}
