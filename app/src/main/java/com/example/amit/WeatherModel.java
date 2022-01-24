package com.example.amit;

public class WeatherModel {
    private String icon;
    private String date;
    private String conditionText;
    private String maxTemp;
    private String minTemp;

    public WeatherModel(String icon, String date, String conditionText, String maxTemp, String minTemp) {
        this.icon = icon;
        this.date = date;
        this.conditionText = conditionText;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
}
