package com.meli.iplookup.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import com.meli.iplookup.dto.CountryDataDTO;
import com.meli.iplookup.model.CountryData;
import com.meli.iplookup.properties.UrlProperties;
import com.meli.iplookup.repository.CountryDataRepository;
import org.springframework.cache.annotation.Cacheable;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CountryDataService {

    @Autowired
    private CountryDataRepository countryDataRepository;

    @Autowired
    private UrlProperties urlProperties;

    @Autowired
    private RestTemplate restTemplate;


    public CountryDataDTO getCountryDataDTO(String countryName) throws NoSuchElementException {
        String url = urlProperties.getCountryInfo().replace("{$country}", countryName);

        CountryDataDTO[] dtos = restTemplate.getForObject(url, CountryDataDTO[].class);

        if (dtos.length == 0) {
            throw new NoSuchElementException();
        }

        if (dtos.length > 1)
            for (CountryDataDTO dto:dtos)
                if (dto.nativeName.equals(countryName))
                    return dto;

        return dtos[0];
    }

    @Cacheable(value = "countryData", key = "#countryName")
    public CountryData getCountryData(String countryName, String countryCode) {
        Optional<CountryData> countryDataOptional = countryDataRepository.findById(countryName);

        if(countryDataOptional.isPresent())
            return countryDataOptional.get();

        CountryData countryData = getUrlCountryData(countryName, countryCode);

        countryDataRepository.save(countryData);

        return countryData;
    }

    private CountryData getUrlCountryData(String countryName, String countryCode) {
        CountryDataDTO dto = this.getCountryDataDTO(countryName);
        Point receivedCountryPoint = Point.at(Coordinate.fromDegrees(dto.latlng[0]), Coordinate.fromDegrees(dto.latlng[1]));
        Point bsAsPoint = Point.at(Coordinate.fromDegrees(urlProperties.getBsAsLat()), Coordinate.fromDegrees(urlProperties.getBsAsLng()));
        int distanceToBsAs = (int)EarthCalc.gcdDistance(bsAsPoint, receivedCountryPoint) / 1000; // m to km
        String[] currencies = new String[dto.currencies.length];
        String[] languages = new String[dto.languages.length];

        for (int i = 0; i < dto.currencies.length; i++){
            currencies[i] = dto.currencies[i].code;
        }
        for (int i = 0; i < dto.languages.length; i++){
            languages[i] = dto.languages[i].nativeName + " (" + dto.languages[i].iso639_1 +")" ;
        }

        return new CountryData( dto.nativeName + " (" + dto.name + ")", countryCode, dto.latlng, dto.timezones, languages, currencies, distanceToBsAs);
    }
}
