package com.smol.soprasteriatask.service;


import com.smol.soprasteriatask.config.SecurityConfig;
import com.smol.soprasteriatask.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    //--------------------------------------------------------------------------------------
    // Service for: /findUser & /findUserResults
    //--------------------------------------------------------------------------------------
    @Override
    public UserCreatedDTO findUserById(String id) {

        ResponseEntity<UserCreatedDTO> response = null;
        try {
            //GET -> http://apiAddr/users/{id}
            response = mRestTemplate.exchange(userUrl + "/" + id,
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), UserCreatedDTO.class);

            if (response.getStatusCode().value() != 200)
                throw new HTTPException(response.getStatusCode().value());

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null; //return null if sth went wrong;
        }

        //return UserCreatedDTO object
        return response.getBody();
    }

    //--------------------------------------------------------------------------------------------
    // Services for adding Skill to the user
    // NOTE: we use hard-coded user id due to API limitations
    //--------------------------------------------------------------------------------------------

    @Override
    public List<SkillDTO> getCurrentUserSkills() {

        ResponseEntity<DetailsFullDTO> response = null;
        try {

            //GET -> http://apiURL/users/alldetails/{id}
            response = mRestTemplate.exchange(userUrl + "/alldetails/" + SecurityConfig.getCurrentUserId(),
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), DetailsFullDTO.class);

            if (response.getStatusCode().value() != 200)
                throw new HTTPException(response.getStatusCode().value());

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null; //return null if sth went wrong
        }

        return response.getBody().getUser().getSkills();
    }

    @Override
    public boolean addSkillToCurrentUser(int skillId) {

        //Creating an array of current user's skill's id's

        List<SkillDTO> currentUserSkills = getCurrentUserSkills();
        List<Integer> skillsIds = new ArrayList<>();

        try {
            //Filling the list of Id's
            currentUserSkills.forEach(s -> skillsIds.add(s.getId()));

            //Finding to the list the id of the skill we want to EXTRA ADD
            skillsIds.add(skillId);

            //Creating the DTO we have to pass
            SaveSkillsRequest saveSkillsRequest = new SaveSkillsRequest(skillsIds, SecurityConfig.getCurrentUserId());

            //Creating the entity with the SaveSkillRequest we want to POST
            HttpEntity<SaveSkillsRequest> entity = new HttpEntity<>(saveSkillsRequest, SecurityService.createHeaders(
                    SecurityConfig.getCurrentUsername(), SecurityConfig.getCurrentPassword()
            ));

            // PUT -> http://urlAPI/users/skills
            ResponseEntity<String> response = mRestTemplate.exchange(userUrl + "/skills",
                    HttpMethod.PUT, entity, String.class);
            if (response.getStatusCode().value() != 201) {
                throw new HTTPException(response.getStatusCode().value());
            }
            return true; //return true if everything went well

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return false; //return false if sth went wrong
        }

    }

    //----------------------------------------------------------------------------------
    // Services for /userInfo
    // NOTE: we use hard-coded user id due to API limitations
    //--------------------------------------------------------------------------------------------

    @Override
    public DetailsNewDTO getCurrentUserDetails() {

        ResponseEntity<DetailsDTO> response = null;
        try {

            //GET -> http://apiURL/users/details/{id}
            response = mRestTemplate.exchange(userUrl + "/details/" + SecurityConfig.getCurrentUserId(),
                    HttpMethod.GET, SecurityService.getStandardStringHttpEntity(), DetailsDTO.class);

            if (response.getStatusCode().value() != 200)
                throw new HTTPException(response.getStatusCode().value());

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return null; //return null if sth went wrong
        }

        //mapping DetailsDTO -> DetailsNewDTO
        return modelMapper.map(response.getBody(), DetailsNewDTO.class);
    }

    @Override
    public boolean saveCurrentUserDetails(DetailsNewDTO details) {

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
            return true; //return true if everything went well

        } catch (Exception eek) {
            System.out.println("** Exception: " + eek.getMessage());
            return false; //return false if sth wen wrong
        }
    }
}
