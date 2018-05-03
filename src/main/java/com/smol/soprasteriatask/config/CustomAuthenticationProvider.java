package com.smol.soprasteriatask.config;

import com.smol.soprasteriatask.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    RestTemplate mRestTemplate;

    @Value("${url.basic}")
    private String basicUrl;

    //Own Authenticate procedure
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (shouldAuthenticateAgainstAPI(name, password)) {

            //setting current password and username variables, because then it won't be possible
            // to retrieve the password
            SecurityConfig.setCurrentPassword(password);
            SecurityConfig.setCurrentUsername(name);

            return new UsernamePasswordAuthenticationToken(
                    name, password, new ArrayList<>());
        } else {
            return null;
        }
    }

    //Use GET method with credentials set during Sign In to determine if the user
    // can be authenticated against external API

    private boolean shouldAuthenticateAgainstAPI(String user, String password) {

        ResponseEntity<String> response = null;
        try {
            HttpHeaders headers = SecurityService.createHeaders(user, password);
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            response = mRestTemplate.exchange(basicUrl + "/skills", HttpMethod.GET, entity, String.class);
        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return false;
        }

        //return true if authenticated
        return response.getStatusCode().value() != 401;

    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
