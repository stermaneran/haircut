package com.example.user.eran;

/**
 * Created by eran on 19/12/17.
 */

public class Appointment {

    String Price;
    String Date;
    String Time;
    String Email;


    public Appointment() {
        //for fire base
    }


    public Appointment(String date, String time, String email, String price) {
        Date = date;
        Time = time;
        Email = email;
        Price = price;
    }
}