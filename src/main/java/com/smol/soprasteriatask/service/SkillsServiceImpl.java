
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
            // GET -> http://urlAPI/skills
            response = mRestTemplate.exchange(skillsUrl,
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), new ParameterizedTypeReference<List<SkillDTO>>() {
                    });
        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null; //return null if sth went wrong
        }

        return response.getBody();
    }

    //Returns true if the operation was successfull (status code was 201)
    @Override
    public boolean addNewSkill(SkillNewDTO skillNewDTO) {

        try {
            //making the entity with the SkillNewDTO we want to POST
            HttpEntity<SkillNewDTO> entity = new HttpEntity<>(skillNewDTO, SecurityService.createHeaders(
                    SecurityConfig.getCurrentUsername(), SecurityConfig.getCurrentPassword()
            ));
            // POST -> http://urlAPI/skills
            ResponseEntity<String> response = mRestTemplate.exchange(skillsUrl,
                    HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().value() != 201) {
                throw new HTTPException(response.getStatusCode().value());
            }
            return true; //return true if everything went well

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return false; //return false if sth went wrong
        }
    }


}
