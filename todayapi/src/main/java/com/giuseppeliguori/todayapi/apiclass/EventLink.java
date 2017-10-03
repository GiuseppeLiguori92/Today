
package com.giuseppeliguori.todayapi.apiclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventLink {

    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
