package com.example.user.eran;

/**
 * Created by eran on 01/11/2017.
 */

    public class Customer {

    public String FirstName, LastName, UserName, Email, Password, Street, City, id, Photo, Type, Gender;

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
        this.Photo = path;
        this.Gender = gender;
        this.Type = "Non-Admin";

    }
}