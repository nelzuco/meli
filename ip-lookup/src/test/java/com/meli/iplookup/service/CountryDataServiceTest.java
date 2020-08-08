package com.meli.iplookup.service;

import com.meli.iplookup.dto.CountryDataDTO;
import com.meli.iplookup.dto.CurrencyCodeDTO;
import com.meli.iplookup.dto.LanguageDTO;
import com.meli.iplookup.model.CountryData;
import com.meli.iplookup.properties.UrlProperties;
import com.meli.iplookup.repository.CountryDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CountryDataServiceTest {


    @InjectMocks
    @Spy
    private CountryDataService countryDataService;

    @Mock
    private CountryDataRepository countryDataRepository;

    @Mock
    private UrlProperties urlProperties;

    @Mock
    private RestTemplate restTemplate;

    private CountryDataDTO countryDataDTO;

    private CountryData countryData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String countryName = "España";
        String[] timezones = {"UTC","UTC+01:00"};
        String code = "EUR";
        int[] latlng = {40,-4};
        String iso639_1 = "es";
        String nativeName = "Español";
        String countryCode = "ES";
        int distanceToBsAs = 10274;

        //CountryDataDTO
        countryDataDTO = new CountryDataDTO();
        countryDataDTO.nativeName = countryName;
        countryDataDTO.timezones = timezones;
        CurrencyCodeDTO[] currencyCodeDTO = {new CurrencyCodeDTO()};
        currencyCodeDTO[0].code = code;
        countryDataDTO.currencies= currencyCodeDTO;
        countryDataDTO.latlng= latlng;
        LanguageDTO[] languageDTO = {new LanguageDTO()};
        languageDTO[0].iso639_1 = iso639_1;
        languageDTO[0].nativeName = nativeName;
        countryDataDTO.languages = languageDTO;
        countryDataDTO.name = "Spain";
        //CountryData
        countryData = new CountryData("España (Spain)",countryCode,latlng,timezones,new String[]{"Español (es)"},new String[]{"EUR"},distanceToBsAs);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCountryDataDTOException() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new CountryDataDTO[0]);
        when(urlProperties.getCountryInfo()).thenReturn("https://restcountries.eu/rest/v2/name/{$country}");

        countryDataService.getCountryDataDTO(countryDataDTO.name);
    }

    @Test
    public void testGetCountryDataDTO() {
        CountryDataDTO[] restResult = new CountryDataDTO[2];
        restResult[0] = new CountryDataDTO();
        restResult[0].nativeName = "Should not select this";
        restResult[1] = countryDataDTO;
        when(restTemplate.getForObject(anyString(), any())).thenReturn(restResult);
        when(urlProperties.getCountryInfo()).thenReturn("https://restcountries.eu/rest/v2/name/{$country}");

        CountryDataDTO result = countryDataService.getCountryDataDTO(countryDataDTO.name);

        assertThat(result, samePropertyValuesAs(countryDataDTO));
    }

    @Test
    public void testGetCountryData(){
        when(countryDataRepository.findById(anyString())).thenReturn(Optional.empty());
        when(urlProperties.getBsAsLat()).thenReturn(-34);
        when(urlProperties.getBsAsLng()).thenReturn(-64);
        Mockito.doReturn(countryDataDTO).when(countryDataService).getCountryDataDTO(anyString());

        CountryData result = countryDataService.getCountryData(countryDataDTO.name, "ES");

        assertThat(result, samePropertyValuesAs(countryData));
    }
}
