package com.example.amit;

public class ForecastModel {
    String date;
    String icon;
    String temp;
    String windspeed;
    String humidity;
    String conditionText;

    public ForecastModel(String date, String icon, String temp, String windspeed, String humidity,String conditionText) {
        this.date = date;
        this.icon = icon;
        this.temp = temp;
        this.windspeed = windspeed;
        this.humidity = humidity;
        this.conditionText=conditionText;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
