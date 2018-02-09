package com.kala.lisa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kalaman on 01.02.18.
 */

public class AdRecyclerAdapter extends RecyclerView.Adapter<AdRecyclerAdapter.AdViewholder>{

    ArrayList<Ad> arrayListAd;

    public AdRecyclerAdapter(ArrayList <Ad> arrayList) {
        arrayListAd = arrayList;
    }

    @Override
    public AdRecyclerAdapter.AdViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ad, parent, false);

        return new AdRecyclerAdapter.AdViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(AdRecyclerAdapter.AdViewholder holder, int position) {
        Ad currentAd = arrayListAd.get(position);
        holder.textViewTitle.setText(currentAd.getTitle());
        holder.textViewDescription.setText(currentAd.getDescription());
    }

    public ArrayList<Ad> getAds() {
        return arrayListAd;
    }

    @Override
    public int getItemCount() {
        return arrayListAd.size();
    }

    class AdViewholder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription;

        public AdViewholder(View itemView) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.textViewTitle);
            textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
        }
    }
}
