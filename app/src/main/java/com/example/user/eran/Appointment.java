package com.example.user.eran;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eran on 19/12/17.
 */

public class Appointment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Appointments");

    String _price;
    String _date;
    String _time;
    String _email;
    String _cust;


    public Appointment() {}


    public Appointment(String cust ,String date, String time, String email,String price) {
        _date = date;
        _time = time;
        _email = email;
        _price = price;
        _cust = cust;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_cust() {
        return _email;
    }

    public void set_cust(String _cust) {
        this._email = _cust;
    }

    public void save()
    {
        DatabaseReference usersRef = ref.child(_cust);
        DatabaseReference c;
        c=usersRef.child("Email");
        c.setValue(_email);
        c=usersRef.child("Date");
        c.setValue(_date);
        c=usersRef.child("Time");
        c.setValue(_time);
        c=usersRef.child("Price");
        c.setValue(_price);

    }
}
