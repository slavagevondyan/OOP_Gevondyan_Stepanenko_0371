package com.example.ais_clinic;


import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    ObservableList<Item> tableData = FXCollections.observableArrayList();
    @FXML
    TableView<Item> table = new TableView<Item>(tableData);
    @FXML
    private TableColumn<Item, String> doctorColumn;

    @FXML
    private TableColumn<Item, String> patientColumn;

    @FXML
    private TableColumn<Item, String> examColumn;

    @FXML
    private TableColumn<Item, String> dateAndTimeColumn;
    @FXML
    private Button addReceptionButton;
    @FXML
    private Button deleteReceptionButton;

    @FXML
    private ComboBox doctorCombo;
    @FXML
    private ComboBox patientCombo;
    @FXML
    private ComboBox examCombo;
    @FXML
    private DatePicker receptionDate;
    @FXML
    private TextField receptionHour;
    @FXML
    private TextField receptionMin;
    @FXML
    private Label receptionLabel;


    //Doctor Tab
    @FXML
    private ListView doctorsList;
    @FXML
    private TextField doctorNameText;
    @FXML
    private TextField doctorEmailText;
    @FXML
    private Label doctorTabLabel;
    @FXML
    private Button addDoctorButton;
    @FXML
    private Button deleteDoctorButton;

    //Patient Tab
    @FXML
    private ListView patientsList;
    @FXML
    private TextField patientNameText;
    @FXML
    private TextField patientEmailText;
    @FXML
    private Label patientTabLabel;
    @FXML
    private Button addPatientButton;
    @FXML
    private Button deletePatientButton;

    //Examination Tab
    @FXML
    private ListView examinationsList;
    @FXML
    private TextField examinationNameText;
    @FXML
    private TextField examinationPriceText;
    @FXML
    private Label examinationTabLabel;
    @FXML
    private Button addExaminationButton;
    @FXML
    private Button deleteExaminationButton;

    private List<Human> doctors = new ArrayList<Human>();   //список врачей
    private List<Human> patients = new ArrayList<Human>();  //список пациентов
    private List<Exam> exams = new ArrayList<Exam>();   //список обследований


    Connection con;
    Statement stmt;

    private void addReception(Item it){
        try {
            String sql = "INSERT INTO receptions (doctor, patient, examination, r_date) " +
                    "VALUES ('" + it.getDoctor() + "', " +
                    "'" + it.getPatient() + "', '" + it.getExam() + "', '" + it.getDateAndTime() + "')";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void deleteReception(Item it){
        try {
            String sql = "DELETE FROM receptions WHERE doctor = '" + it.getDoctor() +
                    "' AND patient = '" + it.getPatient() + "' AND examination = '" + it.getExam() +
                    "' AND r_date = '" + it.getDateAndTime() + "'";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void addDoctor(Human h){
        try {
            String sql = "INSERT INTO doctors (fio, email) " +
                    "VALUES ('" + h.getFio() +
                    "', '" + h.getEmail() + "')";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        doctorCombo.getItems().add(h.getFio());
    }

    private void addPatient(Human h){
        try {
            String sql = "INSERT INTO patients (fio, email) " +
                    "VALUES ('" + h.getFio() +
                    "', '" + h.getEmail() + "')";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        patientCombo.getItems().add(h.getFio());
    }

    private void addExam(Exam e){
        try {
            String sql = "INSERT INTO examinations (name, price) " +
                    "VALUES ('" + e.getName() +
                    "', '" + e.getPriceString() + "')";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        examCombo.getItems().add(e.getName());
    }

    private void loadReceptions(){
        try {
            String SQL = "SELECT * FROM receptions";
            ResultSet resultSet = stmt.executeQuery(SQL);
            while (resultSet.next()) {
                String doctor = resultSet.getString(2);
                String patient = resultSet.getString(3);
                String exam = resultSet.getString(4);
                String dateAndTime = resultSet.getString(5);
                tableData.add(new Item(doctor, patient, exam, dateAndTime));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    private void loadDoctors(){
        try {
            String SQL = "SELECT * FROM doctors";
            ResultSet resultSet = stmt.executeQuery(SQL);

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Human h = new Human(name, email);
                doctors.add(h);
                doctorsList.getItems().add(h.getFio() + " | " + h.getEmail());
                doctorCombo.getItems().add(h.getFio());
            }


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void loadPatients(){
        try {
            String SQL = "SELECT * FROM patients";
            ResultSet resultSet = stmt.executeQuery(SQL);

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Human h = new Human(name, email);
                patients.add(h);
                patientsList.getItems().add(h.getFio() + " | " + h.getEmail());
                patientCombo.getItems().add(h.getFio());
            }


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void loadExaminations(){
        try {
            String SQL = "SELECT * FROM examinations";
            ResultSet resultSet = stmt.executeQuery(SQL);

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                double price = resultSet.getDouble(3);
                Exam e = new Exam(name, Double.toString(price));
                exams.add(e);
                examinationsList.getItems().add(e.getName() + " | " + e.getPriceString());
                examCombo.getItems().add(e.getName());
            }


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void initReceptionsTab(){
        for(Human h: doctors){

        }
    }

    private void deleteDoctor(Human h){
        try {
            String sql = "DELETE FROM doctors WHERE fio = '" + h.getFio() + "'";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void deletePatient(Human h){
        try {
            String sql = "DELETE FROM patients WHERE fio = '" + h.getFio() + "'";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void deleteExamination(Exam e){
        try {
            String sql = "DELETE FROM examinations WHERE name = '" + e.getName() + "'";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    @FXML
    public void initialize(){

        addDoctorButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!Human.CheckData(doctorNameText.getText(), doctorEmailText.getText())){
                    doctorTabLabel.setText("Некорректно заполнены обязательные поля...");
                    doctorTabLabel.setTextFill(Color.RED);
                }
                else {
                    for(Human h: doctors){
                        if (h.getFio() == doctorNameText.getText().trim()){
                            doctorTabLabel.setText("Введенный вами доктор уже есть в системе!");
                            doctorTabLabel.setTextFill(Color.RED);
                            return;
                        }
                        if (h.getEmail() == doctorEmailText.getText().trim()){
                            doctorTabLabel.setText("Введенный вами e-mail уже используется!");
                            doctorTabLabel.setTextFill(Color.RED);
                            return;
                        }
                    }
                    Human h = new Human(doctorNameText.getText(), doctorEmailText.getText());
                    doctors.add(h);
                    addDoctor(h);
                    doctorsList.getItems().add(h.getFio() + " | " + h.getEmail());
                    doctorTabLabel.setText("Доктор добавлен!");
                    doctorTabLabel.setTextFill(Color.BLACK);
                    doctorNameText.setText("");
                    doctorEmailText.setText("");
                }

            }
        });

        deleteDoctorButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (doctors.size() == 0){
                    doctorTabLabel.setText("Список врачей пуст...");
                    doctorTabLabel.setTextFill(Color.BLACK);
                    return;
                }
                if (doctorsList.getSelectionModel().getSelectedIndex() == -1){
                    doctorTabLabel.setText("Выберите доктора");
                    doctorTabLabel.setTextFill(Color.BLACK);
                    return;
                }
                deleteDoctor(doctors.get(doctorsList.getSelectionModel().getSelectedIndex()));
                doctors.remove(doctorsList.getSelectionModel().getSelectedIndex());
                doctorsList.getItems().remove(doctorsList.getSelectionModel().getSelectedIndex());
                doctorTabLabel.setText("Доктор удалён");
                doctorTabLabel.setTextFill(Color.BLACK);
            }
        });

        addPatientButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!Human.CheckData(patientNameText.getText(), patientEmailText.getText())){
                    patientTabLabel.setText("Некорректно заполнены обязательные поля...");
                    patientTabLabel.setTextFill(Color.RED);
                }
                else {
                    for(Human h: patients){
                        if (h.getFio() == patientNameText.getText().trim()){
                            patientTabLabel.setText("Введенный вами пациент уже есть в системе!");
                            patientTabLabel.setTextFill(Color.RED);
                            return;
                        }
                        if (h.getEmail() == patientEmailText.getText().trim()){
                            patientTabLabel.setText("Введенный вами e-mail уже используется!");
                            patientTabLabel.setTextFill(Color.RED);
                            return;
                        }
                    }
                    Human h = new Human(patientNameText.getText(), patientEmailText.getText());
                    addPatient(h);
                    patients.add(h);
                    patientsList.getItems().add(h.getFio() + " | " + h.getEmail());
                    patientTabLabel.setText("Пациент добавлен!");
                    patientTabLabel.setTextFill(Color.BLACK);
                    patientNameText.setText("");
                    patientEmailText.setText("");
                }

            }
        });

        deletePatientButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (patients.size() == 0){
                    patientTabLabel.setText("Список пациентов пуст...");
                    patientTabLabel.setTextFill(Color.BLACK);
                    return;
                }
                if (patientsList.getSelectionModel().getSelectedIndex() == -1){
                    patientTabLabel.setText("Выберите пациента");
                    patientTabLabel.setTextFill(Color.BLACK);
                    return;
                }
                deletePatient(patients.get(patientsList.getSelectionModel().getSelectedIndex()));
                patients.remove(patientsList.getSelectionModel().getSelectedIndex());
                patientsList.getItems().remove(patientsList.getSelectionModel().getSelectedIndex());
                patientTabLabel.setText("Доктор удалён");
                patientTabLabel.setTextFill(Color.BLACK);
            }
        });


        addExaminationButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!Exam.CheckData(examinationNameText.getText(), examinationPriceText.getText())){
                    examinationTabLabel.setText("Некорректно заполнены обязательные поля...");
                    examinationTabLabel.setTextFill(Color.RED);
                }
                else {
                    for(Exam e: exams){
                        if (e.getName() == examinationNameText.getText().trim()){
                            examinationTabLabel.setText("Введенное вами обследование уже есть в системе!");
                            examinationTabLabel.setTextFill(Color.RED);
                            return;
                        }
                    }
                    Exam e = new Exam(examinationNameText.getText(), examinationPriceText.getText());
                    addExam(e);
                    exams.add(e);
                    examinationsList.getItems().add(e.getName() + " | " + e.getPriceString() + " руб.");
                    examinationTabLabel.setText("Обследование добавлено!");
                    examinationTabLabel.setTextFill(Color.BLACK);
                    examinationNameText.setText("");
                    examinationPriceText.setText("");
                }

            }
        });

        deleteExaminationButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (exams.size() == 0){
                    examinationTabLabel.setText("Список обследований пуст...");
                    examinationTabLabel.setTextFill(Color.BLACK);
                    return;
                }
                if (examinationsList.getSelectionModel().getSelectedIndex() == -1){
                    examinationTabLabel.setText("Выберите обследование");
                    examinationTabLabel.setTextFill(Color.BLACK);
                    return;
                }
                deleteExamination(exams.get(examinationsList.getSelectionModel().getSelectedIndex()));
                exams.remove(examinationsList.getSelectionModel().getSelectedIndex());
                examinationsList.getItems().remove(examinationsList.getSelectionModel().getSelectedIndex());
                examinationTabLabel.setText("Обследование удалено");
                examinationTabLabel.setTextFill(Color.BLACK);
            }
        });

        receptionHour.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    receptionHour.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        receptionMin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    receptionMin.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        addReceptionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (doctorCombo.getSelectionModel().getSelectedIndex() == -1){
                    receptionLabel.setText("Выберите врача");
                    receptionLabel.setTextFill(Color.RED);
                    return;
                }
                if (patientCombo.getSelectionModel().getSelectedIndex() == -1){
                    receptionLabel.setText("Выберите пациента");
                    receptionLabel.setTextFill(Color.RED);
                    return;
                }
                if (examCombo.getSelectionModel().getSelectedIndex() == -1){
                    receptionLabel.setText("Выберите обследование");
                    receptionLabel.setTextFill(Color.RED);
                    return;
                }
                if (receptionDate.getValue() == null){
                    receptionLabel.setText("Укажите дату приёма");
                    receptionLabel.setTextFill(Color.RED);
                    return;
                }

                if (receptionHour.getText().trim().isEmpty() || receptionMin.getText().trim().isEmpty()){
                    receptionLabel.setText("Укажите время приёма");
                    receptionLabel.setTextFill(Color.RED);
                    return;
                }

                String doctor = doctors.get(doctorCombo.getSelectionModel().getSelectedIndex()).getFio();
                String patient = patients.get(patientCombo.getSelectionModel().getSelectedIndex()).getFio();
                String exam = exams.get(examCombo.getSelectionModel().getSelectedIndex()).getName();
                LocalDate date = receptionDate.getValue();
                int hour = Integer.parseInt(receptionHour.getText());
                int min = Integer.parseInt(receptionMin.getText());
                Item it = new Item(doctor, patient, exam, date.toString() + " " + hour + ":" + min);
                addReception(it);
                tableData.add(it);
                receptionLabel.setText("Приём добавлен!");
                receptionLabel.setTextFill(Color.BLACK);
            }
        });

        deleteReceptionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (tableData.size() == 0){
                    receptionLabel.setText("Приёмов нет...");
                    receptionLabel.setTextFill(Color.BLACK);
                    return;
                }
                int row = table.getSelectionModel().getSelectedIndex();
                if (row == -1){
                    receptionLabel.setText("Выберите приём!");
                    receptionLabel.setTextFill(Color.RED);
                    return;
                }
                deleteReception(tableData.get(row));
                tableData.remove(row);
                receptionLabel.setText("Приём удалён!");
                receptionLabel.setTextFill(Color.BLACK);
            }
        });


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ais_clinic", "root", "root");
            stmt = con.createStatement();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        // устанавливаем тип и значение которое должно хранится в колонке
        doctorColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("doctor"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("patient"));
        examColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("exam"));
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("dateAndTime"));
        table.setItems(tableData);
        loadReceptions();
        loadDoctors();
        loadPatients();
        loadExaminations();
    }

}