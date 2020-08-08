package com.meli.iplookup.controller;


import com.google.common.net.InetAddresses;
import com.meli.iplookup.dto.CountryInfoResultDTO;
import com.meli.iplookup.dto.ErrorResponseDTO;
import com.meli.iplookup.service.CountryInfoResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.UnknownHostException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("ip-info")
public class IpController {


    @Autowired
    private CountryInfoResultService countryInfoResultService;


    @GetMapping(value = "/{ip}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCountryInfo(@PathVariable String ip) throws UnknownHostException {

        if (!InetAddresses.isInetAddress(ip)) {
            ErrorResponseDTO errorResp = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),"Invalid IP");
            throw new NoSuchElementException();
        }
        
        CountryInfoResultDTO result = null;

        result = countryInfoResultService.getCountryInfoResult(ip);
        
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="The country/ip you provided is invalid")
    public void noSuchElement() {}

    @ExceptionHandler({UnknownHostException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="There was a problem with the connections")
    public void unknownHost() {}

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="The service is temporary unavailable")
    public void clientError() {}

}