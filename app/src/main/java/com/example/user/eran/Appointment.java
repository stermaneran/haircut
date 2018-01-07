package com.example.user.eran;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eran on 19/12/17.
 */

public class Appointment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Appointments");

    private FirebaseAnalytics mFirebaseAnalytics;

    private static final String _path = "Appointments";


    String _custID;
    String _hcid;
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


    public static String get_path() {
        return _path;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public String get_date() {
        return _date;
    }

    public String get_time() {
        return _time;
    }

    public String get_custID() {
        return _custID;
    }

    public String get_hcid() {
        return _hcid;
    }


    public void set_cust(String _cust) {
        this._email = _cust;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public String get_price() {
        return _price;
    }

    public void set_date(String _date) {
        this._date = _date;
    }


    public void set_time(String _time) {
        this._time = _time;
    }



    public String get_cust() {
        return _email;
    }

    public void save() {
        DatabaseReference usersRef = ref.push();
        DatabaseReference c;
        c = usersRef.child("Email");
        c.setValue(_email);
        c = usersRef.child("Date");
        c.setValue(_date);
        c = usersRef.child("Time");
        c.setValue(_time);
        c = usersRef.child("Price");
        c.setValue(_price);

    }
}
