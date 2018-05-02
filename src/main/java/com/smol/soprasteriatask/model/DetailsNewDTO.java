package com.smol.soprasteriatask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsNewDTO {

    private String fieldOfStudy;
    private String firstName;
    private String lastName;
    private String university;
    private int yearOfStudy;

    public DetailsNewDTO(String fieldOfStudy, String firstName, String lastName, String university, int yearOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
        this.yearOfStudy = yearOfStudy;
    }

    public DetailsNewDTO() {
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUniversity() {
        return university;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public String toString() {
        return "DetailsNewDTO{" +
                "fieldOfStudy='" + fieldOfStudy + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", university='" + university + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                '}';
    }
}
