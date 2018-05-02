package com.smol.soprasteriatask.service;


import com.smol.soprasteriatask.config.SecurityConfig;
import com.smol.soprasteriatask.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RestTemplate mRestTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Value("${url.user}")
    private String userUrl;

    @Override
    public UserCreatedDTO findUserById(String id) {

        ResponseEntity<UserCreatedDTO> response = null;
        try {
            //making GET query to:  http://apiAddr/users/{id}
            response = mRestTemplate.exchange(userUrl + "/" + id,
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), UserCreatedDTO.class);

            if (response.getStatusCode().value() != 200)
                return null;

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null;
        }

        //return UserCreatedDTO object
        return response.getBody();
    }

    @Override
    public List<SkillDTO> getCurrentUserSkills() {

        ResponseEntity<DetailsFullDTO> response = null;
        try {

            //GET query to http://apiURL/users/alldetails/{id}
            response = mRestTemplate.exchange(userUrl + "/alldetails/" + SecurityConfig.getCurrentUserId(),
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), DetailsFullDTO.class);

            if (response.getStatusCode().value() != 200)
                return null;

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null;
        }

        //return List<SkillDTO> from DetailsFullDTO -> UserFullDTO -> skills
        return response.getBody().getUser().getSkills();
    }

    @Override
    public void addSkillToCurrentUser(int skillId) {

        //below we create array of current user's skill's id's

        List<SkillDTO> currentUserSkills = getCurrentUserSkills();
        List<Integer> skillsIds = new ArrayList<>();

        try {
            for (SkillDTO skill : currentUserSkills) {
                skillsIds.add(skill.getId());
            }
            //adding to the list the id of the skill we want to EXTRA ADD
            skillsIds.add(skillId);

            //making the DTO we have to pass
            SaveSkillsRequest saveSkillsRequest = new SaveSkillsRequest(skillsIds, SecurityConfig.getCurrentUserId());

            //making the entity with the SaveSkillRequest we want to POST
            HttpEntity<SaveSkillsRequest> entity = new HttpEntity<>(saveSkillsRequest, SecurityService.createHeaders(
                    SecurityConfig.getCurrentUsername(), SecurityConfig.getCurrentPassword()
            ));

            // PUT -> http://urlAPI/users/skills
            ResponseEntity<String> response = mRestTemplate.exchange(userUrl + "/skills",
                    HttpMethod.PUT, entity, String.class);
            if (response.getStatusCode().value() != 201) {
                throw new HTTPException(response.getStatusCode().value());
            }

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
        }

    }

    @Override
    public DetailsNewDTO getCurrentUserDetails() {

        ResponseEntity<DetailsDTO> response = null;
        try {

            //GET -> http://apiURL/users/details/{id}
            response = mRestTemplate.exchange(userUrl + "/details/" + SecurityConfig.getCurrentUserId(),
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), DetailsDTO.class);

            if (response.getStatusCode().value() != 200)
                return null;

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null;
        }

        //mapping DetailsDTO -> DetailsNewDTO
        return modelMapper.map(response.getBody(), DetailsNewDTO.class);
    }

    @Override
    public void saveCurrentUserDetails(DetailsNewDTO details) {

        try {
            //making the entity with details
            HttpEntity<DetailsNewDTO> entity = new HttpEntity<>(details, SecurityService.createHeaders(
                    SecurityConfig.getCurrentUsername(), SecurityConfig.getCurrentPassword()
            ));
            //PUT -> http://apiURL/users/details
            ResponseEntity<String> response = mRestTemplate.exchange(userUrl + "/details",
                    HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode().value() != 200 || response.getStatusCode().value() != 201) {
                throw new HTTPException(response.getStatusCode().value());
            }

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
        }
    }
}
