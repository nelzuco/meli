package com.meli.iplookup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageDTO {

    public String iso639_1;
    public String nativeName;
}
