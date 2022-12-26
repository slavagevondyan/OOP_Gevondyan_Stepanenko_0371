package com.example.ais_clinic;

public class Item {
    public String doctor;
    public String patient;
    public String exam;
    public String dateAndTime;

    public Item(String doctor, String patient, String exam, String dateAndTime){
        this.doctor = doctor;
        this.patient = patient;
        this.exam = exam;
        this.dateAndTime = dateAndTime;
    }

    public String getDoctor(){
        return this.doctor;
    }
    public String getPatient(){
        return this.patient;
    }
    public String getExam(){
        return this.exam;
    }
    public String getDateAndTime(){
        return this.dateAndTime;
    }
}
