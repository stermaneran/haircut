package com.example.user.eran;

/**
 * Created by eran on 19/12/17.
 * represents an appointment object
 */


public class Appointment {

    String Price, Date, Time, Email;

    /**
     * default ctor
     */
    public Appointment() {
        //for fire base
    }

    /**
     * ctor
     */
    public Appointment(String date, String time, String email, String price) {
        Date = date;
        Time = time;
        Email = email;
        Price = price;
    }
}