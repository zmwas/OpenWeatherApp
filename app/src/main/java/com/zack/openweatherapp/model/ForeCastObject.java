package com.zack.openweatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForeCastObject {
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
