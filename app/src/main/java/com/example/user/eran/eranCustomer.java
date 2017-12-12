package com.example.user.eran;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eran on 01/11/2017.
 */


    public class eranCustomer {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("customer");

   private String FirstName, LastName, UserName, Email, Password, Street, City, id, imagePath;

    public eranCustomer(){
        //for fire base
    }

    public eranCustomer(String fname, String lname, String uname, String email,
                        String password, String street,String city, String id,String path) {

        this.FirstName=fname;
        this.LastName=lname;
        this.UserName=uname;
        this.Email=email;
        this.Password=password;
        this.Street=street;
        this.City=city;
        this.id=id;
        this.imagePath=path;

    }

    public String getImagePath(){return imagePath; }

    public void setImagePath(String path){this.imagePath=path;}

    public String getFname() {
        return FirstName;
    }

    public void setFname(String fname) {
        this.FirstName= fname;
    }

    public String getLname() {
        return LastName;
    }

    public void setLname(String lname) {
        this.LastName = lname;
    }

    public String getUname() {
        return UserName;
    }

    public void setUname(String uname) {
        this.UserName = uname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        this.Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void save()
    {
        DatabaseReference usersRef = ref.child(id);
        DatabaseReference c;
        c=usersRef.child("FirstName");
        c.setValue(FirstName);
        c=usersRef.child("LastName");
        c.setValue(LastName);
        c=usersRef.child("UserName");
        c.setValue(UserName);
        c=usersRef.child("Email");
        c.setValue(Email);
        c=usersRef.child("Password");
        c.setValue(Password);
        c=usersRef.child("City");
        c.setValue(City);
        c=usersRef.child("Street");
        c.setValue(Street);
        c=usersRef.child("Photo");
        c.setValue(imagePath);
    }
}

