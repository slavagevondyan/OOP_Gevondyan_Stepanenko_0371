package com.example.ais_clinic;

import java.util.Date;

public class Reception {
    private String doctor;
    private String patient;
    private String exam;
    private Date date;
    private int hour;
    private int min;

    public Reception(int doctorId, int patientId, int examId, Date date, int hour, int min){
        this.doctor = doctor;
        this.patient = patient;
        this.exam = exam;
        this.date = date;
        this.hour = hour;
        this.min = min;
    }

    public void setDoctor(String doctor){
        this.doctor = doctor;
    }

    public String getDoctor(){
        return this.doctor;
    }

    public void setPatient(String patient){
        this.patient = patient;
    }

    public String getPatient(){
        return this.patient;
    }

    public void setExam(String exam){
        this.exam = exam;
    }

    public String getExam(){
        return this.exam;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return this.date;
    }

    public void setHour(int hour){
        this.hour = hour;
    }

    public int getHour(){
        return this.hour;
    }

    public void setMin(int min){
        this.min = min;
    }

    public int getMin(){
        return this.min;
    }
}
