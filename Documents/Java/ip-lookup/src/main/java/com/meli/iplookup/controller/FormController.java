package com.meli.iplookup.controller;


import com.meli.iplookup.dto.CountryInfoResultDTO;
import com.meli.iplookup.handler.ExternalApiErrorHandler;
import com.meli.iplookup.properties.UrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class FormController {
    @Autowired
    private UrlProperties urlProperties;

    @GetMapping("/ip-form")
    public String getIpForm() {
        return "ip-form";
    }

    @PostMapping(value = "/ip-result", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postIpResponseForm(@RequestPart("ip") String ip, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ExternalApiErrorHandler());
        String url = urlProperties.getLocal().replace("{$ip}", ip);
        try {
            model.addAttribute("cto", restTemplate.getForObject(url, CountryInfoResultDTO.class));
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute("message", "Please check the ip you provided");
            } else {
                model.addAttribute("message", "There were problems with the service");
            }
            model.addAttribute("ip", ip);

            return "ip-error";
        }

        return "ip-result";
    }

}
