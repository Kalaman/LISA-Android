package com.kala.lisa;

/**
 * Created by Kalaman on 26.01.18.
 */

public class Purchase {
    String productName;
    String GTIN;
    float price;
    int amount;

    public Purchase(String productName,String GTIN,float price,int amount)
    {
        this.productName = productName;
        this.GTIN = GTIN;
        this.price = price;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;

    }

    public String getGTIN() {
        return GTIN;
    }

    public float getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
