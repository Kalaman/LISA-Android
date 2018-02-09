package com.kala.lisa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<PurchaseList> purchaseLists;
    private TextView mTextMessage;
    RecyclerView recyclerView;
    PurchaseListRecyclerAdapter purchaseListRecyclerAdapter;
    AdRecyclerAdapter adRecyclerAdapter;
    ArrayList <Ad> adArrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    boolean status_home = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    recyclerView.setAdapter(adRecyclerAdapter);
                    ItemClickSupport.addTo(recyclerView).setOnItemClickListener(null);
                    status_home = true;
                    return true;
                case R.id.navigation_purchases:
                    recyclerView.setAdapter(purchaseListRecyclerAdapter);

                    ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            if (purchaseLists.get(position).getPurchaseArrayList().size() > 0) {
                                Intent intent = new Intent(MainActivity.this, PurchaseActivity.class);
                                intent.putExtra("index_purchaselist", position);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Dies ist ein leerer Einkauf !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    status_home = false;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        purchaseLists = getPurchaseList();

        //Dummy Ads
        adArrayList.add(new Ad("REWE Angebot","Barilla Pasta (500g) für nur 0.77€"));
        adArrayList.add(new Ad("REWE Angebot","Kikkoman Soja Sauce für nur 1.79€"));
        adArrayList.add(new Ad("REWE Angebot","Mazola Sesamöl (100ml) für nur 0.99€"));
        adArrayList.add(new Ad("Herzlich Willkommen !","Wir freuen uns, dass Sie LISA nutzen"));
        adRecyclerAdapter = new AdRecyclerAdapter(adArrayList);

        purchaseListRecyclerAdapter =  new PurchaseListRecyclerAdapter(purchaseLists);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adRecyclerAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (!status_home){
                    purchaseLists = getPurchaseList();
                    ((PurchaseListRecyclerAdapter)recyclerView.getAdapter()).setPurchaseArrayList(purchaseLists);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public ArrayList<PurchaseList> getPurchaseList () {
        ArrayList<PurchaseList> purchaseLists = new ArrayList<>();

        try {
            JSONArray response = new FetchJSONArray("http://" + Constants.SERVER_IP +"/sessions/" + Constants.USER_ID).execute().get();

            for(int i=0;i<response.length();++i) {
                JSONObject jsonObject = response.getJSONObject(i);

                String sessionKey = jsonObject.getString("key");
                String date = jsonObject.getString("date");

                PurchaseList purchaseList = new PurchaseList(sessionKey, date);

                purchaseList.setPurchaseArrayList(getPurchasesForSession(purchaseList.getSessionKey()));
                purchaseLists.add(purchaseList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return purchaseLists;
    }

    public ArrayList<Purchase> getPurchasesForSession (String session)
    {
        ArrayList<Purchase> purchases = new ArrayList<Purchase>();
        try {
            JSONArray response = new FetchJSONArray("http://" + Constants.SERVER_IP +"/purchasedItems/" + session).execute().get();

            for (int i=0;i<response.length();++i){
                JSONObject jsonObject = response.getJSONObject(i);

                String gtin = jsonObject.getString("GTIN");
                String productname = jsonObject.getString("Name");
                float price = (float)jsonObject.getDouble("Preis");

                purchases.add(new Purchase(productname,gtin,price,1));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchases;
    }


}
