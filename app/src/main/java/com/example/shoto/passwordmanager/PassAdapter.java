package com.example.shoto.passwordmanager;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PassAdapter extends BaseAdapter {

    private ArrayList<String> arrayList;
    Context context;
    private LayoutInflater layoutInflater;

    public PassAdapter(Context context, ArrayList<String> arrayList){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.pass_list_item, viewGroup, false);
        TextView tv = view.findViewById(R.id.tvListItem);
        tv.setText(arrayList.get(i));
        CardView cv = view.findViewById(R.id.cvPassItems);
        final String from = tv.getText().toString();

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PassStoreActivity.class);
                i.putExtra("from", from);
                context.startActivity(i);
            }
        });

        return view;
    }
}

