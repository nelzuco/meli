package com.meli.iplookup.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
public class UrlProperties {

    private String ipCountry;
    private String countryInfo;
    private String currencyInfo;
    private Integer bsAsLat;
    private Integer bsAsLng;
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getIpCountry() {
        return ipCountry;
    }

    public void setIpCountry(String ipCountry) {
        this.ipCountry = ipCountry;
    }

    public String getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(String countryInfo) {
        this.countryInfo = countryInfo;
    }

    public String getCurrencyInfo() {
        return currencyInfo;
    }

    public void setCurrencyInfo(String currencyInfo) {
        this.currencyInfo = currencyInfo;
    }

    public Integer getBsAsLat() {
        return bsAsLat;
    }

    public void setBsAsLat(Integer bsAsLat) {
        this.bsAsLat = bsAsLat;
    }

    public Integer getBsAsLng() {
        return bsAsLng;
    }

    public void setBsAsLng(Integer bsAsLng) {
        this.bsAsLng = bsAsLng;
    }
}
