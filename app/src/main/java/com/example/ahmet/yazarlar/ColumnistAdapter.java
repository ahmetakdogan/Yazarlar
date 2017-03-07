package com.example.ahmet.yazarlar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmet on 3.12.2016.
 */

public class ColumnistAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    public ArrayList<String> arrayListGazeteler;
    public ArrayList<String> arrayListYazarlar;
    public ArrayList<String> arrayListHaberler;
    public ArrayList<String> arrayListImg;
    Context c;

    public ColumnistAdapter(Context c, ArrayList<String> arrayListYazarlar){
        this.c = c;
        mInflater = LayoutInflater.from(c);
        this.arrayListYazarlar=arrayListYazarlar;
    }

    @Override
    public int getCount() {
        return arrayListYazarlar.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListYazarlar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item;
        item=mInflater.inflate(R.layout.news_item_layout,null);
        TextView txtName = (TextView) item.findViewById(R.id.txtName);
        TextView txtNews = (TextView) item.findViewById(R.id.txtNews);
        TextView txtTitle = (TextView) item.findViewById(R.id.txtTitle);
        ImageView iv = (ImageView) item.findViewById(R.id.imageView);

        Picasso.with(c).load(arrayListImg.get(position)).into(iv);

        txtName.setText(arrayListYazarlar.get(position));
        txtNews.setText(arrayListGazeteler.get(position));
        txtTitle.setText(arrayListHaberler.get(position));

        return item;
    }
}