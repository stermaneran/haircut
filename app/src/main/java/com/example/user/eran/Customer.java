package com.example.user.eran;

/**
 * Created by eran on 01/11/2017.
 */

    public class Customer {

    public String FirstName, LastName, UserName, Email, Password, Street, City, id, imagePath, Type, gender;

    public Customer() {
        //for fire base
    }

    public Customer(String fname, String lname, String uname, String email,
                    String password, String street, String city, String id, String path, String gender) {

        this.FirstName = fname;
        this.LastName = lname;
        this.UserName = uname;
        this.Email = email;
        this.Password = password;
        this.Street = street;
        this.City = city;
        this.id = id;
        this.imagePath = path;
        this.gender = gender;
        this.Type = "Non-Admin";

    }
}