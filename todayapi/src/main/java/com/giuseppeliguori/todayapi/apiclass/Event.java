
package com.giuseppeliguori.todayapi.apiclass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event implements Yearable, Textable {

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("links")
    @Expose
    private List<EventLink> links = null;

    @Override
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<EventLink> getLinks() {
        return links;
    }

    public void setLinks(List<EventLink> links) {
        this.links = links;
    }

}
