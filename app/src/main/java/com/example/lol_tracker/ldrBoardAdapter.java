package com.example.lol_tracker;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ldrBoardAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> maintitle;
    private final ArrayList<Integer> leaguePts;
    private final ArrayList<String> rank;
    //private final Integer[] imgid;

    public ldrBoardAdapter(Activity context, ArrayList<String> maintitle, ArrayList<Integer> lp, ArrayList<String> rank) {
        super(context, R.layout.row_item, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.leaguePts=lp;
        this.rank = rank;

    }

    public View getView(int position,View view,ViewGroup parent)  {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.ldr_row, null,true);

        TextView subtitle = rowView.findViewById(R.id.subtitle);
        TextView numText = (TextView) rowView.findViewById(R.id.rankNum);
        TextView titleText = (TextView) rowView.findViewById(R.id.ldrName);
        TextView lPts = rowView.findViewById(R.id.leaguePts);

        subtitle.setText(rank.get(position));
        numText.setText(String.valueOf(position + 1));
        titleText.setText(maintitle.get(position));
        lPts.setText(String.valueOf(leaguePts.get(position)));


        return rowView;

    };
}
