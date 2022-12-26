package com.example.ais_clinic;

public class Human {
    private String fio;
    private String email;

    public Human(String fio, String email){
        this.fio = fio.trim();
        this.email = email.trim();
    }

    public String getFio(){
        return this.fio;
    }

    public String getEmail(){
        return this.email;
    }

    public void setFio(String fio){
        this.fio = fio.trim();
    }

    public void setEmail(String email){
        this.email = email.trim();
    }

    public static boolean CheckData(String fio, String email){
        if (fio.trim().isEmpty() || email.trim().isEmpty()){
            return false;
        }
        for(char s: fio.toCharArray()){
            if (Character.isDigit(s)) return false;
        }
        return true;
    }
}
