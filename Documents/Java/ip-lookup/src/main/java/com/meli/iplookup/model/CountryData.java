package com.meli.iplookup.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class CountryData implements Serializable {

    @Id
    private String countryName;

    @NotEmpty
    private String countryCode;

    @NotEmpty
    private int[] latlng;

    @NotEmpty
    private String[] timezones;

    @NotEmpty
    private String[] languages;

    @NotEmpty
    private String[] currencies;

    @NotNull
    private int distanceToBsAs;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int[] getLatlng() {
        return latlng;
    }

    public void setLatlng(int[] latlng) {
        this.latlng = latlng;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public void setTimezones(String[] timezones) {
        this.timezones = timezones;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String[] currencies) {
        this.currencies = currencies;
    }

    public int getDistanceToBsAs() {
        return distanceToBsAs;
    }

    public void setDistanceToBsAs(int distanceToBsAs) {
        this.distanceToBsAs = distanceToBsAs;
    }

    public CountryData(String countryName, @NotEmpty String countryCode, @NotEmpty int[] latlng, @NotEmpty String[] timezones, @NotEmpty String[] languages, @NotEmpty String[] currencies, @NotNull int distanceToBsAs) {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.latlng = latlng;
        this.timezones = timezones;
        this.languages = languages;
        this.currencies = currencies;
        this.distanceToBsAs = distanceToBsAs;
    }

    public CountryData() {
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof CountryData))
            return false;

        CountryData countryData = (CountryData) other;

        if (countryData.countryCode != countryCode || countryData.countryName != countryName)
            return false;

        return true;
    }
}
