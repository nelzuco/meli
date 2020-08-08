package com.meli.iplookup.service;


import com.meli.iplookup.dto.CountryDataDTO;
import com.meli.iplookup.dto.CurrencyInfoDTO;
import com.meli.iplookup.dto.RateDTO;
import com.meli.iplookup.properties.UrlProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyInfoServiceTest {

    @InjectMocks
    @Spy
    private CurrencyInfoService currencyInfoService;

    @Mock
    private UrlProperties urlProperties;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyInfoDTO currencyInfoDTO;

    @Mock
    private RateDTO rateDTO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        currencyInfoDTO = new CurrencyInfoDTO();
        rateDTO = new RateDTO();
        rateDTO.USD =  1.127949;
        rateDTO.setOtherField("EUR",1.0);
        currencyInfoDTO.rates = rateDTO;

    }

    @Test(expected = UnknownHostException.class)
    public void testGetCurrencyInfoDTOExceptionWithParam()throws UnknownHostException{
        Mockito.doThrow(UnknownHostException.class).when(currencyInfoService).getCurrencyInfoDTO(any());

        currencyInfoService.getCurrencyInfoDTO("EUR");
    }

    @Test
    public void testGetCurrencyInfoDTOWithParam()throws UnknownHostException{
        Mockito.doReturn(currencyInfoDTO).when(currencyInfoService).getCurrencyInfoDTO(anyString());

        currencyInfoService.getCurrencyInfoDTO("EUR");
    }

    @Test (expected = UnknownHostException.class)
    public void testGetCurrencyInfoDTOException()throws UnknownHostException{
        Mockito.doThrow(UnknownHostException.class).when(currencyInfoService).getCurrencyInfoDTO();

        currencyInfoService.getCurrencyInfoDTO();
    }

    @Test (expected = HttpClientErrorException.class)
    public void testGetCurrencyInfoDTOExceptionHTTP() throws HttpClientErrorException, UnknownHostException {
        Mockito.doThrow(HttpClientErrorException.class).when(currencyInfoService).getCurrencyInfoDTO();

        currencyInfoService.getCurrencyInfoDTO();
    }

    @Test
    public void testGetCurrencyInfoDTO()throws UnknownHostException{
        Mockito.doReturn(currencyInfoDTO).when(currencyInfoService).getCurrencyInfoDTO();

        currencyInfoService.getCurrencyInfoDTO();
    }
}
