package com.meli.iplookup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyInfoDTO {

    public RateDTO rates;
}
