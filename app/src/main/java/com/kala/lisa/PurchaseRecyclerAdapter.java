package com.kala.lisa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kalaman on 26.01.18.
 */


public class PurchaseRecyclerAdapter extends RecyclerView.Adapter<PurchaseRecyclerAdapter.PurchaseViewholder>{

    ArrayList<Purchase> arrayListPurchase;

    public PurchaseRecyclerAdapter(ArrayList <Purchase> arrayList) {
        arrayListPurchase = arrayList;
    }

    @Override
    public PurchaseViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_purchase, parent, false);

        return new PurchaseViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchaseViewholder holder, int position) {
        Purchase curentPurchase = arrayListPurchase.get(position);
        holder.textViewName.setText(curentPurchase.getProductName());
        holder.textViewPrice.setText("" + String.format("%.2f",curentPurchase.getPrice()) + "€");
    }

    public ArrayList<Purchase> getPurchaseArrayList() {
        return arrayListPurchase;
    }

    @Override
    public int getItemCount() {
        return arrayListPurchase.size();
    }

    class PurchaseViewholder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewPrice;

        public PurchaseViewholder(View itemView) {
            super(itemView);
            textViewName = (TextView)itemView.findViewById(R.id.textViewName);
            textViewPrice = (TextView)itemView.findViewById(R.id.textViewPrice);
        }
    }
}
