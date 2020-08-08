package com.meli.iplookup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryNameDTO {

    public String countryCode;

    public String countryName;
}
