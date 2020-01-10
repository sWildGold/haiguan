package com.example.animal.ui.notifications;

public class HomeItem {
    private String title;
    private String content;
    private String short_content;

    HomeItem(String t, String c, String short_c) {
        title = t;
        content = c;
        short_content = short_c;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getShort_content() {
        return short_content;
    }
}
