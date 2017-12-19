package com.example.user.eran;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eran on 19/12/17.
 */

public class Appointment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Appointment");

    double _price;
    String _date;
    String _time;
    Customer _cust;

    public Appointment(String date, String time, Customer cust,double price){
        _date = date;
        _time = time;
        _cust = cust;
        _price = price;
    }

    public void save()
    {
        DatabaseReference usersRef = ref.child(_cust.getId());
        DatabaseReference c;
        c=usersRef.child("ID");
        c.setValue(_cust.getId());
        c=usersRef.child("Date");
        c.setValue(_date);
        c=usersRef.child("Time");
        c.setValue(_time);
        c=usersRef.child("Price");
        c.setValue(_price);
    }
}
