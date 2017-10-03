
package com.giuseppeliguori.todayapi.apiclass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Birth {

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
    private List<BirthLink> links = null;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BirthLink> getLinks() {
        return links;
    }

    public void setLinks(List<BirthLink> links) {
        this.links = links;
    }

}
