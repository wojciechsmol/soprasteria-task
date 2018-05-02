
package com.smol.soprasteriatask.service;

import com.smol.soprasteriatask.config.SecurityConfig;
import com.smol.soprasteriatask.model.SkillDTO;
import com.smol.soprasteriatask.model.SkillNewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.List;

@Service
public class SkillsServiceImpl implements SkillsService {

    @Autowired
    RestTemplate mRestTemplate;

    @Value("${url.skills}")
    private String skillsUrl;

    @Override
    public List<SkillDTO> getAllSkills() {

        ResponseEntity<List<SkillDTO>> response = null;
        try {
            response = mRestTemplate.exchange(skillsUrl,
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), new ParameterizedTypeReference<List<SkillDTO>>() {});
        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null;
        }

        //return List<SkillDTO>
        return response.getBody();
    }

    @Override
    public void addNewSkill(SkillNewDTO skillNewDTO) {

        try {
            //making the entity with the SkillNewDTO we want to POST
            HttpEntity<SkillNewDTO> entity = new HttpEntity<>(skillNewDTO, SecurityService.createHeaders(
                    SecurityConfig.getCurrentUsername(), SecurityConfig.getCurrentPassword()
            ));
            ResponseEntity<String> response = mRestTemplate.exchange(skillsUrl,
                    HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().value() != 201) {
                throw new HTTPException(response.getStatusCode().value());
            }

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
        }
    }


}
