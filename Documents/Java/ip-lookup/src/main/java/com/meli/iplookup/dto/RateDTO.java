package com.meli.iplookup.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class RateDTO {

    public Double USD;

    public Map<String, Double> customCurrencies = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Double> otherFields() {
        return customCurrencies;
    }

    @JsonAnySetter
    public void setOtherField(String key, Double value) {
        customCurrencies.put(key, value);
    }
}
