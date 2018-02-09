package com.kala.lisa;

/**
 * Created by Kalaman on 01.02.18.
 */

public class Ad {
    private String title;
    private String description;

    public Ad(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
