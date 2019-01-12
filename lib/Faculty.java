package com.djwilde.studenciaki.lib;

import java.util.ArrayList;
import java.util.Iterator;

public class Faculty {
    private String name;
    private ArrayList<String> courses;

    public Faculty(String name, ArrayList<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String v : courses) {
            builder.append(v);
            if (courses.iterator().next() != null) {
                builder.append(",");
            }
        }
        return this.name + "=[" + builder.toString() + "]";
    }
}
