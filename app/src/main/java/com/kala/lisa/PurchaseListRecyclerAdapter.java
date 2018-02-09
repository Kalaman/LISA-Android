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


public class PurchaseListRecyclerAdapter extends RecyclerView.Adapter<PurchaseListRecyclerAdapter.PurchaseViewholder>{

    ArrayList<PurchaseList> arrayListPurchase;

    public PurchaseListRecyclerAdapter(ArrayList <PurchaseList> arrayList) {
        arrayListPurchase = arrayList;
    }

    @Override
    public PurchaseViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_purchaselist, parent, false);

        return new PurchaseViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchaseViewholder holder, int position) {
        PurchaseList currentPurchaseList = arrayListPurchase.get(position);
        holder.textViewName.setText("REWE Kauf");
        holder.textViewDate.setText(currentPurchaseList.getDate());
        holder.textViewItemAmount.setText(currentPurchaseList.getPurchaseArrayList().size() + " Artikel");
        holder.textViewPrice.setText("" + String.format("%.2f",currentPurchaseList.getTotalPrice()) + "€");
    }

    public ArrayList<PurchaseList> getPurchaseArrayList() {
        return arrayListPurchase;
    }

    public void setPurchaseArrayList(ArrayList<PurchaseList> arrayListPurchase) {
        this.arrayListPurchase = arrayListPurchase;
    }

    @Override
    public int getItemCount() {
        return arrayListPurchase.size();
    }

    class PurchaseViewholder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewItemAmount;
        TextView textViewPrice;
        TextView textViewDate;

        public PurchaseViewholder(View itemView) {
            super(itemView);
            textViewName = (TextView)itemView.findViewById(R.id.textViewName);
            textViewItemAmount = (TextView)itemView.findViewById(R.id.textViewAmount);
            textViewPrice = (TextView)itemView.findViewById(R.id.textViewPrice);
            textViewDate = (TextView)itemView.findViewById(R.id.textViewDate);
        }
    }
}
