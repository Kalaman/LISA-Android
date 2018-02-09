package com.kala.lisa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Kalaman on 30.01.18.
 */

public class PurchaseActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    PurchaseRecyclerAdapter purchaseRecyclerAdapter;

    TextView textViewName;
    TextView textViewItemAmount;
    TextView textViewPrice;
    TextView textViewDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_purchase);

        int index = getIntent().getExtras().getInt("index_purchaselist");
        PurchaseList selectedPurchaseList = MainActivity.purchaseLists.get(index);

        CardView cardViewPurchase = (CardView) findViewById(R.id.purchaseInclude);

        textViewName = (TextView)cardViewPurchase.findViewById(R.id.textViewName);
        textViewItemAmount = (TextView)cardViewPurchase.findViewById(R.id.textViewAmount);
        textViewPrice = (TextView)cardViewPurchase.findViewById(R.id.textViewPrice);
        textViewDate = (TextView)cardViewPurchase.findViewById(R.id.textViewDate);

        textViewName.setText("REWE");
        textViewItemAmount.setText(selectedPurchaseList.getPurchaseArrayList().size() + " Artikel");
        textViewDate.setText(selectedPurchaseList.getDate());
        textViewPrice.setText(selectedPurchaseList.getTotalPrice() + " €");

        purchaseRecyclerAdapter = new PurchaseRecyclerAdapter(selectedPurchaseList.getPurchaseArrayList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(purchaseRecyclerAdapter);
        recyclerView.setHasFixedSize(true);

    }


}
