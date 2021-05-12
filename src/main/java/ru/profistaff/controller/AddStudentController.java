package ru.profistaff.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import ru.profistaff.model.Student;
import ru.profistaff.service.StudentService;
import ru.profistaff.service.StudentServiceImpl;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    private final StudentService service;

    @FXML
    public Button addStudentBtn;

    @FXML
    public TextField firstNameTF;

    @FXML
    public TextField surnameTF;

    @FXML
    public TextField patronymicTF;

    @FXML
    public DatePicker dateOfBirthDP;

    @FXML
    public TextField groupTF;

    @FXML
    private TextField findByIdTF;

    @FXML
    private TableColumn<Student, String> idColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> surnameColumn;

    @FXML
    private TableColumn<Student, String> patronymicColumn;

    @FXML
    private TableColumn<Student, Date> dateOfBirthColumn;

    @FXML
    private TableColumn<Student, String> groupColumn;

    @FXML
    private TableColumn<Student, String> actionColumn;

    @FXML
    public TableView<Student> studentsTbV;

    public AddStudentController() {
        service = new StudentServiceImpl();
    }

    @FXML
    public void addStudentAction(ActionEvent event) {
        String firstName = firstNameTF.getText();
        String surname = surnameTF.getText();
        String patronymic = patronymicTF.getText();
        String group = groupTF.getText();
        Date date = Date.valueOf(dateOfBirthDP.getValue());

        if (firstName.isEmpty()
                || surname.isEmpty()
                || patronymic.isEmpty()
                || group.isEmpty()
                || dateOfBirthDP.getEditor().getText().isEmpty()) {
            return;
        }
        Student student = new Student(firstName, surname, patronymic, group, date);

        service.save(student);
        refreshTableView();

        clearFields();
    }

    @FXML
    public void findById(ActionEvent actionEvent) {
        findByIdTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                findByIdTF.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    public void deleteByIdBtn(ActionEvent actionEvent) {
        if (!findByIdTF.getText().isEmpty()) {
            Long id = Long.valueOf(findByIdTF.getText());
            Student student = service.findById(id);
            if (student == null) {

            } else {
                service.delete(student);
                refreshTableView();
            }
            findByIdTF.clear();
        }
    }

    private void clearFields() {
        firstNameTF.clear();
        surnameTF.clear();
        patronymicTF.clear();
        groupTF.clear();
        dateOfBirthDP.getEditor().clear();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getId())));
        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        surnameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSurname()));
        patronymicColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPatronymic()));
        dateOfBirthColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDate()));
        groupColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGroup()));

        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFactory
                = new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Student, String> param) {
                        final TableCell<Student, String> cell = new TableCell<Student, String>() {

                            final Button removeBtn = new Button("Удалить");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                        removeBtn.setOnAction(event -> {
                                            Student student = studentsTbV.getSelectionModel().getSelectedItem();
                                            service.delete(student);
                                            refreshTableView();
                                        });
                                    setGraphic(removeBtn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        actionColumn.setCellFactory(cellFactory);

        refreshTableView();
    }

    public void refreshTableView() {
        List<Student> students = service.findAll();
        studentsTbV.setItems(FXCollections.observableArrayList(students));
    }
}
