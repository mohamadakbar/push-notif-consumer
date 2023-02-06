package com.m2u.pushnotif.consumer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class JsonAPI {
    private static final Logger logger = LoggerFactory.getLogger(JsonAPI.class);

    public String callAPIJson(String json,String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        String response = restTemplate.postForObject(uri, entity, String.class);
        return response;

    }
    public String callAPIJsonObject(Object obj,String uri){
        String response = "";

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper mapper = new ObjectMapper();
            String mrJson = mapper.writeValueAsString(obj);
            logger.info("request "+mrJson);
            HttpEntity<String> entity = new HttpEntity<String>(mrJson, headers);
            response = restTemplate.postForObject(uri, entity,String.class);
            logger.info("response "+response);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return response;

    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 3000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        clientHttpRequestFactory.setReadTimeout(5000);
        return clientHttpRequestFactory;
    }

}
