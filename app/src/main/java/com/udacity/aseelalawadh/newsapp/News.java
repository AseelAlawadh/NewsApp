package com.udacity.aseelalawadh.newsapp;

/**
 * Created by aseelalawadh on 23/04/2018.
 */

public class News {

    public final String section;
    public final String date;
    public final String newsDetails;
    public final String newsUrl;
    public final String pillarName;
    public final String authorName;


    public News(String section, String date, String newsDetails, String newsUrl, String pillarName, String authorName) {
        this.section = section;
        this.date = date;
        this.newsDetails = newsDetails;
        this.newsUrl = newsUrl;
        this.pillarName = pillarName;
        this.authorName = authorName ;
    }
/*
    public News(String section, String date, String newsDetails, String newsUrl, String pillarName) {
        this.section = section;
        this.date = date;
        this.newsDetails = newsDetails;
        this.newsUrl = newsUrl;
        this.pillarName = pillarName;
    }*/



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

    public String getPillarName() {
        return pillarName;
    }

    public String getAuthorName() {
        return authorName;
    }
}
