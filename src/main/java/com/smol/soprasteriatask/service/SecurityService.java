package com.smol.soprasteriatask.service;

import com.smol.soprasteriatask.config.SecurityConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

//------------------------------------------------------------------
// Some tools for making authenticated queries against external API
//------------------------------------------------------------------
public interface SecurityService {

    static HttpEntity<String> getStandardStringHttpEntity() {
        return new HttpEntity<>(createHeaders(SecurityConfig.getCurrentUsername(), SecurityConfig.getCurrentPassword()));
    }

    static HttpHeaders createHeaders(String username, String password) {
        String notEncoded = username + ":" + password;
        String encodedAuth = Base64.encodeBase64String(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + encodedAuth);
        return headers;
    }
}
