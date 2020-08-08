package com.meli.iplookup.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class CountryInfo implements Serializable {

    @Id
    private String ipAddress;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String countryName;

    @NotEmpty
    private String[] languages;

    @NotEmpty
    private String[] currencyCodes;

    @NotEmpty
    private String[] timezones;

    @NotNull
    private int distanceToBsAs;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(String[] currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public void setTimezones(String[] timezones) {
        this.timezones = timezones;
    }

    public int getDistanceToBsAs() {
        return distanceToBsAs;
    }

    public void setDistanceToBsAs(int distanceToBsAs) {
        this.distanceToBsAs = distanceToBsAs;
    }

    public CountryInfo(String ipAddress, @NotBlank String countryCode, @NotBlank String countryName, @NotEmpty String[] languages, @NotEmpty String[] currencyCodes, @NotEmpty String[] timezones, @NotNull int distanceToBsAs) {
        this.ipAddress = ipAddress;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.languages = languages;
        this.currencyCodes = currencyCodes;
        this.timezones = timezones;
        this.distanceToBsAs = distanceToBsAs;
    }

    public CountryInfo() {
    }
}
