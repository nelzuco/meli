package com.meli.iplookup.service;

import com.meli.iplookup.dto.CountryInfoResultDTO;
import com.meli.iplookup.dto.CurrencyInfoDTO;
import com.meli.iplookup.dto.RateDTO;
import com.meli.iplookup.model.CountryInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CountryInfoResultServiceTest {


    @InjectMocks
    @Spy
    private CountryInfoResultService countryInfoResultService;

    @Mock
    private CountryInfoService countryInfoService;

    @Mock
    private CurrencyInfoService currencyInfoService;

    @Mock
    private CountryInfoResultDTO countryInfoResultDTO;

    @Mock
    private CountryInfo countryInfo;

    @Mock
    private CurrencyInfoDTO currencyInfoDTO;

    @Mock
    private RateDTO rateDTO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String[] hours =  {"10:10:10"};
        String currentDate = "02:14:24 (UTC), 03:14:24 (UTC+01:00)";
        String ipAddress = "83.44.196.93"; String countryName = "Spain";
        String countryCode = "ES";
        int distanceToBsAs = 10275;
        String[] languages = new String[]{"Español (es)"};
        String[] currencyCodes = new String[]{"EUR"};
        String[] timezones = new String[]{ "UTC","UTC+01:00"};
        //countryInfoResultDTO
        countryInfoResultDTO = new CountryInfoResultDTO();
        countryInfoResultDTO.ip = ipAddress;
        countryInfoResultDTO.languages = new String[]{"Español (es)"};
        countryInfoResultDTO.currentDate = currentDate;
        countryInfoResultDTO.hours = hours;
        countryInfoResultDTO.isoCode = "ES";
        countryInfoResultDTO.estimatedDistance = 10274;
        countryInfoResultDTO.country = "España";
        currencyInfoDTO = new CurrencyInfoDTO();
        rateDTO = new RateDTO();
        rateDTO.USD =  1.127949;
        rateDTO.setOtherField("EUR",1.0);
        currencyInfoDTO.rates = rateDTO;
        countryInfo = new CountryInfo(ipAddress, countryCode, "España (Spain)", languages, currencyCodes, timezones, distanceToBsAs);

    }

    @Test (expected = UnknownHostException.class)
    public void testGetCountryInfoResultException()throws  UnknownHostException{
        when(countryInfoService.getCountryInfo(any())).thenReturn(countryInfo);
        Mockito.doReturn(currencyInfoDTO).when(currencyInfoService).getCurrencyInfoDTO();
        when(countryInfoResultService.getCountryInfoResult(any())).thenThrow(UnknownHostException.class);
        countryInfoResultService.getCountryInfoResult("192.168.0.1");
    }

    @Test
    public void testGetCountryInfoResult() throws UnknownHostException {
        Mockito.doReturn(countryInfoResultDTO).when(countryInfoResultService).getCountryInfoResult("83.44.196.93");
        CountryInfoResultDTO result = countryInfoResultService.getCountryInfoResult("83.44.196.93");
        assertThat(result.country, samePropertyValuesAs(countryInfoResultDTO.country));
    }

}
