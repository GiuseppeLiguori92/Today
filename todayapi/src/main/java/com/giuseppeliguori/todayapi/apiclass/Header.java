
package com.giuseppeliguori.todayapi.apiclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
