package com.djwilde.studenciaki.io;

import com.djwilde.studenciaki.lib.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Faculties implements Loadable {
    private static Faculties instance = new Faculties();
    private static String filename = "faculties.txt";

    private ObservableMap<String, ArrayList<String>> faculties;

    private Faculties() {
    }

    public static Faculties getInstance() {
        return instance;
    }

    public ObservableMap<String, ArrayList<String>> getFaculties() {
        return faculties;
    }

    public Set<String> getKeys() {
        return faculties.keySet();
    }

    public ArrayList<String> getValues(String key) {
        ArrayList<String> tempValues = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> e : faculties.entrySet()) {
            if (e.getKey().equals(key)) {
                tempValues.addAll(e.getValue());
            }
        }
        return tempValues;
    }

    public void addFaculty(String key, ArrayList<String> values) {
        faculties.put(key, values);
    }

    public void loadFromFile() throws IOException {
        faculties = FXCollections.observableHashMap();
        Path path = Paths.get(filename);
        BufferedReader reader = Files.newBufferedReader(path);
        String input;
        String tempName;
        ArrayList<String> tempValues;
        try {
            while ((input = reader.readLine()) != null) {
                String[] inputSlices = input.split(";");
                tempName = inputSlices[0];
                if (faculties.containsKey(tempName)) {
                    tempValues = faculties.get(tempName);
                } else {
                    tempValues = new ArrayList<>();
                    for (int i = 1; i < inputSlices.length; i++) {
                        tempValues.add(inputSlices[i]);
                    }
                    addFaculty(tempName, tempValues);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
