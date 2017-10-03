
package com.giuseppeliguori.todayapi.apiclass;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Events")
    @Expose
    private List<Event> events = null;
    @SerializedName("Births")
    @Expose
    private List<Birth> births = null;
    @SerializedName("Deaths")
    @Expose
    private List<Death> deaths = null;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Birth> getBirths() {
        return births;
    }

    public void setBirths(List<Birth> births) {
        this.births = births;
    }

    public List<Death> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<Death> deaths) {
        this.deaths = deaths;
    }

}
