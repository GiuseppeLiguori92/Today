
package com.giuseppeliguori.todayapi.apiclass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Death implements Yearable, Textable {

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("links")
    @Expose
    private List<DeathLink> links = null;

    @Override
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<DeathLink> getLinks() {
        return links;
    }

    public void setLinks(List<DeathLink> links) {
        this.links = links;
    }

}
