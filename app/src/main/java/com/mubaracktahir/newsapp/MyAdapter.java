package com.mubaracktahir.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;



class MyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public MyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer holder = null;
        if (convertView == null) {
            holder = new ViewContainer();
           // LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = convertView.findViewById(R.id.galleryImage);
            holder.author = convertView.findViewById(R.id.author);
            holder.title = convertView.findViewById(R.id.title);
            holder.sdetails =  convertView.findViewById(R.id.sdetails);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewContainer) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> oneList ;
        oneList = data.get(position);

        try{
            holder.author.setText(oneList.get(MainActivity.KEY_AUTHOR));
            holder.title.setText(oneList.get(MainActivity.KEY_TITLE));
            holder.time.setText(oneList.get(MainActivity.KEY_PUBLISHEDAT));
            holder.sdetails.setText(oneList.get(MainActivity.KEY_DESCRIPTION));

            if(oneList.get(MainActivity.KEY_URLTOIMAGE).length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.with(activity)
                        .load(oneList.get(MainActivity.KEY_URLTOIMAGE))
                        .resize(300, 200)
                        .into(holder.galleryImage);

            }
        }catch(Exception e) {


        }


        return convertView;
    }
}

class ViewContainer {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}
