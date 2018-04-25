package com.udacity.aseelalawadh.newsapp;

/**
 * Created by aseelalawadh on 23/04/2018.
 */

public class News {

    public final String section;
    public final String date;
    public final String newsDetails;
    public final String newsUrl;

    public News(String section, String date, String newsDetails, String newsUrl) {
        this.section = section;
        this.date = date;
        this.newsDetails = newsDetails;
        this.newsUrl = newsUrl;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getNewsDetails() {
        return newsDetails;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}
