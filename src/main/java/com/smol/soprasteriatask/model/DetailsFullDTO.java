package com.smol.soprasteriatask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsFullDTO {

    private String fieldOfStudy;
    private String firstName;
    private String id;
    private String lastName;
    private String university;
    private UserFullDTO user;
    private int yearOfStudy;


    public DetailsFullDTO(String fieldOfStudy, String firstName, String id, String lastName, String university, UserFullDTO user, int yearOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.university = university;
        this.user = user;
        this.yearOfStudy = yearOfStudy;
    }

    public DetailsFullDTO() {
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUniversity() {
        return university;
    }

    public UserFullDTO getUser() {
        return user;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setUser(UserFullDTO user) {
        this.user = user;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public String toString() {
        return "DetailsFullDTO{" +
                "fieldOfStudy='" + fieldOfStudy + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", university='" + university + '\'' +
                ", user=" + user +
                ", yearOfStudy=" + yearOfStudy +
                '}';
    }
}
