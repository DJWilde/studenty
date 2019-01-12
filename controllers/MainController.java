package com.djwilde.studenciaki.controllers;

import com.djwilde.studenciaki.Main;
import com.djwilde.studenciaki.io.Students;
import com.djwilde.studenciaki.lib.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class MainController {
    private List<Student> studentList;
    private List<String> faculties = FXCollections.observableArrayList();
    private List<String> courses = FXCollections.observableArrayList();
    @FXML
    private Label nameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label peselLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label facultyLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label noOfRegisterLabel;
    @FXML
    private Label semesterLabel;
    @FXML
    private Label ectsLabel;
    @FXML
    private Button addNewStudentButton;
    @FXML
    private Button editStudentButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private ListView<Student> studentNameListView;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
        studentNameListView.getSelectionModel().selectedItemProperty().addListener((observableValue, student, t1) -> {
            if (t1 != null) {
                Student temp = studentNameListView.getSelectionModel().getSelectedItem();
                nameLabel.setText(temp.getName());
                genderLabel.setText(temp.getGender());
                peselLabel.setText(Long.toString(temp.getPesel()));
                DateTimeFormatter df = DateTimeFormatter.ofPattern("d.MM.yyyy");
                dateLabel.setText(df.format(temp.getDateOfBirth()));
                facultyLabel.setText(temp.getFaculty());
                courseLabel.setText(temp.getCourse());
                noOfRegisterLabel.setText(Integer.toString(temp.getNoOfRegister()));
                semesterLabel.setText(Integer.toString(temp.getSemester()));
                ectsLabel.setText(Integer.toString(temp.getEcts()));
            }
        });
        studentNameListView.setItems(Students.getInstance().getStudents());
        studentNameListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentNameListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showAddStudentDialog() {
        Dialog<ButtonType> addNewStudentDialog = new Dialog<>();
        addNewStudentDialog.initOwner(mainBorderPane.getScene().getWindow());
        addNewStudentDialog.setTitle("Add New Student");
        addNewStudentDialog.setHeaderText("Add New Student");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("studentDialog.fxml"));
        try {
            addNewStudentDialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        addNewStudentDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        addNewStudentDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        StudentController controller = loader.getController();
        controller.populateFaculties();
        Optional<ButtonType> result = addNewStudentDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student temp = controller.processFields();
            if (temp == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(addNewStudentDialog.getOwner().getScene().getWindow());
                alert.setTitle("Error");
                alert.setContentText("One or more fields is empty. Please fill them in.");
                alert.showAndWait();
            }
            studentNameListView.getSelectionModel().select(temp);
        }
    }

    public void refreshStudent(Student student) {
        nameLabel.setText(student.getName());
        genderLabel.setText(student.getGender());
        peselLabel.setText(Long.toString(student.getPesel()));
        dateLabel.setText(student.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        facultyLabel.setText(student.getFaculty());
        courseLabel.setText(student.getCourse());
        noOfRegisterLabel.setText(Integer.toString(student.getNoOfRegister()));
        semesterLabel.setText(Integer.toString(student.getSemester()));
        ectsLabel.setText(Integer.toString(student.getEcts()));
    }

    @FXML
    public void showEditStudentDialog() {
        Student temp = studentNameListView.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No contact has been selected.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> editStudentDialog = new Dialog<>();
        editStudentDialog.initOwner(mainBorderPane.getScene().getWindow());
        editStudentDialog.setTitle("Edit Contact");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("studentDialog.fxml"));
        try {
            editStudentDialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        StudentController studentController = loader.getController();
        studentController.editStudent(temp);

        editStudentDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        editStudentDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = editStudentDialog.showAndWait();
        if (result != null && result.get() == ButtonType.OK) {
            studentController.updateStudent(temp);
            refreshStudent(temp);
        }
    }

    public void deleteStudent() {
        Student temp = studentNameListView.getSelectionModel().getSelectedItem();
        if (temp == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No contact has been selected.");
            alert.showAndWait();
            return;
        }

        Alert infoAlert = new Alert(Alert.AlertType.CONFIRMATION);
        infoAlert.setTitle("Delete Student?");
        infoAlert.setContentText("Are you sure you want to delete this student?");
        Optional<ButtonType> result = infoAlert.showAndWait();
        if (result != null && result.get() == ButtonType.OK) {
            Students.getInstance().deleteStudent(temp);
            if (!studentNameListView.getSelectionModel().isEmpty()) {
                studentNameListView.getSelectionModel().selectLast();
            } else {
                nameLabel.setText("");
                genderLabel.setText("");
                peselLabel.setText("");
                dateLabel.setText("");
                facultyLabel.setText("");
                courseLabel.setText("");
                noOfRegisterLabel.setText("");
                semesterLabel.setText("");
                courseLabel.setText("");
            }

        }
    }
}
