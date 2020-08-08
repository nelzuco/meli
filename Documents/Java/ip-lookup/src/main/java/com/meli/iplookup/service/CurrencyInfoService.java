package com.meli.iplookup.service;

import com.meli.iplookup.dto.CurrencyInfoDTO;
import com.meli.iplookup.properties.UrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;

@Service
public class CurrencyInfoService {


    @Autowired
    private UrlProperties urlProperties;

    @Autowired
    private RestTemplate restTemplate;


    public CurrencyInfoDTO getCurrencyInfoDTO(String currencyCode) throws UnknownHostException {
        String url = urlProperties.getCurrencyInfo().replace("{$currencyCode}", "," + currencyCode);
        return restTemplate.getForObject(url, CurrencyInfoDTO.class);
    }

    public CurrencyInfoDTO getCurrencyInfoDTO() throws UnknownHostException, HttpClientErrorException {
        String url = urlProperties.getCurrencyInfo().replace("{$currencyCode}", "");
        return restTemplate.getForObject(url, CurrencyInfoDTO.class);
    }
}
