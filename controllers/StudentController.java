package com.djwilde.studenciaki.controllers;

import com.djwilde.studenciaki.io.Faculties;
import com.djwilde.studenciaki.io.Students;
import com.djwilde.studenciaki.lib.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class StudentController {
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField peselField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> facultyComboBox;
    @FXML
    private ComboBox<String> courseComboBox;
    @FXML
    private TextField noOfRegisterField;
    @FXML
    private TextField semesterField;
    @FXML
    private TextField ectsField;

    public Student processFields() {
        String name = nameField.getText();
        String gender = genderComboBox.getSelectionModel().getSelectedItem();
        String pesel = peselField.getText();
        LocalDate date = datePicker.getValue();
        String faculty = facultyComboBox.getSelectionModel().getSelectedItem();
        String course = courseComboBox.getSelectionModel().getSelectedItem();
        String noOfRegister = noOfRegisterField.getText();
        String semester = semesterField.getText();
        String ects = ectsField.getText();

        if (name != null && gender != null && pesel != null && date != null && faculty != null && course != null &&
                noOfRegister != null && semester != null && ects != null) {
            Student temp = new Student(name, gender, Long.parseLong(pesel), date, faculty, course, Integer.parseInt(noOfRegister),
                    Integer.parseInt(semester), Integer.parseInt(ects));
            Students.getInstance().addStudent(temp);
            return temp;
        }
        return null;
    }

    public void editStudent(Student student) {
        nameField.setText(student.getName());
        genderComboBox.getSelectionModel().select(student.getGender());
        peselField.setText(Long.toString(student.getPesel()));
        datePicker.setValue(student.getDateOfBirth());
        facultyComboBox.getSelectionModel().select(student.getFaculty());
        courseComboBox.getSelectionModel().select(student.getCourse());
        noOfRegisterField.setText(Integer.toString(student.getNoOfRegister()));
        semesterField.setText(Integer.toString(student.getSemester()));
        ectsField.setText(Integer.toString(student.getEcts()));
    }

    public void updateStudent(Student student) {
        student.setName(nameField.getText());
        student.setGender(genderComboBox.getSelectionModel().getSelectedItem());
        student.setPesel(Long.parseLong(peselField.getText()));
        student.setDateOfBirth(datePicker.getValue());
        student.setFaculty(facultyComboBox.getSelectionModel().getSelectedItem());
        student.setFaculty(courseComboBox.getSelectionModel().getSelectedItem());
        student.setNoOfRegister(Integer.parseInt(noOfRegisterField.getText()));
        student.setSemester(Integer.parseInt(semesterField.getText()));
        student.setEcts(Integer.parseInt(ectsField.getText()));
    }

    public void populateFaculties() {
        facultyComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            ArrayList<String> tempValues = new ArrayList<>();
            for (String k : Faculties.getInstance().getKeys()) {
                if (facultyComboBox.getSelectionModel().getSelectedItem().equals(k)) {
                    tempValues.clear();
                    tempValues = Faculties.getInstance().getValues(k);
                    courseComboBox.getItems().setAll(FXCollections.observableArrayList(tempValues));
                    courseComboBox.getSelectionModel().select(0);
                }
            }
        });
    }
}
