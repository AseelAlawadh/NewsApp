package com.udacity.aseelalawadh.newsapp;

/**
 * Created by aseelalawadh on 23/04/2018.
 */

public class News {

    public String section;
    public String date;
    public String newsDetails;
    public String newsUrl;
    public String pillarName;
    public String authorName;

    public News(String section, String date, String newsDetails, String newsUrl, String pillarName, String authorName) {
        this.section = section;
        this.date = date;
        this.newsDetails = newsDetails;
        this.newsUrl = newsUrl;
        this.pillarName = pillarName;
        this.authorName = authorName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(String newsDetails) {
        this.newsDetails = newsDetails;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
