package com.meli.iplookup.service;


import com.meli.iplookup.dto.CountryNameDTO;
import com.meli.iplookup.model.CountryData;
import com.meli.iplookup.model.CountryInfo;
import com.meli.iplookup.properties.UrlProperties;
import com.meli.iplookup.repository.CountryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CountryInfoService {

    @Autowired
    private CountryInfoRepository countryInfoRepository;

    @Autowired
    private UrlProperties urlProperties;

    @Autowired
    private CountryDataService countryDataService;

    @Autowired
    private RestTemplate restTemplate;

    public CountryNameDTO getCountryNameDTO(String ipAddress) {
        String url = urlProperties.getIpCountry().replace("{$ip}", ipAddress);
        return restTemplate.getForObject(url, CountryNameDTO.class);
    }

    @Cacheable(value = "ipAddr", key="#ipAddress")
    public CountryInfo getCountryInfo(String ipAddress) throws NoSuchElementException {
        Optional<CountryInfo> countryInfoOptional = countryInfoRepository.findById(ipAddress);
        if (countryInfoOptional.isPresent()) {
            return countryInfoOptional.get();
        }

        CountryNameDTO countryNameDTO = this.getCountryNameDTO(ipAddress);

        if (countryNameDTO.countryName.equals("")) {
            throw new NoSuchElementException();
        }

        String countryCode = countryNameDTO.countryCode;
        String countryName = countryNameDTO.countryName;
        CountryData countryData = countryDataService.getCountryData(countryName, countryCode);

        CountryInfo countryInfo = new CountryInfo(ipAddress, countryCode, countryData.getCountryName(), countryData.getLanguages(), countryData.getCurrencies(), countryData.getTimezones(), countryData.getDistanceToBsAs());
        countryInfoRepository.save(countryInfo);
        return countryInfo;
    }
}
