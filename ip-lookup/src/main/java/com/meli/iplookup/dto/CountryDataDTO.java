package com.meli.iplookup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDataDTO {

    public CurrencyCodeDTO[] currencies;
    public int[] latlng;
    public String[] timezones;
    public LanguageDTO[] languages;
    public String nativeName;
    public String name;
}
