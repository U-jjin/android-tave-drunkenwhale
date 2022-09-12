package org.androidtown.alcohol.Community;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommunityListviewItem {
    private String name;
    private String category;
    private String date;
    private String good;
    private  String title;
    private String comment;
    private String text;

    public CommunityListviewItem(String name, String category, String date, String good, String title, String comment, String text) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.good = good;
        this.title = title;
        this.comment = comment;
        this.text = text;
    }

    public CommunityListviewItem (){}


    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
