package com.kala.lisa;

import java.util.ArrayList;

/**
 * Created by Kalaman on 26.01.18.
 */

public class PurchaseList {
    ArrayList<Purchase> purchaseArrayList;
    String sessionKey;
    String date;

    public PurchaseList(String sessionKey, String date) {
        purchaseArrayList = new ArrayList<>();
        this.date = date;
        this.sessionKey = sessionKey;
    }

    public void addPurchase(Purchase purchase) {
        purchaseArrayList.add(purchase);
    }

    public ArrayList<Purchase> getPurchaseArrayList() {
        return purchaseArrayList;
    }

    public String getDate() {
        return date;
    }

    public float getTotalPrice() {
        float totalPrice = 0;

        for(Purchase purchase : purchaseArrayList)
            totalPrice += purchase.getPrice();

        return totalPrice;
    }

    public void setPurchaseArrayList(ArrayList<Purchase> purchaseArrayList) {
        this.purchaseArrayList = purchaseArrayList;
    }

    public String getSessionKey() {
        return sessionKey;
    }
}
