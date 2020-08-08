package com.meli.iplookup.service;


import com.meli.iplookup.dto.CountryInfoResultDTO;
import com.meli.iplookup.dto.CurrencyInfoDTO;
import com.meli.iplookup.model.CountryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Service
public class CountryInfoResultService {

    @Autowired
    private CountryInfoService countryInfoService;

    @Autowired
    private CurrencyInfoService currencyInfoService;

    public CountryInfoResultDTO getCountryInfoResult(String ipAddress) throws UnknownHostException {
        CountryInfo countryInfo = countryInfoService.getCountryInfo(ipAddress);

        String[] currencies = countryInfo.getCurrencyCodes();
        String[] timezones = countryInfo.getTimezones();
        String[] currencyInfo = new String[currencies.length];
        CurrencyInfoDTO currencyInfoDTO;

        for (int i = 0; i < currencies.length; i++) {
            switch (currencies[i]) {
                case "USD":
                    currencyInfo[i] = currencies[i];
                    break;
                case "EUR":
                    currencyInfoDTO = currencyInfoService.getCurrencyInfoDTO();
                    currencyInfo[i] = currencies[i] + " (1" + currencies[i] + " = " + currencyInfoDTO.rates.USD + " U$S)";
                    break;
                default:
                    currencyInfoDTO = currencyInfoService.getCurrencyInfoDTO(currencies[i]);
                    Double eurToUsdRate = currencyInfoDTO.rates.USD;
                    System.out.println(currencies[i]);
                    Double eurToCurrencyRate = currencyInfoDTO.rates.otherFields().get(currencies[i]);
                    Double currencyToUsdRate = eurToCurrencyRate / eurToUsdRate;

                    currencyInfo[i] = currencies[i] + " (1" + currencies[i] + " = " + currencyToUsdRate + " U$S)";
            }
        }

        CountryInfoResultDTO result = new CountryInfoResultDTO();
        result.ip=ipAddress;
        result.country = countryInfo.getCountryName();
        result.currencies = currencyInfo;
        result.estimatedDistance = countryInfo.getDistanceToBsAs();
        result.isoCode = countryInfo.getCountryCode();
        result.hours = this.getHours(timezones);
        result.currentDate = this.getCurrentTime(timezones[0]);
        result.languages = countryInfo.getLanguages();

        return result;
    }

    private String getCurrentTime(String timezone){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now(ZoneId.of(timezone)));
    }

    private String[] getHours(String[] timezones) {
        LocalTime now;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] hours = new String[timezones.length];

        for (int i = 0; i < timezones.length; i++) {
            now = LocalTime.now(ZoneId.of(timezones[i]));
            hours[i] = dtf.format(now) + " (" + timezones[i] + ")";
        }

        return hours;
    }
}
