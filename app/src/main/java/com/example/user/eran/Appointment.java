package com.example.user.eran;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eran on 19/12/17.
 */

public class Appointment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Appointment");

    String _price;
    String _date;
    String _time;
    String _cust;

    public Appointment(String date, String time, String cust,String price) {
        _date = date;
        _time = time;
        _cust = cust;
        _price = price;
    }
}
