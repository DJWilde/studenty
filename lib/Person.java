package com.djwilde.studenciaki.lib;

import java.time.LocalDate;

public abstract class Person {
    private String name;
    private String gender;
    private long pesel;
    private LocalDate dateOfBirth;

    public Person(String name, String gender, long pesel, LocalDate dateOfBirth) {
        this.name = name;
        this.gender = gender;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
