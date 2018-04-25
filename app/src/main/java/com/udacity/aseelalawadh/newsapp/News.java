package com.udacity.aseelalawadh.newsapp;

/**
 * Created by aseelalawadh on 23/04/2018.
 */

public class News {

    public final String section ;
    public final String date;
    public final String newsDetails;

    public News(String section, String date, String newsDetails) {
        this.section = section;
        this.date = date;
        this.newsDetails = newsDetails;
    }

//    public News(String id, String newsDetails) {
//        this.id = id;
//        this.newsDetails = newsDetails;
//    }


    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getNewsDetails() {
        return newsDetails;
    }
}
