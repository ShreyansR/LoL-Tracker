package com.example.lol_tracker;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BuildListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<build> buildList;
    //private final String[] maintitle;
    //private final Integer[] imgid;

    public BuildListAdapter(Activity context, List<build> buildList) {
        super(context, R.layout.build_row_item);

        this.context = context;
        this.buildList = buildList;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.build_row_item, null, true);


        TextView titleText = rowView.findViewById(R.id.build_name);
        ImageView[] imageView = {rowView.findViewById(R.id.champ1), rowView.findViewById(R.id.champ2),
                rowView.findViewById(R.id.champ3), rowView.findViewById(R.id.champ4),
                rowView.findViewById(R.id.champ5), rowView.findViewById(R.id.champ6),
                rowView.findViewById(R.id.champ7), rowView.findViewById(R.id.champ8)};
        TextView synsText = rowView.findViewById(R.id.build_syns);
        titleText.setText(buildList.get(position).name);
        Log.d("Build list adapter", buildList.get(position).name);

        for (int i = 0; i < imageView.length ; i++){
            imageView[i].setImageResource(buildList.get(position).imgid[i]);
        }
        for (String syn : buildList.get(position).synergy){
            synsText.append(syn);
        }
        return rowView;
    }
}
