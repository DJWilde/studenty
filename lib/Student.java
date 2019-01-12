package com.djwilde.studenciaki.lib;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student extends Person {
    private String faculty;
    private String course;
    private int noOfRegister;
    private int semester;
    private int ects;

    public Student(String name, String gender, long pesel, LocalDate dateOfBirth, String faculty, String course,
                   int noOfRegister, int semester, int ects) {
        super(name, gender, pesel, dateOfBirth);
        this.faculty = faculty;
        this.course = course;
        this.noOfRegister = noOfRegister;
        this.semester = semester;
        this.ects = ects;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getNoOfRegister() {
        return noOfRegister;
    }

    public void setNoOfRegister(int noOfRegister) {
        this.noOfRegister = noOfRegister;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
