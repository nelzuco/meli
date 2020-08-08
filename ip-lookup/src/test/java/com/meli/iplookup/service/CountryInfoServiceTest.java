package com.meli.iplookup.service;

import com.meli.iplookup.dto.CountryDataDTO;
import com.meli.iplookup.dto.CountryNameDTO;
import com.meli.iplookup.model.CountryData;
import com.meli.iplookup.model.CountryInfo;
import com.meli.iplookup.properties.UrlProperties;
import com.meli.iplookup.repository.CountryInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryInfoServiceTest {

    @InjectMocks
    @Spy
    private CountryInfoService countryInfoService = new CountryInfoService();

    @Mock
    private CountryDataService countryDataService;

    @Mock
    private CountryInfoRepository countryInfoRepository;

    @Mock
    private UrlProperties urlProperties;

    @Mock
    private RestTemplate restTemplate;

    private CountryInfo countryInfo;

    private CountryNameDTO countryNameDTO;

    private CountryData countryData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String countryName = "Spain";
        String countryCode = "ES";
        String ipAddress = "83.44.196.93";
        int distanceToBsAs = 10275;
        String[] languages = new String[]{"Español (es)"};
        String[] currencyCodes = new String[]{"EUR"};
        String[] timezones = new String[]{""};
        countryNameDTO = new CountryNameDTO();
        countryNameDTO.countryName = countryName;
        countryNameDTO.countryCode = countryCode;

        //CountryData
        int[] latlng = {40,-4};
        countryData = new CountryData("España (Spain)",countryCode,latlng,timezones,new String[]{"Español (es)"},new String[]{"EUR"},distanceToBsAs);


        countryInfo = new CountryInfo(ipAddress, countryCode, "España (Spain)", languages, currencyCodes, timezones, distanceToBsAs);
    }


    @Test(expected = NoSuchElementException.class)
    public void testGetCountryInfoResponseStatusException(){
        when(countryInfoRepository.findById(anyString())).thenReturn(Optional.empty());
        countryNameDTO.countryName = "";
        Mockito.doReturn(countryNameDTO).when(countryInfoService).getCountryNameDTO(anyString());

        countryInfoService.getCountryInfo("192.168.0.1");
    }



    @Test
    public void testGetCountryInfo() {
        when(countryInfoRepository.findById(anyString())).thenReturn(Optional.empty());
        Mockito.doReturn(countryNameDTO).when(countryInfoService).getCountryNameDTO(anyString());
        Mockito.doReturn(countryData).when(countryDataService).getCountryData(anyString(),anyString());

        CountryInfo result =  countryInfoService.getCountryInfo("83.44.196.93");
        assertThat(result, samePropertyValuesAs(countryInfo));
    }

    @Test
    public void testGetCountryNameDTO(){

        CountryNameDTO restResult = countryNameDTO;
        when(restTemplate.getForObject(anyString(), any())).thenReturn(restResult);
        when(urlProperties.getIpCountry()).thenReturn("https://api.ip2country.info/ip?{$ip}");

        CountryNameDTO result = countryInfoService.getCountryNameDTO("83.44.196.93");

        assertThat(result, samePropertyValuesAs(countryNameDTO));

    }
}
