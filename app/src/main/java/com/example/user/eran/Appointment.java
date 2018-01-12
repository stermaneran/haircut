package com.example.user.eran;

/**
 * Created by eran on 19/12/17.
 */

public class Appointment {

    String _price;
    String _date;
    String _time;
    String _email;
    String _cust;


    public Appointment() {
        //for fire base
    }


    public Appointment(String cust, String date, String time, String email, String price) {
        _date = date;
        _time = time;
        _email = email;
        _price = price;
        _cust = cust;
    }
}