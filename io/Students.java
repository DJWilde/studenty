package com.djwilde.studenciaki.io;

import com.djwilde.studenciaki.lib.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class Students implements Loadable {
    private static Students instance = new Students();
    private static String filename = "students.txt";

    private ObservableList<Student> students;
    private DateTimeFormatter formatter;

    private Students() {
        this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public static Students getInstance() {
        return instance;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
    }

    public void loadFromFile() throws IOException {
        students = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader reader = Files.newBufferedReader(path);
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                String[] linePieces = line.split(";");
                String name = linePieces[0];
                String genderStr = linePieces[1];
                String peselStr = linePieces[2];
                String dateStr = linePieces[3];
                String faculty = linePieces[4];
                String course = linePieces[5];
                String noOfRegisterStr = linePieces[6];
                String semesterStr = linePieces[7];
                String ectsStr = linePieces[8];

                LocalDate date = LocalDate.parse(dateStr, formatter);
                String gender = genderStr;
                long pesel = Long.parseLong(peselStr);
                int noOfRegister = Integer.parseInt(noOfRegisterStr);
                int semester = Integer.parseInt(semesterStr);
                int ects = Integer.parseInt(ectsStr);

                this.addStudent(new Student(name, gender, pesel, date, faculty, course, noOfRegister, semester, ects));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public void saveStudents() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter writer = Files.newBufferedWriter(path);

        try {
            Iterator<Student> it = students.iterator();
            while (it.hasNext()) {
                Student temp = it.next();
                writer.write(String.format("%s;%s;%d;%s;%s;%s;%d;%d;%d", temp.getName(),
                        temp.getGender(), temp.getPesel(), temp.getDateOfBirth().format(formatter),
                        temp.getFaculty(), temp.getCourse(), temp.getNoOfRegister(), temp.getSemester(), temp.getEcts()));
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
