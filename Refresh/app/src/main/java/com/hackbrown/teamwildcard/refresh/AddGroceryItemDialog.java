package com.hackbrown.teamwildcard.refresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jenna on 2/4/17.
 */

public class AddGroceryItemDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private ArrayList<LinearLayout> cwTags;

    public AddGroceryItemDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        cwTags = new ArrayList<LinearLayout>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_add_grocery_item_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                //c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public String getText() {
        EditText et = (EditText) findViewById(R.id.itemText);
        return et.getText().toString();
    }

    public int getQuantity() {
        EditText et = (EditText) findViewById(R.id.itemQuantity);
        String s = et.getText().toString();
        return Integer.parseInt(s);
    }


}