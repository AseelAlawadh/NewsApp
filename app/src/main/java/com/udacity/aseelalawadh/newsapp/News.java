package com.udacity.aseelalawadh.newsapp;

/**
 * Created by aseelalawadh on 23/04/2018.
 */

public class News {

    public String section;
    public String date;
    public String newsDetails;
    public String newsUrl;
    public String authorName;

    public News(String section, String date, String newsDetails, String newsUrl, String pillarName, String authorName) {
        this.section = section;
        this.date = date;
        this.newsDetails = newsDetails;
        this.newsUrl = newsUrl;
        this.authorName = authorName;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
