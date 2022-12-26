package com.example.ais_clinic;

public class Exam {
    private String name;
    private double price;

    public Exam(String name, String price){
        this.name = name.trim();
        this.price = Double.parseDouble(price.trim());
    }

    public String getName(){
        return this.name;
    }

    public String getPriceString(){
        return Double.toString(this.price);
    }

    public double getPrice(){
        return this.price;
    }


    public static boolean CheckData(String name, String price){
        if (name.trim().isEmpty() || price.trim().isEmpty()){
            return false;
        }

        try{
            double d = Double.parseDouble(price.trim());
        }
        catch(Exception ex){
            return false;
        }

        if(Double.parseDouble(price.trim()) <= 0){
            return false;
        }

        return true;
    }
}
